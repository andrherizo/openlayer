/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.hrz.postgis.donnee.domainobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rub_rubriqueinformation")
public class RubriqueInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rubinfo", unique = true, nullable = false)
    private Integer idRubriqueInfo;
    
    @Column(name = "code_rubinfo", unique = true, length = 50, nullable = false)
    private String code;
    
    @Column(name = "id_typerubinfo", nullable = false)
    private Integer idTypeRubriqueInfo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_typerubinfo", nullable = false, insertable = false, updatable = false)
    private TypeRubriqueInformation typeRubriqueInfo;

    public RubriqueInformation() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Integer getIdRubriqueInfo() {
        return idRubriqueInfo;
    }

    public void setIdRubriqueInfo(Integer idRubriqueInfo) {
        this.idRubriqueInfo = idRubriqueInfo;
    }

    public TypeRubriqueInformation getTypeRubriqueInfo() {
        return typeRubriqueInfo;
    }

    public void setTypeRubriqueInfo(TypeRubriqueInformation typeRubriqueInfo) {
        this.typeRubriqueInfo = typeRubriqueInfo;
    }

    public Integer getIdTypeRubriqueInfo() {
        return idTypeRubriqueInfo;
    }

    public void setIdTypeRubriqueInfo(Integer idTypeRubriqueInfo) {
        this.idTypeRubriqueInfo = idTypeRubriqueInfo;
    }
}
