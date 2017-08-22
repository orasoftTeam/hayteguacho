/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "TBL_CANDIDATOXOFERTALABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCandidatoxofertalaboral.findAll", query = "SELECT t FROM TblCandidatoxofertalaboral t"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findByIdcandidatoxofertalaboral", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.idcandidatoxofertalaboral = :idcandidatoxofertalaboral"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findByIdofertalaboral", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.idofertalaboral = :idofertalaboral"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findByIdcandidato", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.idcandidato = :idcandidato"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findByPretensionsalarial", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.pretensionsalarial = :pretensionsalarial"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findBySituacionlaboral", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.situacionlaboral = :situacionlaboral"),
    @NamedQuery(name = "TblCandidatoxofertalaboral.findByEstadocandidatoxofertalaboral", query = "SELECT t FROM TblCandidatoxofertalaboral t WHERE t.estadocandidatoxofertalaboral = :estadocandidatoxofertalaboral")})
public class TblCandidatoxofertalaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idcandidatoxofertalaboral;
    @Basic(optional = false)
    private BigInteger idofertalaboral;
    @Basic(optional = false)
    private BigInteger idcandidato;
    private Double pretensionsalarial;
    @Basic(optional = false)
    private String situacionlaboral;
    @Basic(optional = false)
    private String estadocandidatoxofertalaboral;

    public TblCandidatoxofertalaboral() {
    }

    public TblCandidatoxofertalaboral(BigDecimal idcandidatoxofertalaboral) {
        this.idcandidatoxofertalaboral = idcandidatoxofertalaboral;
    }

    public TblCandidatoxofertalaboral(BigDecimal idcandidatoxofertalaboral, BigInteger idofertalaboral, BigInteger idcandidato, String situacionlaboral, String estadocandidatoxofertalaboral) {
        this.idcandidatoxofertalaboral = idcandidatoxofertalaboral;
        this.idofertalaboral = idofertalaboral;
        this.idcandidato = idcandidato;
        this.situacionlaboral = situacionlaboral;
        this.estadocandidatoxofertalaboral = estadocandidatoxofertalaboral;
    }

    public BigDecimal getIdcandidatoxofertalaboral() {
        return idcandidatoxofertalaboral;
    }

    public void setIdcandidatoxofertalaboral(BigDecimal idcandidatoxofertalaboral) {
        this.idcandidatoxofertalaboral = idcandidatoxofertalaboral;
    }

    public BigInteger getIdofertalaboral() {
        return idofertalaboral;
    }

    public void setIdofertalaboral(BigInteger idofertalaboral) {
        this.idofertalaboral = idofertalaboral;
    }

    public BigInteger getIdcandidato() {
        return idcandidato;
    }

    public void setIdcandidato(BigInteger idcandidato) {
        this.idcandidato = idcandidato;
    }

    public Double getPretensionsalarial() {
        return pretensionsalarial;
    }

    public void setPretensionsalarial(Double pretensionsalarial) {
        this.pretensionsalarial = pretensionsalarial;
    }

    public String getSituacionlaboral() {
        return situacionlaboral;
    }

    public void setSituacionlaboral(String situacionlaboral) {
        this.situacionlaboral = situacionlaboral;
    }

    public String getEstadocandidatoxofertalaboral() {
        return estadocandidatoxofertalaboral;
    }

    public void setEstadocandidatoxofertalaboral(String estadocandidatoxofertalaboral) {
        this.estadocandidatoxofertalaboral = estadocandidatoxofertalaboral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcandidatoxofertalaboral != null ? idcandidatoxofertalaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCandidatoxofertalaboral)) {
            return false;
        }
        TblCandidatoxofertalaboral other = (TblCandidatoxofertalaboral) object;
        if ((this.idcandidatoxofertalaboral == null && other.idcandidatoxofertalaboral != null) || (this.idcandidatoxofertalaboral != null && !this.idcandidatoxofertalaboral.equals(other.idcandidatoxofertalaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCandidatoxofertalaboral[ idcandidatoxofertalaboral=" + idcandidatoxofertalaboral + " ]";
    }
    
}
