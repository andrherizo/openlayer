/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.repository;

import java.util.List;
import mg.hrz.postgis.donnee.domainobject.Geometrie;
import mg.hrz.postgis.repository.commun.CommunRepository;
import org.postgresql.util.PGobject;


public interface GeometryDoRepository extends CommunRepository<Geometrie> {
    public List<Object[]> getGeometryByName(String name);
    public Object getGeom(String geoJson);
    public int saveGeometry(String name, PGobject geometry);
}
