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
@Table(name = "TBL_TIPOLOGIAEMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipologiaempresa.findAll", query = "SELECT t FROM TblTipologiaempresa t"),
    @NamedQuery(name = "TblTipologiaempresa.findByIdtipologia", query = "SELECT t FROM TblTipologiaempresa t WHERE t.idtipologia = :idtipologia"),
    @NamedQuery(name = "TblTipologiaempresa.findByNombretipologia", query = "SELECT t FROM TblTipologiaempresa t WHERE t.nombretipologia = :nombretipologia")})
public class TblTipologiaempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idtipologia;
    @Basic(optional = false)
    private String nombretipologia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipologia", fetch = FetchType.LAZY)
    private List<TblEmpresa> tblEmpresaList;

    public TblTipologiaempresa() {
    }

    public TblTipologiaempresa(BigDecimal idtipologia) {
        this.idtipologia = idtipologia;
    }

    public TblTipologiaempresa(BigDecimal idtipologia, String nombretipologia) {
        this.idtipologia = idtipologia;
        this.nombretipologia = nombretipologia;
    }

    public BigDecimal getIdtipologia() {
        return idtipologia;
    }

    public void setIdtipologia(BigDecimal idtipologia) {
        this.idtipologia = idtipologia;
    }

    public String getNombretipologia() {
        return nombretipologia;
    }

    public void setNombretipologia(String nombretipologia) {
        this.nombretipologia = nombretipologia;
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
        hash += (idtipologia != null ? idtipologia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTipologiaempresa)) {
            return false;
        }
        TblTipologiaempresa other = (TblTipologiaempresa) object;
        if ((this.idtipologia == null && other.idtipologia != null) || (this.idtipologia != null && !this.idtipologia.equals(other.idtipologia))) {
            return false;
        }
        return true;
    }
    /*
    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblTipologiaempresa[ idtipologia=" + idtipologia + " ]";
    }
    */
    @Override
    public String toString() {
        return idtipologia.toString();
    }
}
