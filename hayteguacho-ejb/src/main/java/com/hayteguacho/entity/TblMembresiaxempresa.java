/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_MEMBRESIAXEMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMembresiaxempresa.findAll", query = "SELECT t FROM TblMembresiaxempresa t"),
    @NamedQuery(name = "TblMembresiaxempresa.findByIdmembresiaxempresa", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.idmembresiaxempresa = :idmembresiaxempresa"),
    @NamedQuery(name = "TblMembresiaxempresa.findByPrecioxofertasextra", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.precioxofertasextra = :precioxofertasextra"),
    @NamedQuery(name = "TblMembresiaxempresa.findByCantidadofertasextra", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.cantidadofertasextra = :cantidadofertasextra"),
    @NamedQuery(name = "TblMembresiaxempresa.findByCantidadcontratada", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.cantidadcontratada = :cantidadcontratada"),
    @NamedQuery(name = "TblMembresiaxempresa.findByFechainicio", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.fechainicio = :fechainicio"),
    @NamedQuery(name = "TblMembresiaxempresa.findByFechavencimiento", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.fechavencimiento = :fechavencimiento"),
    @NamedQuery(name = "TblMembresiaxempresa.findByFechacancelacion", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.fechacancelacion = :fechacancelacion"),
    @NamedQuery(name = "TblMembresiaxempresa.findByEstado", query = "SELECT t FROM TblMembresiaxempresa t WHERE t.estado = :estado")})
public class TblMembresiaxempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMEMBRESIAXEMPRESA")
    private BigDecimal idmembresiaxempresa;
    @Basic(optional = false)
    @Column(name = "PRECIOXOFERTASEXTRA")
    private double precioxofertasextra;
    @Basic(optional = false)
    @Column(name = "CANTIDADOFERTASEXTRA")
    private BigInteger cantidadofertasextra;
    @Basic(optional = false)
    @Column(name = "CANTIDADCONTRATADA")
    private BigInteger cantidadcontratada;
    @Basic(optional = false)
    @Column(name = "FECHAINICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Basic(optional = false)
    @Column(name = "FECHAVENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencimiento;
    
    @Column(name = "FECHACANCELACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacancelacion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "IDMEMBRESIA", referencedColumnName = "IDMEMBRESIA")
    @ManyToOne(optional = false)
    private TblMembresia idmembresia;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private TblEmpresa idempresa;

    public TblMembresiaxempresa() {
    }

    public TblMembresiaxempresa(BigDecimal idmembresiaxempresa) {
        this.idmembresiaxempresa = idmembresiaxempresa;
    }

    public TblMembresiaxempresa(BigDecimal idmembresiaxempresa, double precioxofertasextra, BigInteger cantidadofertasextra, BigInteger cantidadcontratada, Date fechainicio, Date fechavencimiento, Date fechacancelacion, String estado) {
        this.idmembresiaxempresa = idmembresiaxempresa;
        this.precioxofertasextra = precioxofertasextra;
        this.cantidadofertasextra = cantidadofertasextra;
        this.cantidadcontratada = cantidadcontratada;
        this.fechainicio = fechainicio;
        this.fechavencimiento = fechavencimiento;
        this.fechacancelacion = fechacancelacion;
        this.estado = estado;
    }

    public BigDecimal getIdmembresiaxempresa() {
        return idmembresiaxempresa;
    }

    public void setIdmembresiaxempresa(BigDecimal idmembresiaxempresa) {
        this.idmembresiaxempresa = idmembresiaxempresa;
    }

    public double getPrecioxofertasextra() {
        return precioxofertasextra;
    }

    public void setPrecioxofertasextra(double precioxofertasextra) {
        this.precioxofertasextra = precioxofertasextra;
    }

    public BigInteger getCantidadofertasextra() {
        return cantidadofertasextra;
    }

    public void setCantidadofertasextra(BigInteger cantidadofertasextra) {
        this.cantidadofertasextra = cantidadofertasextra;
    }

    public BigInteger getCantidadcontratada() {
        return cantidadcontratada;
    }

    public void setCantidadcontratada(BigInteger cantidadcontratada) {
        this.cantidadcontratada = cantidadcontratada;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public Date getFechacancelacion() {
        return fechacancelacion;
    }

    public void setFechacancelacion(Date fechacancelacion) {
        this.fechacancelacion = fechacancelacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TblMembresia getIdmembresia() {
        return idmembresia;
    }

    public void setIdmembresia(TblMembresia idmembresia) {
        this.idmembresia = idmembresia;
    }

    public TblEmpresa getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(TblEmpresa idempresa) {
        this.idempresa = idempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmembresiaxempresa != null ? idmembresiaxempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMembresiaxempresa)) {
            return false;
        }
        TblMembresiaxempresa other = (TblMembresiaxempresa) object;
        if ((this.idmembresiaxempresa == null && other.idmembresiaxempresa != null) || (this.idmembresiaxempresa != null && !this.idmembresiaxempresa.equals(other.idmembresiaxempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblMembresiaxempresa[ idmembresiaxempresa=" + idmembresiaxempresa + " ]";
    }
    
}
