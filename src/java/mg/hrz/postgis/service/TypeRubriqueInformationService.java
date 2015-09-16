/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.service;

import java.util.Set;

import mg.hrz.postgis.donnee.domainobject.RubriqueInformation;

public interface TypeRubriqueInformationService {
    
    public Set<RubriqueInformation> getRubriques(int idType);
    public int getNextId();
}
