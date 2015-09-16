package mg.hrz.postgis.repository.commun;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import mg.hrz.postgis.contrainte.Order;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;


/**
 * @param <T>
 */

public abstract class AbstractCommunRepository<T> implements CommunRepository<T> {

    private static final String JPQL_DELETE = "DELETE FROM ";
    private static final String JPQL_WHERE = " WHERE e.";
    private static final String JPQL_AND = " AND e.";
    private static final String JPQL_ALIAS_ENTITY = " e ";
    private static final String JPQL_PARAM = " = :param";
    private static final String JPQL_ORDER_BY = " ORDER BY ";
    private static final String SEPARATOR = " ";
    
    @PersistenceContext(unitName = "PostGisWebPU")
    protected transient EntityManager entityManager;
    
    private Class<T> entityType = null;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void initialize() {
        Type type = getClass().getSuperclass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        entityType = (Class<T>) paramType.getActualTypeArguments()[0];
    }

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    @Override
    @Transactional
    public List<T> findAll() {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        c.select(root);
        TypedQuery<T> query = this.entityManager.createQuery(c);
        return query.getResultList();
    }

    /**
     * Generic method to get an object based on class and identifier. 
     *
     * @param id the identifier (primary key) of the class
     * @return a populated object
     */
    @Override
    @Transactional
    public T findById(final Serializable id) {
        if (this.entityType == null) {
            this.initialize();
        }
        return this.entityManager.find(entityType, id);
    }

    /**
     * Generic method to get an object based on criteria.
     *
     * @param criteria Map with criteria
     * @return a populated object
     */
    @Transactional
    public T findByCriteria(final Map<String, Object> criteria) {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        CriteriaQuery<T> select = c.select(root);
        Predicate[] predicateList = this.preparePredicate(qb, c, root, select, criteria);
        if (predicateList.length == 1) {
            select.where(predicateList[0]);
        } else {
            select.where(qb.and(predicateList));
        }
        TypedQuery<T> query = this.entityManager.createQuery(c);
        return query.getSingleResult();
    }

    /**
     * Generic method to get all objects based on criteria.
     * 
     * @param criteria Map with criteria
     * @return a populated object
     */
    @Transactional
    @Override
    public List<T> findAllByCriteria(final Map<String, Object> criteria) {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        CriteriaQuery<T> select = c.select(root);
        Predicate[] predicateList = this.preparePredicate(qb, c, root, select, criteria);
        if (predicateList.length == 1) {
            select.where(predicateList[0]);
        } else {
            select.where(qb.and(predicateList));
        }
        TypedQuery<T> query = this.entityManager.createQuery(c);
        return query.getResultList();
    }

    /**
     * Generic method to add an object.
     *
     * @param entity the object to add
     */
    @Override
    @Transactional
    public void insert(final T entity) {
        this.entityManager.persist(entity);
    }

    /**
     * Generic method to update an object.
     *
     * @param entity the object to save
     * @return
     */
    @Override
    @Transactional
    public T update(final T entity) {
        return this.entityManager.merge(entity);
    }

    /**
     * Remove all data from database
     */
    @Transactional
    @Override
    public boolean exists(final Serializable id) {
        T result = this.entityManager.find(this.entityType, id);
        return result != null;
    }

    /**
     * Remove all data from database
     */
    @Transactional
    @Override
    public boolean exists(final Map<String, Object> criteria) {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        CriteriaQuery<T> select = c.select(root);
        Predicate[] predicateList = this.preparePredicate(qb, c, root, select, criteria);

        if (predicateList.length == 1) {
            select.where(predicateList[0]);
        } else {
            select.where(qb.and(predicateList));
        }
        TypedQuery<T> query = this.entityManager.createQuery(c);
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
        }

