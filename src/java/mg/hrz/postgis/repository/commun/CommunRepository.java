package mg.hrz.postgis.repository.commun;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 */
public interface CommunRepository<T> {

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> findAll();

    /**
     * Generic method used to get all objects by page of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @param numeroPage indice de la page a recupere commence a 0 , [0 .... ]
     * @param nombreDeLignesParPage nombre de ligne pas page , ( le pas , l
     * offset )
     *
     * @return List of populated objects
     */
    List<T> findAllPagination(int numeroPage, int nombreDeLignesParPage);

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param id the identifier (primary key) of the class
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T findById(final Serializable id);

    /**
     * Generic method to get an object based on criteria. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param criteria Map with criteria
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T findByCriteria(final Map<String, Object> criteria);

    /**
     * Generic method to get all objects based on criteria. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param criteria Map with criteria
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    List<T> findAllByCriteria(final Map<String, Object> criteria);

    /**
     * Generic method to add an object.
     *
     * @param entity the object to add
     */
    void insert(final T entity);

    /**
     * Generic method to update an object.
     *
     * @param entity the object to save
     */
    T update(final T entity);

    /**
     * Remove all data from database
     *
     */
    boolean exists(final Serializable id);

    /**
     * Remove all data from database
     *
     */
    boolean exists(final Map<String, Object> criteria);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param entity the identifier (primary key) of the class
     */
    void delete(final Serializable id);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param entity the identifier (primary key) of the class
     */
    void delete(final T entity);

    /**
     * Remove all data from database by criteria
     *
     */
    void deleteByCriteria(final Map<String, Object> criteria);

    /**
     * Remove all data from database
     *
     */
    void deleteAll();

    /**
     * Generic method used to count all objects a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return number of rows in a table
     */
    int countAllRows();

    /**
     * Generic method to get all objects based on criteria. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param numeroPage indice de la page a recupere commence a 0 , [0 .... ]
     * @param nombreDeLignesParPage nombre de ligne pas page , ( le pas , l
     * offset )
     * @param criteria Map with criteria (cf findByCriteria) + criteria for
     * order
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    List<T> findByCriteriaPagination(int numeroPage, int nombreDeLignesParPage, final Map<String, Object> criteria);

    /**
     * Generic method used to count all objects a particular type with criteria
     *
     * @return number of rows in a table corresponding at criteria
     */
    int countRowsByCriteria(final Map<String, Object> criteria);
}
