/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import mg.hrz.postgis.donnee.domainobject.TypeRubriqueInformation;
import mg.hrz.postgis.repository.commun.AbstractCommunRepository;

@Named
@RequestScoped
public class TypeRubriqueInformationRepositoryImpl extends AbstractCommunRepository<TypeRubriqueInformation> implements TypeRubriqueInformationRepository {
    
}
