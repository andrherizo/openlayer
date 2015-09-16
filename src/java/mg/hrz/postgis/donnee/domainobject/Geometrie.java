/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.donnee.domainobject;

import org.postgresql.util.PGobject;

public class Geometrie {

    private String name;
    private PGobject geom;
    private String geomJson;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PGobject getGeom() {
        return geom;
    }

    public void setGeom(PGobject geom) {
        this.geom = geom;
    }

    public String getGeomJson() {
        return geomJson;
    }

    public void setGeomJson(String geomJson) {
        this.geomJson = geomJson;
    }
}
