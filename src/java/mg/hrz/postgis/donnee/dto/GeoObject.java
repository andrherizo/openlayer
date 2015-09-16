/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.donnee.dto;

import com.google.gson.Gson;
import java.util.List;

public class GeoObject {

    private String type;
    private List<Object> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Object> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
