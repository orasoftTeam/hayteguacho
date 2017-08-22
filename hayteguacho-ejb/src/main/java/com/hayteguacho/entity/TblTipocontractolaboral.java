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

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_TIPOCONTRACTOLABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipocontractolaboral.findAll", query = "SELECT t FROM TblTipocontractolaboral t"),
    @NamedQuery(name = "TblTipocontractolaboral.findByIdtipocontracto", query = "SELECT t FROM TblTipocontractolaboral t WHERE t.idtipocontracto = :idtipocontracto"),
    @NamedQuery(name = "TblTipocontractolaboral.findByNombretipocontacto", query = "SELECT t FROM TblTipocontractolaboral t WHERE t.nombretipocontacto = :nombretipocontacto")})
public class TblTipocontractolaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idtipocontracto;
    @Basic(optional = false)
    private String nombretipocontacto;

    public TblTipocontractolaboral() {
    }

    public TblTipocontractolaboral(BigDecimal idtipocontracto) {
        this.idtipocontracto = idtipocontracto;
    }

    public TblTipocontractolaboral(BigDecimal idtipocontracto, String nombretipocontacto) {
        this.idtipocontracto = idtipocontracto;
        this.nombretipocontacto = nombretipocontacto;
    }

    public BigDecimal getIdtipocontracto() {
        return idtipocontracto;
    }

    public void setIdtipocontracto(BigDecimal idtipocontracto) {
        this.idtipocontracto = idtipocontracto;
    }

    public String getNombretipocontacto() {
        return nombretipocontacto;
    }

    public void setNombretipocontacto(String nombretipocontacto) {
        this.nombretipocontacto = nombretipocontacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocontracto != null ? idtipocontracto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTipocontractolaboral)) {
            return false;
        }
        TblTipocontractolaboral other = (TblTipocontractolaboral) object;
        if ((this.idtipocontracto == null && other.idtipocontracto != null) || (this.idtipocontracto != null && !this.idtipocontracto.equals(other.idtipocontracto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblTipocontractolaboral[ idtipocontracto=" + idtipocontracto + " ]";
    }
    
}
