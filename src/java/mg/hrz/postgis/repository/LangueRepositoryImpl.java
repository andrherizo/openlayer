/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.repository;

import javax.enterprise.context.RequestScoped;

import mg.hrz.postgis.donnee.domainobject.Langue;
import mg.hrz.postgis.repository.commun.AbstractCommunRepository;
import javax.inject.Named;

@Named
@RequestScoped
public class LangueRepositoryImpl extends AbstractCommunRepository<Langue> implements LangueRepository {
    
}
