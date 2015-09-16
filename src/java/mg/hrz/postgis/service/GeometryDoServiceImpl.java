/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mg.hrz.postgis.donnee.domainobject.Geometrie;
import mg.hrz.postgis.repository.GeometryDoRepository;
import org.postgresql.util.PGobject;

@Named
@RequestScoped
public class GeometryDoServiceImpl implements GeometryDoService {
    
    @Inject GeometryDoRepository geometryDoRepository;

    @Override
    public List<Geometrie> getGeometryByName(String name) {
        List<Object[]> results = geometryDoRepository.getGeometryByName(name);
        List<Geometrie> geoms = new ArrayList<>();
        for (Object[] r : results) {
            Geometrie g = new Geometrie();
            g.setName(r[0].toString());
            g.setGeom((PGobject) r[1]);
            g.setGeomJson(r[2].toString());
            geoms.add(g);
        }
        return geoms;
    }

    @Override
    public Geometrie getGeoJsonGeometry(String name, String geoJson) {
        Object result = geometryDoRepository.getGeom(geoJson);
        if(result != null) {
            Geometrie g = new Geometrie();
            g.setName(name);
            g.setGeom((PGobject) result);
            return g;
        }
        return null;
    }

    @Override
    public List<Geometrie> getGeoJsonGeometry(Map<String, String> geoJsons) {
        List<Geometrie> pg = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = geoJsons.entrySet();
        Iterator<Map.Entry<String, String>> i = entries.iterator();
        while (i.hasNext()) {
            Map.Entry<String, String> entry = i.next();
            String name = entry.getKey();
            String geoJson = entry.getValue();
            Geometrie g = getGeoJsonGeometry(name, geoJson);
            if(g != null)
                pg.add(g);
        }
        return pg;
    }

    @Override
    public int saveGeometrie(List<Geometrie> geometries) {
        int insert = 0;
        for (Geometrie geometrie : geometries) {
            insert += geometryDoRepository.saveGeometry(geometrie.getName(), geometrie.getGeom());
        }
        return insert;
    }
}