        return result != null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Predicate[] preparePredicate(final CriteriaBuilder qb, final CriteriaQuery<T> c, Root<T> root, CriteriaQuery<T> select,
            final Map<String, Object> criteria) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        Path<Object> path = null;
        String[] fields = null;
        int tailleFields = 0;
        if (criteria != null) {
            for (String key : criteria.keySet()) {
                if (key.contains(".")) {
                    fields = key.split("\\.");
                    if (fields[0].contains("id")) {
                        fields = new String[1];
                        fields[0] = key;
                    }
                } else {
                    fields = new String[1];
                    fields[0] = key;
                }
                tailleFields = fields.length;
                if (tailleFields == 1) {
                    path = root.get(key);
                } else {
                    Join join = null;
                    boolean isFirst = true;
                    int fieldIndex = tailleFields - 1;
                    for (int i = 0; i < tailleFields; i++) {
                        if (isFirst) {
                            join = root.join(fields[i]);
                            isFirst = false;
                        } else {
                            join = join.join(fields[i]);
                        }
                        if ((fieldIndex - i) == 1) {
                            break;
                        }
                    }
                    if (join != null) {
                        path = join.get(fields[fieldIndex]);
                    }
                }
                predicateList.add(qb.equal(path, criteria.get(key)));
            }
        }
        Predicate[] preTab = {};
        return predicateList.toArray(preTab);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Predicate[] preparePredicateForCount(final CriteriaBuilder qb, Root<T> root, final Map<String, Object> criteria) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        Path<Object> path = null;
        String[] fields = null;
        int tailleFields = 0;
        if (criteria != null) {
            for (String key : criteria.keySet()) {
                if (key.contains(".")) {
                    fields = key.split("\\.");
                    if (fields[0].contains("id")) {
                        fields = new String[1];
                        fields[0] = key;
                    }
                } else {
                    fields = new String[1];
                    fields[0] = key;
                }
                tailleFields = fields.length;
                if (tailleFields == 1) {
                    path = root.get(key);
                } else {
                    Join join = null;
                    boolean isFirst = true;
                    int fieldIndex = tailleFields - 1;
                    for (int i = 0; i < tailleFields; i++) {
                        if (isFirst) {
                            join = root.join(fields[i]);
                            isFirst = false;
                        } else {
                            join = join.join(fields[i]);
                        }
                        if ((fieldIndex - i) == 1) {
                            break;
                        }
                    }
                    if (join != null) {
                        path = join.get(fields[fieldIndex]);
                    }
                }
                predicateList.add(qb.equal(path, criteria.get(key)));
            }
        }
        Predicate[] preTab = {};
        return predicateList.toArray(preTab);
    }

    /**
     * Generic method to delete an object based on class and id
     *
     * @param id the identifier (primary key) of the class
     */
    @Override
    @Transactional
    public void delete(final T entity) {
        T result = this.entityManager.merge(entity);
        this.entityManager.remove(result);
    }

    @Transactional
    @Override
    public void delete(final Serializable id) {
        T entity = this.findById(id);
        this.entityManager.remove(entity);
    }

    @Override
    @Transactional
    public void deleteByCriteria(final Map<String, Object> criteria) {
        Query query = this.prepareDeleteQuery(criteria);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll() {
        Query query = this.prepareDeleteQuery(null);
        query.executeUpdate();
    }

    private Query prepareDeleteQuery(final Map<String, Object> criteria) {
        StringBuilder jpql = new StringBuilder(JPQL_DELETE + this.entityType.getSimpleName() + JPQL_ALIAS_ENTITY);
        boolean withWhere = false;
        int paramIndex = 0;
        if (criteria != null) {
            for (String key : criteria.keySet()) {
                if (paramIndex == 0) {
                    jpql.append(JPQL_WHERE + key + JPQL_PARAM + (++paramIndex));
                } else {
                    jpql.append(JPQL_AND + key + JPQL_PARAM + (++paramIndex));
                }
            }
            withWhere = true;
        }
        Query query = this.entityManager.createQuery(jpql.toString());
        if (withWhere) {
            String paramName = "";
            paramIndex = 0;
            for (Object value : criteria.values()) {
                paramName = "param" + (++paramIndex);
                query.setParameter(paramName, value);
            }

        }
        return query;
    }

    /**
     * Generic method used to get all objects by page of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @param numeroPage indice de la page a recupere commence a 0 , [0 .... ]
     * @param nombreDeLignesParPage nombre de ligne pas page , ( le pas , l
     * offset )
     * @return List of populated objects
     */
    @Override
    @Transactional
    public List<T> findAllPagination(int numeroPage, int nombreDeLignesParPage) {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        c.select(root);
        TypedQuery<T> query = this.entityManager.createQuery(c);
        int startPosition = (numeroPage * nombreDeLignesParPage);
        query.setFirstResult(startPosition);
        query.setMaxResults(nombreDeLignesParPage);
        return query.getResultList();
    }

    /**
     * Generic method used to count all objects a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return number of rows in a table
     */
    @Override
    @Transactional
    public int countAllRows() {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> c = qb.createQuery(Long.class);
        Root<T> root = c.from(this.entityType);
        c.select(qb.count(root));
        return this.entityManager.createQuery(c).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public List<T> findByCriteriaPagination(int numeroPage, int nombreDeLignesParPage, final Map<String, Object> criteria) {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(this.entityType);
        Root<T> root = c.from(this.entityType);
        CriteriaQuery<T> select = c.select(root);

        Map<String, Object> criteriaOrder = getCriteriaOrder(criteria);
        Map<String, Object> criteriaWhere = getCriteriaWhere(criteria);

        Predicate[] predicateList = this.preparePredicate(qb, c, root, select, criteriaWhere);
        if (predicateList.length == 1) {
            select.where(predicateList[0]);
        } else if (predicateList.length > 0) {
            select.where(qb.and(predicateList));
        }
        // gere l'orderBy
        if (criteriaOrder.size() > 0) {
            javax.persistence.criteria.Order order;
            String sort = (String) MapUtils.getObject(criteriaOrder, Order.SORT.getValue());
            String dir = (String) MapUtils.getObject(criteriaOrder, Order.DIRECTION.getValue());
            if (StringUtils.equals(dir, Order.ASC.getValue())) {
                order = qb.asc(root.get(sort));
            } else {
                order = qb.desc(root.get(sort));
            }
            c.orderBy(order);
        }

        TypedQuery<T> query = this.entityManager.createQuery(c);
        int startPosition = (numeroPage * nombreDeLignesParPage);
        query.setFirstResult(startPosition);
        query.setMaxResults(nombreDeLignesParPage);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int countRowsByCriteria(final Map<String, Object> criteria) {
        Map<String, Object> criteriaWhere = getCriteriaWhere(criteria);
        if (criteriaWhere.size() > 0) {

            CriteriaBuilder qbCount = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> count = qbCount.createQuery(Long.class);
            Root<T> root = count.from(this.entityType);
            count.select(qbCount.count(root));

            // predicateList for where
            Predicate[] predicateList = this.preparePredicateForCount(qbCount, root, criteriaWhere);
            if (predicateList.length == 1) {
                count.where(predicateList[0]);
            } else if (predicateList.length > 0) {
                count.where(qbCount.and(predicateList));
            }
            return this.entityManager.createQuery(count).getSingleResult().intValue();
        } else {
            return countAllRows();
        }
    }

    /**
     * Find in allCriteria, all criteria for order clause Exemple : sort =
     * libelle dir = asc
     *
     * @param allCriteria
     * @return criteria for order by
     */
    public Map<String, Object> getCriteriaOrder(Map<String, Object> allCriteria) {
        Map<String, Object> criteriaOrder = new HashMap<String, Object>();
        Object dir = MapUtils.getObject(allCriteria, Order.DIRECTION.getValue());
        Object sort = MapUtils.getObject(allCriteria, Order.SORT.getValue());

        if (dir != null && sort != null) {
            criteriaOrder.put(Order.SORT.getValue(), sort);
            criteriaOrder.put(Order.DIRECTION.getValue(), dir);
        }
        return criteriaOrder;
    }

    /**
     * Find in allCriteria, all criteria for where clause
     *
     * @param allCriteria
     * @return criteria for where clause
     */
    public Map<String, Object> getCriteriaWhere(Map<String, Object> allCriteria) {
        Map<String, Object> criteriaWhere = new HashMap<String, Object>();
        if (allCriteria != null) {
            for (Map.Entry<String, Object> c : allCriteria.entrySet()) {
                if (!StringUtils.equals(Order.DIRECTION.getValue(), c.getKey()) && !StringUtils.equals(Order.SORT.getValue(), c.getKey())) {
                    criteriaWhere.put(c.getKey(), c.getValue());
                }
            }
        }
        return criteriaWhere;
    }

    /**
     * methode pour faire la concatenation du JPQL avec ORDER BY
     *
     * @param criteria
     * @param jpql
     * @return
     */
    public String concatJPQL(Map<String, Object> criteria, String jpql) {
        String jpqlConcat = null;
        if (criteria != null) {
            String sort = (String) MapUtils.getObject(criteria, Order.SORT.getValue());
            String dir = (String) MapUtils.getObject(criteria, Order.DIRECTION.getValue());
            if (sort != null && dir != null) {
                String jpqlNoOrder = getOderByString(jpql, SEPARATOR);
                jpqlConcat = jpqlNoOrder.concat(JPQL_ORDER_BY.concat(sort).concat(SEPARATOR + dir.toUpperCase()));
            } else {
                jpqlConcat = jpql;
            }
        } else {
            jpqlConcat = jpql;
        }
        return jpqlConcat;
    }

    /**
     * decouper une chaine de caractere avec les separateur , en une liste de
     * string
     *
     * @param string
     * @return
     */
    private String getOderByString(String string, String separator) {
        String instructionOrder = "ORDER";
        StringBuffer jpql = new StringBuffer();
        List<String> liste = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(string, separator);
        while (st.hasMoreTokens()) {
            liste.add(st.nextToken());
        }
        for (String elt : liste) {
            if (!elt.equalsIgnoreCase(instructionOrder)) {
                jpql.append(SEPARATOR);
                jpql.append(elt);
            } else {
                break;
            }
        }
        return jpql.toString();
    }
}
