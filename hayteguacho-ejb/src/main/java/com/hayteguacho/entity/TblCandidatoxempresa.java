/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import com.admin.hayteguacho.form.MenuForm;
import com.admin.hayteguacho.form.UserForm;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_CANDIDATOXEMPRESA")

@SqlResultSetMapping(
        name = "MenuMapping",
        classes = @ConstructorResult(
                targetClass = MenuForm.class,
                columns = {
                    @ColumnResult(name = "identificador", type = String.class),
                    @ColumnResult(name = "nombremenu", type = String.class),
                    @ColumnResult(name = "opcion", type = String.class)/*,
                    @ColumnResult(name = "producto", type = String.class)*/}))

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCandidatoxempresa.findAll", query = "SELECT t FROM TblCandidatoxempresa t"),
    @NamedQuery(name = "TblCandidatoxempresa.findByIdcandidatoxempresa", query = "SELECT t FROM TblCandidatoxempresa t WHERE t.idcandidatoxempresa = :idcandidatoxempresa"),
    @NamedQuery(name = "TblCandidatoxempresa.findByIdempresa", query = "SELECT t FROM TblCandidatoxempresa t WHERE t.idempresa = :idempresa"),
    @NamedQuery(name = "TblCandidatoxempresa.findByIdcandidato", query = "SELECT t FROM TblCandidatoxempresa t WHERE t.idcandidato = :idcandidato"),
    @NamedQuery(name = "TblCandidatoxempresa.findByEstadocandxemp", query = "SELECT t FROM TblCandidatoxempresa t WHERE t.estadocandxemp = :estadocandxemp")})
public class TblCandidatoxempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idcandidatoxempresa;
    @Basic(optional = false)
    private BigInteger idempresa;
    @Basic(optional = false)
    private BigInteger idcandidato;
    @Basic(optional = false)
    private Character estadocandxemp;

    public TblCandidatoxempresa() {
    }

    public TblCandidatoxempresa(BigDecimal idcandidatoxempresa) {
        this.idcandidatoxempresa = idcandidatoxempresa;
    }

    public TblCandidatoxempresa(BigDecimal idcandidatoxempresa, BigInteger idempresa, BigInteger idcandidato, Character estadocandxemp) {
        this.idcandidatoxempresa = idcandidatoxempresa;
        this.idempresa = idempresa;
        this.idcandidato = idcandidato;
        this.estadocandxemp = estadocandxemp;
    }

    public BigDecimal getIdcandidatoxempresa() {
        return idcandidatoxempresa;
    }

    public void setIdcandidatoxempresa(BigDecimal idcandidatoxempresa) {
        this.idcandidatoxempresa = idcandidatoxempresa;
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

    public Character getEstadocandxemp() {
        return estadocandxemp;
    }

    public void setEstadocandxemp(Character estadocandxemp) {
        this.estadocandxemp = estadocandxemp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcandidatoxempresa != null ? idcandidatoxempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCandidatoxempresa)) {
            return false;
        }
        TblCandidatoxempresa other = (TblCandidatoxempresa) object;
        if ((this.idcandidatoxempresa == null && other.idcandidatoxempresa != null) || (this.idcandidatoxempresa != null && !this.idcandidatoxempresa.equals(other.idcandidatoxempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCandidatoxempresa[ idcandidatoxempresa=" + idcandidatoxempresa + " ]";
    }
    
}
