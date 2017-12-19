/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import com.admin.hayteguacho.form.OfertaAplicaForm;
import com.admin.hayteguacho.form.UserForm;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_CIUDAD")

@SqlResultSetMapping(
        name = "OfertaAplicaMapping",
        classes = @ConstructorResult(
                targetClass = OfertaAplicaForm.class,
                columns = {
                    @ColumnResult(name = "titulo", type = String.class),
                    @ColumnResult(name = "inscritos", type = String.class),
                    }))

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCiudad.findAll", query = "SELECT t FROM TblCiudad t"),
    @NamedQuery(name = "TblCiudad.findByIdciudad", query = "SELECT t FROM TblCiudad t WHERE t.idciudad = :idciudad"),
    @NamedQuery(name = "TblCiudad.findByNombreciudad", query = "SELECT t FROM TblCiudad t WHERE t.nombreciudad = :nombreciudad")})
public class TblCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idciudad;
    @Basic(optional = false)
    private String nombreciudad;
    @JoinColumn(name = "IDDEPARTAMENTO", referencedColumnName = "IDDEPARTAMENTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblDepartamento iddepartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idciudad", fetch = FetchType.LAZY)
    private List<TblEmpresa> tblEmpresaList;

    public TblCiudad() {
    }

    public TblCiudad(BigDecimal idciudad) {
        this.idciudad = idciudad;
    }

    public TblCiudad(BigDecimal idciudad, String nombreciudad) {
        this.idciudad = idciudad;
        this.nombreciudad = nombreciudad;
    }

    public BigDecimal getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(BigDecimal idciudad) {
        this.idciudad = idciudad;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }

    public TblDepartamento getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(TblDepartamento iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    @XmlTransient
    public List<TblEmpresa> getTblEmpresaList() {
        return tblEmpresaList;
    }

    public void setTblEmpresaList(List<TblEmpresa> tblEmpresaList) {
        this.tblEmpresaList = tblEmpresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idciudad != null ? idciudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCiudad)) {
            return false;
        }
        TblCiudad other = (TblCiudad) object;
        if ((this.idciudad == null && other.idciudad != null) || (this.idciudad != null && !this.idciudad.equals(other.idciudad))) {
            return false;
        }
        return true;
    }
     /*
    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCiudad[ idciudad=" + idciudad + " ]";
    }

    */
    @Override
    public String toString() {
        return idciudad.toString() ;
    }
}
