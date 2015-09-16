/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.service;

import java.util.List;
import java.util.Map;
import mg.hrz.postgis.donnee.domainobject.Geometrie;

public interface GeometryDoService {
    public List<Geometrie> getGeometryByName(String name);
    public List<Geometrie> getGeoJsonGeometry(Map<String, String> geoJsons);
    public Geometrie getGeoJsonGeometry(String name, String geoJson);
    public int saveGeometrie(List<Geometrie> geometries);
}
