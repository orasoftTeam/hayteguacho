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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "TBL_MNTO_MODULOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMntoModulos.findAll", query = "SELECT t FROM TblMntoModulos t"),
    @NamedQuery(name = "TblMntoModulos.findByIdmodulo", query = "SELECT t FROM TblMntoModulos t WHERE t.idmodulo = :idmodulo"),
    @NamedQuery(name = "TblMntoModulos.findByNombremodulo", query = "SELECT t FROM TblMntoModulos t WHERE t.nombremodulo = :nombremodulo"),
    @NamedQuery(name = "TblMntoModulos.findByEstadomodulo", query = "SELECT t FROM TblMntoModulos t WHERE t.estadomodulo = :estadomodulo")})
public class TblMntoModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMODULO")
    private BigDecimal idmodulo;
    @Column(name = "NOMBREMODULO")
    private String nombremodulo;
    @Column(name = "ESTADOMODULO")
    private String estadomodulo;
    @JoinTable(name = "TBL_MODULOS_X_ROL", joinColumns = {
        @JoinColumn(name = "IDMODULO", referencedColumnName = "IDMODULO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDROL", referencedColumnName = "IDROL")})
    @ManyToMany
    private List<TblRol> tblRolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmodulo")
    private List<TblMenusXModulo> tblMenusXModuloList;

    public TblMntoModulos() {
    }

    public TblMntoModulos(BigDecimal idmodulo) {
        this.idmodulo = idmodulo;
    }

    public BigDecimal getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(BigDecimal idmodulo) {
        this.idmodulo = idmodulo;
    }

    public String getNombremodulo() {
        return nombremodulo;
    }

    public void setNombremodulo(String nombremodulo) {
        this.nombremodulo = nombremodulo;
    }

    public String getEstadomodulo() {
        return estadomodulo;
    }

    public void setEstadomodulo(String estadomodulo) {
        this.estadomodulo = estadomodulo;
    }

    @XmlTransient
    public List<TblRol> getTblRolList() {
        return tblRolList;
    }

    public void setTblRolList(List<TblRol> tblRolList) {
        this.tblRolList = tblRolList;
    }

    @XmlTransient
    public List<TblMenusXModulo> getTblMenusXModuloList() {
        return tblMenusXModuloList;
    }

    public void setTblMenusXModuloList(List<TblMenusXModulo> tblMenusXModuloList) {
        this.tblMenusXModuloList = tblMenusXModuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodulo != null ? idmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMntoModulos)) {
            return false;
        }
        TblMntoModulos other = (TblMntoModulos) object;
        if ((this.idmodulo == null && other.idmodulo != null) || (this.idmodulo != null && !this.idmodulo.equals(other.idmodulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblMntoModulos[ idmodulo=" + idmodulo + " ]";
    }
    
}
