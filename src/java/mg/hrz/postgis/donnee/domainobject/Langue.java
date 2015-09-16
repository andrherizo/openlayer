/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.donnee.domainobject;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "lan_langue")
public class Langue implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "code_langue", nullable = false, unique = true)
    private String codeLangue;
    
    @Column(name = "libelle", nullable = false)
    private String libelle;

    public String getCodeLangue() {
        return codeLangue;
    }

    public void setCodeLangue(String codeLangue) {
        this.codeLangue = codeLangue;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
