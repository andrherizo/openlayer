package mg.hrz.postgis.presentation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.hrz.postgis.donnee.domainobject.Langue;
import mg.hrz.postgis.donnee.domainobject.RubriqueInformation;
import mg.hrz.postgis.donnee.domainobject.TypeRubriqueInformation;
import mg.hrz.postgis.repository.LangueRepository;
import mg.hrz.postgis.repository.RubriqueInformationRepository;
import mg.hrz.postgis.repository.TypeRubriqueInformationRepository;
import mg.hrz.postgis.service.TypeRubriqueInformationService;

public class SuccessControllerServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 4126712564041019731L;
    
    @Inject
    LangueRepository langueRepository;
    
    @Inject
    TypeRubriqueInformationRepository typeRubriqueInformationRepository;
    
    @Inject
    RubriqueInformationRepository rubriqueInformationRepository;
    
    @Inject
    TypeRubriqueInformationService typeRubriqueInformationService;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        List<Langue> langues = langueRepository.findAll();
        List<RubriqueInformation> rubriques = rubriqueInformationRepository.findAll();
        List<TypeRubriqueInformation> typeRubriques = typeRubriqueInformationRepository.findAll();
        Map<Integer, Set<RubriqueInformation>> rubriquesPerType = new HashMap<>();
        
        for (TypeRubriqueInformation typeRubriqueInformation : typeRubriques) {
            rubriquesPerType.put(typeRubriqueInformation.getIdTypeRubriqueInfo(), typeRubriqueInformation.getRubriqueInformations());
        }
        
        request.setAttribute("langues", langues);
        request.setAttribute("rubriques", rubriques);
        request.setAttribute("typeRubriques", typeRubriques);
        request.setAttribute("rubriquesPerType", rubriquesPerType);
        
        getServletContext().getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
    }
}
