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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_CHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblChat.findAll", query = "SELECT t FROM TblChat t"),
    @NamedQuery(name = "TblChat.findByIdchat", query = "SELECT t FROM TblChat t WHERE t.idchat = :idchat"),
    @NamedQuery(name = "TblChat.findByIdempresa", query = "SELECT t FROM TblChat t WHERE t.idempresa = :idempresa"),
    @NamedQuery(name = "TblChat.findByIdcandidato", query = "SELECT t FROM TblChat t WHERE t.idcandidato = :idcandidato")})
public class TblChat implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idchat;
    @Basic(optional = false)
    private BigInteger idempresa;
    @Basic(optional = false)
    private BigInteger idcandidato;

    public TblChat() {
    }

    public TblChat(BigDecimal idchat) {
        this.idchat = idchat;
    }

    public TblChat(BigDecimal idchat, BigInteger idempresa, BigInteger idcandidato) {
        this.idchat = idchat;
        this.idempresa = idempresa;
        this.idcandidato = idcandidato;
    }

    public BigDecimal getIdchat() {
        return idchat;
    }

    public void setIdchat(BigDecimal idchat) {
        this.idchat = idchat;
    }

    public BigInteger getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(BigInteger idempresa) {
        this.idempresa = idempresa;
    }

    public BigInteger getIdcandidato() {
        return idcandidato;
    }

    public void setIdcandidato(BigInteger idcandidato) {
        this.idcandidato = idcandidato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idchat != null ? idchat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblChat)) {
            return false;
        }
        TblChat other = (TblChat) object;
        if ((this.idchat == null && other.idchat != null) || (this.idchat != null && !this.idchat.equals(other.idchat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblChat[ idchat=" + idchat + " ]";
    }
    
}
