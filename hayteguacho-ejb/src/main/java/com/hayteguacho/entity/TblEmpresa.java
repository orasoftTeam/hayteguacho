/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "TBL_EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEmpresa.findAll", query = "SELECT t FROM TblEmpresa t"),
    @NamedQuery(name = "TblEmpresa.findByIdempresa", query = "SELECT t FROM TblEmpresa t WHERE t.idempresa = :idempresa"),
    @NamedQuery(name = "TblEmpresa.findByNombreempresa", query = "SELECT t FROM TblEmpresa t WHERE t.nombreempresa = :nombreempresa"),
    @NamedQuery(name = "TblEmpresa.findByRazonsocialemp", query = "SELECT t FROM TblEmpresa t WHERE t.razonsocialemp = :razonsocialemp"),
    @NamedQuery(name = "TblEmpresa.findByNitempresa", query = "SELECT t FROM TblEmpresa t WHERE t.nitempresa = :nitempresa"),
    @NamedQuery(name = "TblEmpresa.findByCodpostalempresa", query = "SELECT t FROM TblEmpresa t WHERE t.codpostalempresa = :codpostalempresa"),
    @NamedQuery(name = "TblEmpresa.findByDireccionempresa", query = "SELECT t FROM TblEmpresa t WHERE t.direccionempresa = :direccionempresa"),
    @NamedQuery(name = "TblEmpresa.findByNumtrabajadoresempresa", query = "SELECT t FROM TblEmpresa t WHERE t.numtrabajadoresempresa = :numtrabajadoresempresa"),
    @NamedQuery(name = "TblEmpresa.findByPaginawebempresa", query = "SELECT t FROM TblEmpresa t WHERE t.paginawebempresa = :paginawebempresa"),
    @NamedQuery(name = "TblEmpresa.findByLogoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.logoempresa = :logoempresa"),
    @NamedQuery(name = "TblEmpresa.findByNombrecontactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.nombrecontactoempresa = :nombrecontactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByApellcontactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.apellcontactoempresa = :apellcontactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByTelefono1contactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.telefono1contactoempresa = :telefono1contactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByTelefono2contactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.telefono2contactoempresa = :telefono2contactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByTelefono3contactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.telefono3contactoempresa = :telefono3contactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByCorreocontactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.correocontactoempresa = :correocontactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByContrasenacontactoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.contrasenacontactoempresa = :contrasenacontactoempresa"),
    @NamedQuery(name = "TblEmpresa.findByEstadoempresa", query = "SELECT t FROM TblEmpresa t WHERE t.estadoempresa = :estadoempresa")})
