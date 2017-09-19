/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_TIPOPERIDOMEMBRESIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipoperidomembresia.findAll", query = "SELECT t FROM TblTipoperidomembresia t"),
    @NamedQuery(name = "TblTipoperidomembresia.findByIdtipoperiodomembresia", query = "SELECT t FROM TblTipoperidomembresia t WHERE t.idtipoperiodomembresia = :idtipoperiodomembresia"),
    @NamedQuery(name = "TblTipoperidomembresia.findByNombretipoperiodomembresia", query = "SELECT t FROM TblTipoperidomembresia t WHERE t.nombretipoperiodomembresia = :nombretipoperiodomembresia")})
public class TblTipoperidomembresia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPOPERIODOMEMBRESIA")
    private BigDecimal idtipoperiodomembresia;
    @Basic(optional = false)
    @Column(name = "NOMBRETIPOPERIODOMEMBRESIA")
    private String nombretipoperiodomembresia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipoperiodomembresia")
    private List<TblMembresia> tblMembresiaList;

    public TblTipoperidomembresia() {
    }

    public TblTipoperidomembresia(BigDecimal idtipoperiodomembresia) {
        this.idtipoperiodomembresia = idtipoperiodomembresia;
    }

    public TblTipoperidomembresia(BigDecimal idtipoperiodomembresia, String nombretipoperiodomembresia) {
        this.idtipoperiodomembresia = idtipoperiodomembresia;
        this.nombretipoperiodomembresia = nombretipoperiodomembresia;
    }

    public BigDecimal getIdtipoperiodomembresia() {
        return idtipoperiodomembresia;
    }

    public void setIdtipoperiodomembresia(BigDecimal idtipoperiodomembresia) {
        this.idtipoperiodomembresia = idtipoperiodomembresia;
    }

    public String getNombretipoperiodomembresia() {
        return nombretipoperiodomembresia;
    }

    public void setNombretipoperiodomembresia(String nombretipoperiodomembresia) {
        this.nombretipoperiodomembresia = nombretipoperiodomembresia;
    }

    @XmlTransient
    public List<TblMembresia> getTblMembresiaList() {
        return tblMembresiaList;
    }

    public void setTblMembresiaList(List<TblMembresia> tblMembresiaList) {
        this.tblMembresiaList = tblMembresiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoperiodomembresia != null ? idtipoperiodomembresia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTipoperidomembresia)) {
            return false;
        }
        TblTipoperidomembresia other = (TblTipoperidomembresia) object;
        if ((this.idtipoperiodomembresia == null && other.idtipoperiodomembresia != null) || (this.idtipoperiodomembresia != null && !this.idtipoperiodomembresia.equals(other.idtipoperiodomembresia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(idtipoperiodomembresia);
    }
    
}
