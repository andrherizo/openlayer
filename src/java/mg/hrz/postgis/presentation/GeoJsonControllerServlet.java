/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.presentation;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.hrz.postgis.donnee.domainobject.Geometrie;
import mg.hrz.postgis.donnee.dto.GeoObject;
import mg.hrz.postgis.service.GeometryDoService;

public class GeoJsonControllerServlet extends HttpServlet {

    @Inject
    GeometryDoService geometryDoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/geojson.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String geoJson = req.getParameter("geo");
        int retour = 0;
        if (geoJson != null) {
            Type listType = new TypeToken<List<GeoObject>>() {}.getType();
            List<GeoObject> geoJsons = new Gson().fromJson(geoJson, listType);
            Map<String, String> map = new HashMap<>();
            for (GeoObject g : geoJsons) {
                map.put(g.getType(), g.toString());
            }
            List<Geometrie> g = geometryDoService.getGeoJsonGeometry(map);
            retour = geometryDoService.saveGeometrie(g);
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(retour));
    }
}