public class TblEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idempresa;
    @Basic(optional = false)
    private String nombreempresa;
    @Basic(optional = false)
    private String razonsocialemp;
    @Basic(optional = false)
    private String nitempresa;
    @Basic(optional = false)
    private String codpostalempresa;
    @Basic(optional = false)
    private String direccionempresa;
    @Basic(optional = false)
    private String numtrabajadoresempresa;
    @Basic(optional = false)
    @Lob
    private String descripcionempresa;
    private String paginawebempresa;
    private String logoempresa;
    @Basic(optional = false)
    private String nombrecontactoempresa;
    @Basic(optional = false)
    private String apellcontactoempresa;
    @Basic(optional = false)
    private String telefono1contactoempresa;
    private String telefono2contactoempresa;
    private String telefono3contactoempresa;
    @Basic(optional = false)
    private String correocontactoempresa;
    @Basic(optional = false)
    private String contrasenacontactoempresa;
    @Basic(optional = false)
    private Character estadoempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa", fetch = FetchType.LAZY)
    private List<TblOfertalaboral> tblOfertalaboralList;
    @JoinColumn(name = "IDTIPOLOGIA", referencedColumnName = "IDTIPOLOGIA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblTipologiaempresa idtipologia;
    @JoinColumn(name = "IDMEMBRESIA", referencedColumnName = "IDMEMBRESIA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblMembresia idmembresia;
    @JoinColumn(name = "IDCIUDAD", referencedColumnName = "IDCIUDAD")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCiudad idciudad;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCategoriaempresa idcategoria;
    @JoinColumn(name = "IDCARGOEMPRESA", referencedColumnName = "IDCARGOEMPRESA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCargoempresa idcargoempresa;

    public TblEmpresa() {
    }

    public TblEmpresa(BigDecimal idempresa) {
        this.idempresa = idempresa;
    }

    public TblEmpresa(BigDecimal idempresa, String nombreempresa, String razonsocialemp, String nitempresa, String codpostalempresa, String direccionempresa, String numtrabajadoresempresa, String descripcionempresa, String nombrecontactoempresa, String apellcontactoempresa, String telefono1contactoempresa, String correocontactoempresa, String contrasenacontactoempresa, Character estadoempresa) {
        this.idempresa = idempresa;
        this.nombreempresa = nombreempresa;
        this.razonsocialemp = razonsocialemp;
        this.nitempresa = nitempresa;
        this.codpostalempresa = codpostalempresa;
        this.direccionempresa = direccionempresa;
        this.numtrabajadoresempresa = numtrabajadoresempresa;
        this.descripcionempresa = descripcionempresa;
        this.nombrecontactoempresa = nombrecontactoempresa;
        this.apellcontactoempresa = apellcontactoempresa;
        this.telefono1contactoempresa = telefono1contactoempresa;
        this.correocontactoempresa = correocontactoempresa;
        this.contrasenacontactoempresa = contrasenacontactoempresa;
        this.estadoempresa = estadoempresa;
    }

    public BigDecimal getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(BigDecimal idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombreempresa() {
        return nombreempresa;
    }

    public void setNombreempresa(String nombreempresa) {
        this.nombreempresa = nombreempresa;
    }

    public String getRazonsocialemp() {
        return razonsocialemp;
    }

    public void setRazonsocialemp(String razonsocialemp) {
        this.razonsocialemp = razonsocialemp;
    }

    public String getNitempresa() {
        return nitempresa;
    }

    public void setNitempresa(String nitempresa) {
        this.nitempresa = nitempresa;
    }

    public String getCodpostalempresa() {
        return codpostalempresa;
    }

    public void setCodpostalempresa(String codpostalempresa) {
        this.codpostalempresa = codpostalempresa;
    }

    public String getDireccionempresa() {
        return direccionempresa;
    }

    public void setDireccionempresa(String direccionempresa) {
        this.direccionempresa = direccionempresa;
    }

    public String getNumtrabajadoresempresa() {
        return numtrabajadoresempresa;
    }

    public void setNumtrabajadoresempresa(String numtrabajadoresempresa) {
        this.numtrabajadoresempresa = numtrabajadoresempresa;
    }

    public String getDescripcionempresa() {
        return descripcionempresa;
    }

    public void setDescripcionempresa(String descripcionempresa) {
        this.descripcionempresa = descripcionempresa;
    }

    public String getPaginawebempresa() {
        return paginawebempresa;
    }

    public void setPaginawebempresa(String paginawebempresa) {
        this.paginawebempresa = paginawebempresa;
    }

    public String getLogoempresa() {
        return logoempresa;
    }

    public void setLogoempresa(String logoempresa) {
        this.logoempresa = logoempresa;
    }

    public String getNombrecontactoempresa() {
        return nombrecontactoempresa;
    }

    public void setNombrecontactoempresa(String nombrecontactoempresa) {
        this.nombrecontactoempresa = nombrecontactoempresa;
    }

    public String getApellcontactoempresa() {
        return apellcontactoempresa;
    }

    public void setApellcontactoempresa(String apellcontactoempresa) {
        this.apellcontactoempresa = apellcontactoempresa;
    }

    public String getTelefono1contactoempresa() {
        return telefono1contactoempresa;
    }

    public void setTelefono1contactoempresa(String telefono1contactoempresa) {
        this.telefono1contactoempresa = telefono1contactoempresa;
    }

    public String getTelefono2contactoempresa() {
        return telefono2contactoempresa;
    }

    public void setTelefono2contactoempresa(String telefono2contactoempresa) {
        this.telefono2contactoempresa = telefono2contactoempresa;
    }

    public String getTelefono3contactoempresa() {
        return telefono3contactoempresa;
    }

    public void setTelefono3contactoempresa(String telefono3contactoempresa) {
        this.telefono3contactoempresa = telefono3contactoempresa;
    }

    public String getCorreocontactoempresa() {
        return correocontactoempresa;
    }

    public void setCorreocontactoempresa(String correocontactoempresa) {
        this.correocontactoempresa = correocontactoempresa;
    }

    public String getContrasenacontactoempresa() {
        return contrasenacontactoempresa;
    }

    public void setContrasenacontactoempresa(String contrasenacontactoempresa) {
        this.contrasenacontactoempresa = contrasenacontactoempresa;
    }

    public Character getEstadoempresa() {
        return estadoempresa;
    }

    public void setEstadoempresa(Character estadoempresa) {
        this.estadoempresa = estadoempresa;
    }

    @XmlTransient
    public List<TblOfertalaboral> getTblOfertalaboralList() {
        return tblOfertalaboralList;
    }

    public void setTblOfertalaboralList(List<TblOfertalaboral> tblOfertalaboralList) {
        this.tblOfertalaboralList = tblOfertalaboralList;
    }

    public TblTipologiaempresa getIdtipologia() {
        return idtipologia;
    }

    public void setIdtipologia(TblTipologiaempresa idtipologia) {
        this.idtipologia = idtipologia;
    }

    public TblMembresia getIdmembresia() {
        return idmembresia;
    }

    public void setIdmembresia(TblMembresia idmembresia) {
        this.idmembresia = idmembresia;
    }

    public TblCiudad getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(TblCiudad idciudad) {
        this.idciudad = idciudad;
    }

    public TblCategoriaempresa getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(TblCategoriaempresa idcategoria) {
        this.idcategoria = idcategoria;
    }

    public TblCargoempresa getIdcargoempresa() {
        return idcargoempresa;
    }

    public void setIdcargoempresa(TblCargoempresa idcargoempresa) {
        this.idcargoempresa = idcargoempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempresa != null ? idempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblEmpresa)) {
            return false;
        }
        TblEmpresa other = (TblEmpresa) object;
        if ((this.idempresa == null && other.idempresa != null) || (this.idempresa != null && !this.idempresa.equals(other.idempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblEmpresa[ idempresa=" + idempresa + " ]";
    }
    
}
