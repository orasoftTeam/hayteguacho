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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_MENUS_X_MODULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMenusXModulo.findAll", query = "SELECT t FROM TblMenusXModulo t"),
    @NamedQuery(name = "TblMenusXModulo.findByIdmenuxmodulo", query = "SELECT t FROM TblMenusXModulo t WHERE t.idmenuxmodulo = :idmenuxmodulo"),
    @NamedQuery(name = "TblMenusXModulo.findByNombremenu", query = "SELECT t FROM TblMenusXModulo t WHERE t.nombremenu = :nombremenu"),
    @NamedQuery(name = "TblMenusXModulo.findByEstadomenu", query = "SELECT t FROM TblMenusXModulo t WHERE t.estadomenu = :estadomenu"),
    @NamedQuery(name = "TblMenusXModulo.findByNivel", query = "SELECT t FROM TblMenusXModulo t WHERE t.nivel = :nivel"),
    @NamedQuery(name = "TblMenusXModulo.findBySecuencia", query = "SELECT t FROM TblMenusXModulo t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TblMenusXModulo.findByOpcion", query = "SELECT t FROM TblMenusXModulo t WHERE t.opcion = :opcion")})
public class TblMenusXModulo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMENUXMODULO")
    private BigDecimal idmenuxmodulo;
    @Column(name = "NOMBREMENU")
    private String nombremenu;
    @Column(name = "ESTADOMENU")
    private String estadomenu;
    @Column(name = "NIVEL")
    private BigInteger nivel;
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "OPCION")
    private String opcion;
    @JoinColumn(name = "IDMODULO", referencedColumnName = "IDMODULO")
    @ManyToOne(optional = false)
    private TblMntoModulos idmodulo;

    public TblMenusXModulo() {
    }

    public TblMenusXModulo(BigDecimal idmenuxmodulo) {
        this.idmenuxmodulo = idmenuxmodulo;
    }

    public BigDecimal getIdmenuxmodulo() {
        return idmenuxmodulo;
    }

    public void setIdmenuxmodulo(BigDecimal idmenuxmodulo) {
        this.idmenuxmodulo = idmenuxmodulo;
    }

    public String getNombremenu() {
        return nombremenu;
    }

    public void setNombremenu(String nombremenu) {
        this.nombremenu = nombremenu;
    }

    public String getEstadomenu() {
        return estadomenu;
    }

    public void setEstadomenu(String estadomenu) {
        this.estadomenu = estadomenu;
    }

    public BigInteger getNivel() {
        return nivel;
    }

    public void setNivel(BigInteger nivel) {
        this.nivel = nivel;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public TblMntoModulos getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(TblMntoModulos idmodulo) {
        this.idmodulo = idmodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenuxmodulo != null ? idmenuxmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMenusXModulo)) {
            return false;
        }
        TblMenusXModulo other = (TblMenusXModulo) object;
        if ((this.idmenuxmodulo == null && other.idmenuxmodulo != null) || (this.idmenuxmodulo != null && !this.idmenuxmodulo.equals(other.idmenuxmodulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblMenusXModulo[ idmenuxmodulo=" + idmenuxmodulo + " ]";
    }
    
}
