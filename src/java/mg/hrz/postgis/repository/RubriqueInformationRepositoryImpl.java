/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import mg.hrz.postgis.donnee.domainobject.RubriqueInformation;
import mg.hrz.postgis.repository.commun.AbstractCommunRepository;

@Named
@RequestScoped
public class RubriqueInformationRepositoryImpl extends AbstractCommunRepository<RubriqueInformation> implements RubriqueInformationRepository {
    
}
