/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TBL_ROLUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRolusuario.findAll", query = "SELECT t FROM TblRolusuario t"),
    @NamedQuery(name = "TblRolusuario.findByIdrolusuario", query = "SELECT t FROM TblRolusuario t WHERE t.idrolusuario = :idrolusuario"),
    @NamedQuery(name = "TblRolusuario.findByEmailusuario", query = "SELECT t FROM TblRolusuario t WHERE t.emailusuario = :emailusuario"),
    @NamedQuery(name = "TblRolusuario.findByEstadorolusuario", query = "SELECT t FROM TblRolusuario t WHERE t.estadorolusuario = :estadorolusuario")})
public class TblRolusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDROLUSUARIO")
    private BigDecimal idrolusuario;
    @Column(name = "EMAILUSUARIO")
    private String emailusuario;
    @Column(name = "ESTADOROLUSUARIO")
    private String estadorolusuario;
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL")
    @ManyToOne(optional = false)
    private TblRol idrol;

    public TblRolusuario() {
    }

    public TblRolusuario(BigDecimal idrolusuario) {
        this.idrolusuario = idrolusuario;
    }

    public BigDecimal getIdrolusuario() {
        return idrolusuario;
    }

    public void setIdrolusuario(BigDecimal idrolusuario) {
        this.idrolusuario = idrolusuario;
    }

    public String getEmailusuario() {
        return emailusuario;
    }

    public void setEmailusuario(String emailusuario) {
        this.emailusuario = emailusuario;
    }

    public String getEstadorolusuario() {
        return estadorolusuario;
    }

    public void setEstadorolusuario(String estadorolusuario) {
        this.estadorolusuario = estadorolusuario;
    }

    public TblRol getIdrol() {
        return idrol;
    }

    public void setIdrol(TblRol idrol) {
        this.idrol = idrol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrolusuario != null ? idrolusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRolusuario)) {
            return false;
        }
        TblRolusuario other = (TblRolusuario) object;
        if ((this.idrolusuario == null && other.idrolusuario != null) || (this.idrolusuario != null && !this.idrolusuario.equals(other.idrolusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.entity.TblRolusuario[ idrolusuario=" + idrolusuario + " ]";
    }
    
}
