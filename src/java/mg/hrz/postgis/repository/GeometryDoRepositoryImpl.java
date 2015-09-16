/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.repository;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import mg.hrz.postgis.donnee.domainobject.Geometrie;
import mg.hrz.postgis.repository.commun.AbstractCommunRepository;
import org.postgresql.util.PGobject;

@Named
@RequestScoped
public class GeometryDoRepositoryImpl extends AbstractCommunRepository<Geometrie> implements GeometryDoRepository {
    
    @Override
    public List getGeometryByName(String name) {
        String sql = "SELECT name, geom, ST_AsGeoJSON(geom) FROM geometries WHERE name = ?1 ";
        return this.entityManager.createNativeQuery(sql).setParameter(1, "Point").getResultList();
    }

    @Override
    public Object getGeom(String geoJson) {
        String sql = "SELECT ST_GeomFromGeoJSON(?1) AS geometry";
        return this.entityManager.createNativeQuery(sql).setParameter(1, geoJson).getSingleResult();
    }

    @Override
    @Transactional
    public int saveGeometry(String name, PGobject geometry) {
        String sql = "INSERT INTO geometries (name, geom) VALUES (?1, ?2)";
        return this.entityManager.createNativeQuery(sql)
                .setParameter(1, name)
                .setParameter(2, geometry)
                .executeUpdate();
    }
}