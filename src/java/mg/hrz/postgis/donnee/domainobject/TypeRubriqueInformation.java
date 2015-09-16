/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.donnee.domainobject;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tri_type_rubinfo")
public class TypeRubriqueInformation implements Serializable {

    private static final long serialVersionUID = 7055555432348247583L;
    @Id
    @Column(name = "id_typerubinfo", unique = true, nullable = false)
    private Integer idTypeRubriqueInfo;
    
    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;
    
    @OneToMany(mappedBy = "typeRubriqueInfo")
    private Set<RubriqueInformation> rubriqueInformations;

    public Integer getIdTypeRubriqueInfo() {
        return idTypeRubriqueInfo;
    }

    public void setIdTypeRubriqueInfo(Integer idTypeRubriqueInfo) {
        this.idTypeRubriqueInfo = idTypeRubriqueInfo;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<RubriqueInformation> getRubriqueInformations() {
        return rubriqueInformations;
    }

    public void setRubriqueInformations(
            Set<RubriqueInformation> rubriqueInformations) {
        this.rubriqueInformations = rubriqueInformations;
    }
}
