/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.service;

import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mg.hrz.postgis.donnee.domainobject.RubriqueInformation;
import mg.hrz.postgis.donnee.domainobject.TypeRubriqueInformation;
import mg.hrz.postgis.repository.TypeRubriqueInformationRepository;

@Named
@RequestScoped
public class TypeRubriqueInformationServiceImpl implements TypeRubriqueInformationService {

    @Inject TypeRubriqueInformationRepository typeRubriqueInformationRepository;
    
    @Override
    public Set<RubriqueInformation> getRubriques(int idType) {
        TypeRubriqueInformation type = typeRubriqueInformationRepository.findById(idType);
        return type.getRubriqueInformations();
    }

    @Override
    public int getNextId() {
        return typeRubriqueInformationRepository.countAllRows() + 1;
    }
    
}
