/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.presentation;

import java.io.IOException;
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

public class LangueControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Inject
    LangueRepository langueRepository;
    @Inject
    TypeRubriqueInformationRepository typeRubriqueInformationRepository;
    @Inject
    RubriqueInformationRepository rubriqueInformationRepository;
    @Inject
    TypeRubriqueInformationService typeRubriqueInformationService;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        String libelle = request.getParameter("libelle");
        if (code != null && !code.isEmpty() && libelle != null && !libelle.isEmpty()) {
            Langue langue = new Langue();
            langue.setCodeLangue(code);
            langue.setLibelle(libelle);
            langueRepository.insert(langue);
        }

        String typeRubrique = request.getParameter("typerubrique");
        String rubrique1 = request.getParameter("rubrique1");
        String rubrique2 = request.getParameter("rubrique2");
        if (typeRubrique != null && !typeRubrique.isEmpty()) {
            TypeRubriqueInformation type = new TypeRubriqueInformation();
            type.setLibelle(typeRubrique);
            type.setIdTypeRubriqueInfo(typeRubriqueInformationService.getNextId());
            typeRubriqueInformationRepository.insert(type);

            if (rubrique1 != null && !rubrique1.isEmpty()) {
                RubriqueInformation rub1 = new RubriqueInformation();
                rub1.setIdTypeRubriqueInfo(type.getIdTypeRubriqueInfo());
                rub1.setCode(rubrique1);
                rub1.setTypeRubriqueInfo(type);
                rubriqueInformationRepository.insert(rub1);
            }

            if (rubrique2 != null && !rubrique2.isEmpty()) {
                RubriqueInformation rub2 = new RubriqueInformation();
                rub2.setIdTypeRubriqueInfo(type.getIdTypeRubriqueInfo());
                rub2.setCode(rubrique2);
                rub2.setTypeRubriqueInfo(type);
                rubriqueInformationRepository.insert(rub2);
            }
            
            response.sendRedirect("success");        }
    }
}
