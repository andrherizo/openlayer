/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.presentation;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.hrz.postgis.donnee.domainobject.Geometrie;
import mg.hrz.postgis.service.GeometryDoService;

public class GeometryControllerServlet extends HttpServlet {
    
    @Inject
    GeometryDoService geometryDoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Geometrie> results = geometryDoService.getGeometryByName("Point");        
        req.setAttribute("geom", results);
        getServletContext().getRequestDispatcher("/WEB-INF/geometry.jsp").forward(req, resp);
    }
}
