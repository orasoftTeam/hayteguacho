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
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "TBL_CARGOEMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCargoempresa.findAll", query = "SELECT t FROM TblCargoempresa t"),
    @NamedQuery(name = "TblCargoempresa.findByIdcargoempresa", query = "SELECT t FROM TblCargoempresa t WHERE t.idcargoempresa = :idcargoempresa"),
    @NamedQuery(name = "TblCargoempresa.findByNombrecargoempresa", query = "SELECT t FROM TblCargoempresa t WHERE t.nombrecargoempresa = :nombrecargoempresa")})
public class TblCargoempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idcargoempresa;
    @Basic(optional = false)
    private String nombrecargoempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcargoempresa", fetch = FetchType.LAZY)
    private List<TblEmpresa> tblEmpresaList;

    public TblCargoempresa() {
    }

    public TblCargoempresa(BigDecimal idcargoempresa) {
        this.idcargoempresa = idcargoempresa;
    }

    public TblCargoempresa(BigDecimal idcargoempresa, String nombrecargoempresa) {
        this.idcargoempresa = idcargoempresa;
        this.nombrecargoempresa = nombrecargoempresa;
    }

    public BigDecimal getIdcargoempresa() {
        return idcargoempresa;
    }

    public void setIdcargoempresa(BigDecimal idcargoempresa) {
        this.idcargoempresa = idcargoempresa;
    }

    public String getNombrecargoempresa() {
        return nombrecargoempresa;
    }

    public void setNombrecargoempresa(String nombrecargoempresa) {
        this.nombrecargoempresa = nombrecargoempresa;
    }

    @XmlTransient
    public List<TblEmpresa> getTblEmpresaList() {
        return tblEmpresaList;
    }

    public void setTblEmpresaList(List<TblEmpresa> tblEmpresaList) {
        this.tblEmpresaList = tblEmpresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcargoempresa != null ? idcargoempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCargoempresa)) {
            return false;
        }
        TblCargoempresa other = (TblCargoempresa) object;
        if ((this.idcargoempresa == null && other.idcargoempresa != null) || (this.idcargoempresa != null && !this.idcargoempresa.equals(other.idcargoempresa))) {
            return false;
        }
        return true;
    }
    /*
    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCargoempresa[ idcargoempresa=" + idcargoempresa + " ]";
    }
    */
    
    @Override
    public String toString() {
        return idcargoempresa.toString();
    }
}
