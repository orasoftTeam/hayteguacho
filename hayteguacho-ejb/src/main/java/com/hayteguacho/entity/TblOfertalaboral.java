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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_OFERTALABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblOfertalaboral.findAll", query = "SELECT t FROM TblOfertalaboral t"),
    @NamedQuery(name = "TblOfertalaboral.findByIdofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.idofertalaboral = :idofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByIdjornadalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.idjornadalaboral = :idjornadalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByIdtipocontracto", query = "SELECT t FROM TblOfertalaboral t WHERE t.idtipocontrato = :idtipocontrato"),
    @NamedQuery(name = "TblOfertalaboral.findByIdpuestotrabajo", query = "SELECT t FROM TblOfertalaboral t WHERE t.idpuestotrabajo = :idpuestotrabajo"),
    @NamedQuery(name = "TblOfertalaboral.findByIdciudad", query = "SELECT t FROM TblOfertalaboral t WHERE t.idciudad = :idciudad"),
    @NamedQuery(name = "TblOfertalaboral.findByTituloofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.tituloofertalaboral = :tituloofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByFechahoraofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.fechahoraofertalaboral = :fechahoraofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByFechavigenciaofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.fechavigenciaofertalaboral = :fechavigenciaofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByFechacontratacionofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.fechacontratacionofertalaboral = :fechacontratacionofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByCantidadvacante", query = "SELECT t FROM TblOfertalaboral t WHERE t.cantidadvacante = :cantidadvacante"),
    @NamedQuery(name = "TblOfertalaboral.findBySalariominofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.salariominofertalaboral = :salariominofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findBySalariomaxofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.salariomaxofertalaboral = :salariomaxofertalaboral"),
    @NamedQuery(name = "TblOfertalaboral.findByPersonacondiscapacidad", query = "SELECT t FROM TblOfertalaboral t WHERE t.personacondiscapacidad = :personacondiscapacidad"),
    @NamedQuery(name = "TblOfertalaboral.findByEstadoofertalaboral", query = "SELECT t FROM TblOfertalaboral t WHERE t.estadoofertalaboral = :estadoofertalaboral")})
