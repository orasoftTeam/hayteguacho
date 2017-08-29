/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_TIPOCONTRATOLABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipocontractolaboral.findAll", query = "SELECT t FROM TblTipocontratolaboral t"),
    @NamedQuery(name = "TblTipocontractolaboral.findByIdtipocontracto", query = "SELECT t FROM TblTipocontratolaboral t WHERE t.idtipocontrato = :idtipocontrato"),
    @NamedQuery(name = "TblTipocontractolaboral.findByNombretipocontacto", query = "SELECT t FROM TblTipocontratolaboral t WHERE t.nombretipocontrato = :nombretipocontrato")})
public class TblTipocontratolaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Getter @Setter
    private BigDecimal idtipocontrato;
    @Basic(optional = false)
    @Getter @Setter
    private String nombretipocontrato;

    public TblTipocontratolaboral() {
    }

    public TblTipocontratolaboral(BigDecimal idtipocontrato) {
        this.idtipocontrato = idtipocontrato;
    }

    public TblTipocontratolaboral(BigDecimal idtipocontrato, String nombretipocontrato) {
        this.idtipocontrato = idtipocontrato;
        this.nombretipocontrato = nombretipocontrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocontrato != null ? idtipocontrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTipocontratolaboral)) {
            return false;
        }
        TblTipocontratolaboral other = (TblTipocontratolaboral) object;
        if ((this.idtipocontrato == null && other.idtipocontrato != null) || (this.idtipocontrato != null && !this.idtipocontrato.equals(other.idtipocontrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblTipocontractolaboral[ idtipocontracto=" + idtipocontrato + " ]";
    }
    
}
