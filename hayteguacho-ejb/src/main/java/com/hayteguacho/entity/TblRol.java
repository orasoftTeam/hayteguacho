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
@Table(name = "TBL_ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRol.findAll", query = "SELECT t FROM TblRol t"),
    @NamedQuery(name = "TblRol.findByIdrol", query = "SELECT t FROM TblRol t WHERE t.idrol = :idrol"),
    @NamedQuery(name = "TblRol.findByNombrerol", query = "SELECT t FROM TblRol t WHERE t.nombrerol = :nombrerol"),
    @NamedQuery(name = "TblRol.findByEstadorol", query = "SELECT t FROM TblRol t WHERE t.estadorol = :estadorol")})
public class TblRol implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDROL")
    private BigDecimal idrol;
    @Basic(optional = false)
    @Column(name = "NOMBREROL")
    private String nombrerol;
    @Column(name = "ESTADOROL")
    private String estadorol;
    @ManyToMany(mappedBy = "tblRolList")
    private List<TblMntoModulos> tblMntoModulosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrol")
    private List<TblRolusuario> tblRolusuarioList;

    public TblRol() {
    }

    public TblRol(BigDecimal idrol) {
        this.idrol = idrol;
    }

    public TblRol(BigDecimal idrol, String nombrerol) {
        this.idrol = idrol;
        this.nombrerol = nombrerol;
    }

    public BigDecimal getIdrol() {
        return idrol;
    }

    public void setIdrol(BigDecimal idrol) {
        this.idrol = idrol;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }

    public String getEstadorol() {
        return estadorol;
    }

    public void setEstadorol(String estadorol) {
        this.estadorol = estadorol;
    }

    @XmlTransient
    public List<TblMntoModulos> getTblMntoModulosList() {
        return tblMntoModulosList;
    }

    public void setTblMntoModulosList(List<TblMntoModulos> tblMntoModulosList) {
        this.tblMntoModulosList = tblMntoModulosList;
    }

    @XmlTransient
    public List<TblRolusuario> getTblRolusuarioList() {
        return tblRolusuarioList;
    }

    public void setTblRolusuarioList(List<TblRolusuario> tblRolusuarioList) {
        this.tblRolusuarioList = tblRolusuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrol != null ? idrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRol)) {
            return false;
        }
        TblRol other = (TblRol) object;
        if ((this.idrol == null && other.idrol != null) || (this.idrol != null && !this.idrol.equals(other.idrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblRol[ idrol=" + idrol + " ]";
    }
    
}
