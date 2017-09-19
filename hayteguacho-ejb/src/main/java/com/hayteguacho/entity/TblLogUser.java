/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TBL_LOG_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLogUser.findAll", query = "SELECT t FROM TblLogUser t"),
    @NamedQuery(name = "TblLogUser.findByIdloguser", query = "SELECT t FROM TblLogUser t WHERE t.idloguser = :idloguser"),
    @NamedQuery(name = "TblLogUser.findByTipolog", query = "SELECT t FROM TblLogUser t WHERE t.tipolog = :tipolog"),
    @NamedQuery(name = "TblLogUser.findByFechalog", query = "SELECT t FROM TblLogUser t WHERE t.fechalog = :fechalog"),
    @NamedQuery(name = "TblLogUser.findByEstadolog", query = "SELECT t FROM TblLogUser t WHERE t.estadolog = :estadolog")})
public class TblLogUser implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDLOGUSER")
    private BigDecimal idloguser;
    @Basic(optional = false)
    @Column(name = "TIPOLOG")
    private String tipolog;
    @Basic(optional = false)
    @Column(name = "FECHALOG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechalog;
    @Basic(optional = false)
    @Column(name = "ESTADOLOG")
    private String estadolog;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private TblEmpresa idempresa;

    public TblLogUser() {
    }

    public TblLogUser(BigDecimal idloguser) {
        this.idloguser = idloguser;
    }

    public TblLogUser(BigDecimal idloguser, String tipolog, Date fechalog, String estadolog) {
        this.idloguser = idloguser;
        this.tipolog = tipolog;
        this.fechalog = fechalog;
        this.estadolog = estadolog;
    }

    public BigDecimal getIdloguser() {
        return idloguser;
    }

    public void setIdloguser(BigDecimal idloguser) {
        this.idloguser = idloguser;
    }

    public String getTipolog() {
        return tipolog;
    }

    public void setTipolog(String tipolog) {
        this.tipolog = tipolog;
    }

    public Date getFechalog() {
        return fechalog;
    }

    public void setFechalog(Date fechalog) {
        this.fechalog = fechalog;
    }

    public String getEstadolog() {
        return estadolog;
    }

    public void setEstadolog(String estadolog) {
        this.estadolog = estadolog;
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
        hash += (idloguser != null ? idloguser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLogUser)) {
            return false;
        }
        TblLogUser other = (TblLogUser) object;
        if ((this.idloguser == null && other.idloguser != null) || (this.idloguser != null && !this.idloguser.equals(other.idloguser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblLogUser[ idloguser=" + idloguser + " ]";
    }
    
}
