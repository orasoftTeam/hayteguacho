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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TBL_DEPARTAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDepartamento.findAll", query = "SELECT t FROM TblDepartamento t"),
    @NamedQuery(name = "TblDepartamento.findByIddepartamento", query = "SELECT t FROM TblDepartamento t WHERE t.iddepartamento = :iddepartamento"),
    @NamedQuery(name = "TblDepartamento.findByNombredepartamento", query = "SELECT t FROM TblDepartamento t WHERE t.nombredepartamento = :nombredepartamento")})
public class TblDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal iddepartamento;
    @Basic(optional = false)
    private String nombredepartamento;
    @JoinColumn(name = "IDPAIS", referencedColumnName = "IDPAIS")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblPais idpais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddepartamento", fetch = FetchType.LAZY)
    private List<TblCiudad> tblCiudadList;

    public TblDepartamento() {
    }

    public TblDepartamento(BigDecimal iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public TblDepartamento(BigDecimal iddepartamento, String nombredepartamento) {
        this.iddepartamento = iddepartamento;
        this.nombredepartamento = nombredepartamento;
    }

    public BigDecimal getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(BigDecimal iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getNombredepartamento() {
        return nombredepartamento;
    }

    public void setNombredepartamento(String nombredepartamento) {
        this.nombredepartamento = nombredepartamento;
    }

    public TblPais getIdpais() {
        return idpais;
    }

    public void setIdpais(TblPais idpais) {
        this.idpais = idpais;
    }

    @XmlTransient
    public List<TblCiudad> getTblCiudadList() {
        return tblCiudadList;
    }

    public void setTblCiudadList(List<TblCiudad> tblCiudadList) {
        this.tblCiudadList = tblCiudadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepartamento != null ? iddepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDepartamento)) {
            return false;
        }
        TblDepartamento other = (TblDepartamento) object;
        if ((this.iddepartamento == null && other.iddepartamento != null) || (this.iddepartamento != null && !this.iddepartamento.equals(other.iddepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblDepartamento[ iddepartamento=" + iddepartamento + " ]";
    }
    
}
