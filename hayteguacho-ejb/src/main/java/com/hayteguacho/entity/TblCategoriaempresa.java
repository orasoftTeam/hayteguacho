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
@Table(name = "TBL_CATEGORIAEMPRESA")
@XmlRootElement


@NamedQueries({
    @NamedQuery(name = "TblCategoriaempresa.findAll", query = "SELECT t FROM TblCategoriaempresa t"),
    @NamedQuery(name = "TblCategoriaempresa.findByIdcategoria", query = "SELECT t FROM TblCategoriaempresa t WHERE t.idcategoria = :idcategoria"),
    @NamedQuery(name = "TblCategoriaempresa.findByNombrecategoria", query = "SELECT t FROM TblCategoriaempresa t WHERE t.nombrecategoria = :nombrecategoria")})
public class TblCategoriaempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idcategoria;
    @Basic(optional = false)
    private String nombrecategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcategoria", fetch = FetchType.LAZY)
    private List<TblEmpresa> tblEmpresaList;

    public TblCategoriaempresa() {
    }

    public TblCategoriaempresa(BigDecimal idcategoria) {
        this.idcategoria = idcategoria;
    }

    public TblCategoriaempresa(BigDecimal idcategoria, String nombrecategoria) {
        this.idcategoria = idcategoria;
        this.nombrecategoria = nombrecategoria;
    }

    public BigDecimal getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(BigDecimal idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
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
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCategoriaempresa)) {
            return false;
        }
        TblCategoriaempresa other = (TblCategoriaempresa) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }
    /*
    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCategoriaempresa[ idcategoria=" + idcategoria + " ]";
    }
    */
    
    @Override
    public String toString() {
        return idcategoria.toString();
    }
}
