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
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "TBL_CHATCANDIXOFERTALABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblChatcandixofertalaboral.findAll", query = "SELECT t FROM TblChatcandixofertalaboral t"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByIdchatcandixofertalaboral", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.idchatcandixofertalaboral = :idchatcandixofertalaboral"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByIdofertalaboral", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.idofertalaboral = :idofertalaboral"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByIdchat", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.idchat = :idchat"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByFechahora", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByUserde", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.userde = :userde"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByUserpara", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.userpara = :userpara"),
    @NamedQuery(name = "TblChatcandixofertalaboral.findByEstadochat", query = "SELECT t FROM TblChatcandixofertalaboral t WHERE t.estadochat = :estadochat")})
public class TblChatcandixofertalaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idchatcandixofertalaboral;
    @Basic(optional = false)
    private BigInteger idofertalaboral;
    @Basic(optional = false)
    private BigInteger idchat;
    @Basic(optional = false)
    @Lob
    private String mensaje;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    private String userde;
    @Basic(optional = false)
    private String userpara;
    private Character estadochat;

    public TblChatcandixofertalaboral() {
    }

    public TblChatcandixofertalaboral(BigDecimal idchatcandixofertalaboral) {
        this.idchatcandixofertalaboral = idchatcandixofertalaboral;
    }

    public TblChatcandixofertalaboral(BigDecimal idchatcandixofertalaboral, BigInteger idofertalaboral, BigInteger idchat, String mensaje, Date fechahora, String userde, String userpara) {
        this.idchatcandixofertalaboral = idchatcandixofertalaboral;
        this.idofertalaboral = idofertalaboral;
        this.idchat = idchat;
        this.mensaje = mensaje;
        this.fechahora = fechahora;
        this.userde = userde;
        this.userpara = userpara;
    }

    public BigDecimal getIdchatcandixofertalaboral() {
        return idchatcandixofertalaboral;
    }

    public void setIdchatcandixofertalaboral(BigDecimal idchatcandixofertalaboral) {
        this.idchatcandixofertalaboral = idchatcandixofertalaboral;
    }

    public BigInteger getIdofertalaboral() {
        return idofertalaboral;
    }

    public void setIdofertalaboral(BigInteger idofertalaboral) {
        this.idofertalaboral = idofertalaboral;
    }

    public BigInteger getIdchat() {
        return idchat;
    }

    public void setIdchat(BigInteger idchat) {
        this.idchat = idchat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getUserde() {
        return userde;
    }

    public void setUserde(String userde) {
        this.userde = userde;
    }

    public String getUserpara() {
        return userpara;
    }

    public void setUserpara(String userpara) {
        this.userpara = userpara;
    }

    public Character getEstadochat() {
        return estadochat;
    }

    public void setEstadochat(Character estadochat) {
        this.estadochat = estadochat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idchatcandixofertalaboral != null ? idchatcandixofertalaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblChatcandixofertalaboral)) {
            return false;
        }
        TblChatcandixofertalaboral other = (TblChatcandixofertalaboral) object;
        if ((this.idchatcandixofertalaboral == null && other.idchatcandixofertalaboral != null) || (this.idchatcandixofertalaboral != null && !this.idchatcandixofertalaboral.equals(other.idchatcandixofertalaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblChatcandixofertalaboral[ idchatcandixofertalaboral=" + idchatcandixofertalaboral + " ]";
    }
    
}
