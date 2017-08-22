/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TBL_JORNADALABORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblJornadalaboral.findAll", query = "SELECT t FROM TblJornadalaboral t"),
    @NamedQuery(name = "TblJornadalaboral.findByIdjornadalaboral", query = "SELECT t FROM TblJornadalaboral t WHERE t.idjornadalaboral = :idjornadalaboral"),
    @NamedQuery(name = "TblJornadalaboral.findByNombrejornadalaboral", query = "SELECT t FROM TblJornadalaboral t WHERE t.nombrejornadalaboral = :nombrejornadalaboral")})
public class TblJornadalaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idjornadalaboral;
    @Basic(optional = false)
    private String nombrejornadalaboral;

    public TblJornadalaboral() {
    }

    public TblJornadalaboral(BigDecimal idjornadalaboral) {
        this.idjornadalaboral = idjornadalaboral;
    }

    public TblJornadalaboral(BigDecimal idjornadalaboral, String nombrejornadalaboral) {
        this.idjornadalaboral = idjornadalaboral;
        this.nombrejornadalaboral = nombrejornadalaboral;
    }

    public BigDecimal getIdjornadalaboral() {
        return idjornadalaboral;
    }

    public void setIdjornadalaboral(BigDecimal idjornadalaboral) {
        this.idjornadalaboral = idjornadalaboral;
    }

    public String getNombrejornadalaboral() {
        return nombrejornadalaboral;
    }

    public void setNombrejornadalaboral(String nombrejornadalaboral) {
        this.nombrejornadalaboral = nombrejornadalaboral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjornadalaboral != null ? idjornadalaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblJornadalaboral)) {
            return false;
        }
        TblJornadalaboral other = (TblJornadalaboral) object;
        if ((this.idjornadalaboral == null && other.idjornadalaboral != null) || (this.idjornadalaboral != null && !this.idjornadalaboral.equals(other.idjornadalaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblJornadalaboral[ idjornadalaboral=" + idjornadalaboral + " ]";
    }
    
}
