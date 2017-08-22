/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "TBL_PUESTOTRABAJO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPuestotrabajo.findAll", query = "SELECT t FROM TblPuestotrabajo t"),
    @NamedQuery(name = "TblPuestotrabajo.findByIdpuestotrabajo", query = "SELECT t FROM TblPuestotrabajo t WHERE t.idpuestotrabajo = :idpuestotrabajo"),
    @NamedQuery(name = "TblPuestotrabajo.findByIdcategoria", query = "SELECT t FROM TblPuestotrabajo t WHERE t.idcategoria = :idcategoria"),
    @NamedQuery(name = "TblPuestotrabajo.findByNombrepuestotrabajo", query = "SELECT t FROM TblPuestotrabajo t WHERE t.nombrepuestotrabajo = :nombrepuestotrabajo")})
public class TblPuestotrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idpuestotrabajo;
    @Basic(optional = false)
    private BigInteger idcategoria;
    @Basic(optional = false)
    private String nombrepuestotrabajo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpuestotrabajo", fetch = FetchType.LAZY)
    private List<TblCandidato> tblCandidatoList;

    public TblPuestotrabajo() {
    }

    public TblPuestotrabajo(BigDecimal idpuestotrabajo) {
        this.idpuestotrabajo = idpuestotrabajo;
    }

    public TblPuestotrabajo(BigDecimal idpuestotrabajo, BigInteger idcategoria, String nombrepuestotrabajo) {
        this.idpuestotrabajo = idpuestotrabajo;
        this.idcategoria = idcategoria;
        this.nombrepuestotrabajo = nombrepuestotrabajo;
    }

    public BigDecimal getIdpuestotrabajo() {
        return idpuestotrabajo;
    }

    public void setIdpuestotrabajo(BigDecimal idpuestotrabajo) {
        this.idpuestotrabajo = idpuestotrabajo;
    }

    public BigInteger getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(BigInteger idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrepuestotrabajo() {
        return nombrepuestotrabajo;
    }

    public void setNombrepuestotrabajo(String nombrepuestotrabajo) {
        this.nombrepuestotrabajo = nombrepuestotrabajo;
    }

    @XmlTransient
    public List<TblCandidato> getTblCandidatoList() {
        return tblCandidatoList;
    }

    public void setTblCandidatoList(List<TblCandidato> tblCandidatoList) {
        this.tblCandidatoList = tblCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpuestotrabajo != null ? idpuestotrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPuestotrabajo)) {
            return false;
        }
        TblPuestotrabajo other = (TblPuestotrabajo) object;
        if ((this.idpuestotrabajo == null && other.idpuestotrabajo != null) || (this.idpuestotrabajo != null && !this.idpuestotrabajo.equals(other.idpuestotrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblPuestotrabajo[ idpuestotrabajo=" + idpuestotrabajo + " ]";
    }
    
}
