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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "TBL_MEMBRESIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMembresia.findAll", query = "SELECT t FROM TblMembresia t"),
    @NamedQuery(name = "TblMembresia.findByIdmembresia", query = "SELECT t FROM TblMembresia t WHERE t.idmembresia = :idmembresia"),
    @NamedQuery(name = "TblMembresia.findByTitulomembresia", query = "SELECT t FROM TblMembresia t WHERE t.titulomembresia = :titulomembresia"),
    @NamedQuery(name = "TblMembresia.findByPreciomembresia", query = "SELECT t FROM TblMembresia t WHERE t.preciomembresia = :preciomembresia"),
    @NamedQuery(name = "TblMembresia.findByPrecioxoferta", query = "SELECT t FROM TblMembresia t WHERE t.precioxoferta = :precioxoferta"),
    @NamedQuery(name = "TblMembresia.findByCantidadoferta", query = "SELECT t FROM TblMembresia t WHERE t.cantidadoferta = :cantidadoferta")})
public class TblMembresia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMEMBRESIA")
    private BigDecimal idmembresia;
    @Basic(optional = false)
    @Column(name = "TITULOMEMBRESIA")
    private String titulomembresia;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCIONMEMBRESIA")
    private String descripcionmembresia;
    @Basic(optional = false)
    @Lob
    @Column(name = "VENTAJASDEMEMBRESIA")
    private String ventajasdemembresia;
    @Basic(optional = false)
    @Column(name = "PRECIOMEMBRESIA")
    private double preciomembresia;
    @Column(name = "PRECIOXOFERTA")
    private Double precioxoferta;
    @Column(name = "CANTIDADOFERTA")
    private BigInteger cantidadoferta;
    private BigInteger cantidaduser;
    private String estadomembresia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmembresia")
    private List<TblMembresiaxempresa> tblMembresiaxempresaList;
    @JoinColumn(name = "IDTIPOPERIODOMEMBRESIA", referencedColumnName = "IDTIPOPERIODOMEMBRESIA")
    @ManyToOne(optional = false)
    private TblTipoperidomembresia idtipoperiodomembresia;

    public String getEstadomembresia() {
        return estadomembresia;
    }

    public void setEstadomembresia(String estadomembresia) {
        this.estadomembresia = estadomembresia;
    }
    

    public TblMembresia() {
    }

    public TblMembresia(BigDecimal idmembresia) {
        this.idmembresia = idmembresia;
    }

    public TblMembresia(BigDecimal idmembresia, String titulomembresia, String descripcionmembresia, String ventajasdemembresia, double preciomembresia) {
        this.idmembresia = idmembresia;
        this.titulomembresia = titulomembresia;
        this.descripcionmembresia = descripcionmembresia;
        this.ventajasdemembresia = ventajasdemembresia;
        this.preciomembresia = preciomembresia;
    }

    public BigDecimal getIdmembresia() {
        return idmembresia;
    }

    public void setIdmembresia(BigDecimal idmembresia) {
        this.idmembresia = idmembresia;
    }

    public String getTitulomembresia() {
        return titulomembresia;
    }

    public void setTitulomembresia(String titulomembresia) {
        this.titulomembresia = titulomembresia;
    }

    public String getDescripcionmembresia() {
        return descripcionmembresia;
    }

    public void setDescripcionmembresia(String descripcionmembresia) {
        this.descripcionmembresia = descripcionmembresia;
    }

    public String getVentajasdemembresia() {
        return ventajasdemembresia;
    }

    public void setVentajasdemembresia(String ventajasdemembresia) {
        this.ventajasdemembresia = ventajasdemembresia;
    }

    public double getPreciomembresia() {
        return preciomembresia;
    }

    public void setPreciomembresia(double preciomembresia) {
        this.preciomembresia = preciomembresia;
    }

    public Double getPrecioxoferta() {
        return precioxoferta;
    }

    public void setPrecioxoferta(Double precioxoferta) {
        this.precioxoferta = precioxoferta;
    }

    public BigInteger getCantidadoferta() {
        return cantidadoferta;
    }

    public void setCantidadoferta(BigInteger cantidadoferta) {
        this.cantidadoferta = cantidadoferta;
    }

    @XmlTransient
    public List<TblMembresiaxempresa> getTblMembresiaxempresaList() {
        return tblMembresiaxempresaList;
    }

    public void setTblMembresiaxempresaList(List<TblMembresiaxempresa> tblMembresiaxempresaList) {
        this.tblMembresiaxempresaList = tblMembresiaxempresaList;
    }

    public TblTipoperidomembresia getIdtipoperiodomembresia() {
        return idtipoperiodomembresia;
    }

    public void setIdtipoperiodomembresia(TblTipoperidomembresia idtipoperiodomembresia) {
        this.idtipoperiodomembresia = idtipoperiodomembresia;
    }

    public BigInteger getCantidaduser() {
        return cantidaduser;
    }

    public void setCantidaduser(BigInteger cantidaduser) {
        this.cantidaduser = cantidaduser;
    }

   /* @XmlTransient
    public List<TblEmpresa> getTblEmpresaList() {
        return tblEmpresaList;
    }

    public void setTblEmpresaList(List<TblEmpresa> tblEmpresaList) {
        this.tblEmpresaList = tblEmpresaList;
    }*/


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmembresia != null ? idmembresia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMembresia)) {
            return false;
        }
        TblMembresia other = (TblMembresia) object;
        if ((this.idmembresia == null && other.idmembresia != null) || (this.idmembresia != null && !this.idmembresia.equals(other.idmembresia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblMembresia[ idmembresia=" + idmembresia + " ]";
    }
    
}