public class TblOfertalaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idofertalaboral;
    @Basic(optional = false)
    private BigInteger idjornadalaboral;
    @Basic(optional = false)
    private BigInteger idtipocontrato;
    @Basic(optional = false)
    private BigInteger idpuestotrabajo;
    @Basic(optional = false)
    private BigInteger idciudad;
    @Basic(optional = false)
    private String tituloofertalaboral;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraofertalaboral;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigenciaofertalaboral;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacontratacionofertalaboral;
    @Basic(optional = false)
    private BigInteger cantidadvacante;
    @Basic(optional = false)
    private double salariominofertalaboral;
    @Basic(optional = false)
    private double salariomaxofertalaboral;
    @Basic(optional = false)
    @Lob
    private String descripcionofertalaboral;
    @Basic(optional = false)
    @Lob
    private String requerimientosofertalaboral;
    @Lob
    private String habilidadesofertalaboral;
    @Lob
    private String conocimientoofertalaboral;
    private Character personacondiscapacidad;
    @Basic(optional = false)
    private String estadoofertalaboral;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblEmpresa idempresa;
    
    @Transient
    private @Setter String idempresa_tbl;

    public TblOfertalaboral() {
    }

    public TblOfertalaboral(BigDecimal idofertalaboral) {
        this.idofertalaboral = idofertalaboral;
    }

    public TblOfertalaboral(BigDecimal idofertalaboral, BigInteger idjornadalaboral, BigInteger idtipocontrato, BigInteger idpuestotrabajo, BigInteger idciudad, String tituloofertalaboral, Date fechahoraofertalaboral, Date fechavigenciaofertalaboral, Date fechacontratacionofertalaboral, BigInteger cantidadvacante, double salariominofertalaboral, double salariomaxofertalaboral, String descripcionofertalaboral, String requerimientosofertalaboral, String estadoofertalaboral) {
        this.idofertalaboral = idofertalaboral;
        this.idjornadalaboral = idjornadalaboral;
        this.idtipocontrato = idtipocontrato;
        this.idpuestotrabajo = idpuestotrabajo;
        this.idciudad = idciudad;
        this.tituloofertalaboral = tituloofertalaboral;
        this.fechahoraofertalaboral = fechahoraofertalaboral;
        this.fechavigenciaofertalaboral = fechavigenciaofertalaboral;
        this.fechacontratacionofertalaboral = fechacontratacionofertalaboral;
        this.cantidadvacante = cantidadvacante;
        this.salariominofertalaboral = salariominofertalaboral;
        this.salariomaxofertalaboral = salariomaxofertalaboral;
        this.descripcionofertalaboral = descripcionofertalaboral;
        this.requerimientosofertalaboral = requerimientosofertalaboral;
        this.estadoofertalaboral = estadoofertalaboral;
    }

    public BigDecimal getIdofertalaboral() {
        return idofertalaboral;
    }

    public void setIdofertalaboral(BigDecimal idofertalaboral) {
        this.idofertalaboral = idofertalaboral;
    }

    public BigInteger getIdjornadalaboral() {
        return idjornadalaboral;
    }

    public void setIdjornadalaboral(BigInteger idjornadalaboral) {
        this.idjornadalaboral = idjornadalaboral;
    }

    public BigInteger getIdtipocontrato() {
        return idtipocontrato;
    }

    public void setIdtipocontrato(BigInteger idtipocontrato) {
        this.idtipocontrato = idtipocontrato;
    }

    public BigInteger getIdpuestotrabajo() {
        return idpuestotrabajo;
    }

    public void setIdpuestotrabajo(BigInteger idpuestotrabajo) {
        this.idpuestotrabajo = idpuestotrabajo;
    }

    public BigInteger getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(BigInteger idciudad) {
        this.idciudad = idciudad;
    }

    public String getTituloofertalaboral() {
        return tituloofertalaboral;
    }

    public void setTituloofertalaboral(String tituloofertalaboral) {
        this.tituloofertalaboral = tituloofertalaboral;
    }

    public Date getFechahoraofertalaboral() {
        return fechahoraofertalaboral;
    }

    public void setFechahoraofertalaboral(Date fechahoraofertalaboral) {
        this.fechahoraofertalaboral = fechahoraofertalaboral;
    }

    public Date getFechavigenciaofertalaboral() {
        return fechavigenciaofertalaboral;
    }

    public void setFechavigenciaofertalaboral(Date fechavigenciaofertalaboral) {
        this.fechavigenciaofertalaboral = fechavigenciaofertalaboral;
    }

    public Date getFechacontratacionofertalaboral() {
        return fechacontratacionofertalaboral;
    }

    public void setFechacontratacionofertalaboral(Date fechacontratacionofertalaboral) {
        this.fechacontratacionofertalaboral = fechacontratacionofertalaboral;
    }

    public BigInteger getCantidadvacante() {
        return cantidadvacante;
    }

    public void setCantidadvacante(BigInteger cantidadvacante) {
        this.cantidadvacante = cantidadvacante;
    }

    public double getSalariominofertalaboral() {
        return salariominofertalaboral;
    }

    public void setSalariominofertalaboral(double salariominofertalaboral) {
        this.salariominofertalaboral = salariominofertalaboral;
    }

    public double getSalariomaxofertalaboral() {
        return salariomaxofertalaboral;
    }

    public void setSalariomaxofertalaboral(double salariomaxofertalaboral) {
        this.salariomaxofertalaboral = salariomaxofertalaboral;
    }

    public String getDescripcionofertalaboral() {
        return descripcionofertalaboral;
    }

    public void setDescripcionofertalaboral(String descripcionofertalaboral) {
        this.descripcionofertalaboral = descripcionofertalaboral;
    }

    public String getRequerimientosofertalaboral() {
        return requerimientosofertalaboral;
    }

    public void setRequerimientosofertalaboral(String requerimientosofertalaboral) {
        this.requerimientosofertalaboral = requerimientosofertalaboral;
    }

    public String getHabilidadesofertalaboral() {
        return habilidadesofertalaboral;
    }

    public void setHabilidadesofertalaboral(String habilidadesofertalaboral) {
        this.habilidadesofertalaboral = habilidadesofertalaboral;
    }

    public String getConocimientoofertalaboral() {
        return conocimientoofertalaboral;
    }

    public void setConocimientoofertalaboral(String conocimientoofertalaboral) {
        this.conocimientoofertalaboral = conocimientoofertalaboral;
    }

    public Character getPersonacondiscapacidad() {
        return personacondiscapacidad;
    }

    public void setPersonacondiscapacidad(Character personacondiscapacidad) {
        this.personacondiscapacidad = personacondiscapacidad;
    }

    public String getEstadoofertalaboral() {
        return estadoofertalaboral;
    }

    public void setEstadoofertalaboral(String estadoofertalaboral) {
        this.estadoofertalaboral = estadoofertalaboral;
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
        hash += (idofertalaboral != null ? idofertalaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblOfertalaboral)) {
            return false;
        }
        TblOfertalaboral other = (TblOfertalaboral) object;
        if ((this.idofertalaboral == null && other.idofertalaboral != null) || (this.idofertalaboral != null && !this.idofertalaboral.equals(other.idofertalaboral))) {
            return false;
        }
        return true;
    }

    public String getIdempresa_tbl() {
        return idempresa.getIdempresa().toString();
    }
    


    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblOfertalaboral[ idofertalaboral=" + idofertalaboral + " ]";
    }
    
}
