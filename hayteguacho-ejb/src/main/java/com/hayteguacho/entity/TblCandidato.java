/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.entity;

import com.admin.hayteguacho.form.UserForm;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Entity
@Table(name = "TBL_CANDIDATO")

@SqlResultSetMapping(
        name = "UsuarioMapping",
        classes = @ConstructorResult(
                targetClass = UserForm.class,
                columns = {
                    @ColumnResult(name = "tipo", type = String.class),
                    @ColumnResult(name = "identificador", type = String.class),
                    @ColumnResult(name = "nombre", type = String.class),
                    @ColumnResult(name = "correo", type = String.class),
                    @ColumnResult(name = "contrasena", type = String.class),
                    @ColumnResult(name = "nombrerol", type = String.class),
                    @ColumnResult(name = "idrol", type = String.class),
                    @ColumnResult(name = "idrolusuario", type = String.class)/*,
                    @ColumnResult(name = "producto", type = String.class)*/}))

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCandidato.findAll", query = "SELECT t FROM TblCandidato t"),
    @NamedQuery(name = "TblCandidato.findByIdcandidato", query = "SELECT t FROM TblCandidato t WHERE t.idcandidato = :idcandidato"),
    @NamedQuery(name = "TblCandidato.findByNombrecandidato", query = "SELECT t FROM TblCandidato t WHERE t.nombrecandidato = :nombrecandidato"),
    @NamedQuery(name = "TblCandidato.findByApellidocandidato", query = "SELECT t FROM TblCandidato t WHERE t.apellidocandidato = :apellidocandidato"),
    @NamedQuery(name = "TblCandidato.findByTelefono1candidato", query = "SELECT t FROM TblCandidato t WHERE t.telefono1candidato = :telefono1candidato"),
    @NamedQuery(name = "TblCandidato.findByTelefono2candidato", query = "SELECT t FROM TblCandidato t WHERE t.telefono2candidato = :telefono2candidato"),
    @NamedQuery(name = "TblCandidato.findByGenerocandidato", query = "SELECT t FROM TblCandidato t WHERE t.generocandidato = :generocandidato"),
    @NamedQuery(name = "TblCandidato.findByFechanacimientocandidato", query = "SELECT t FROM TblCandidato t WHERE t.fechanacimientocandidato = :fechanacimientocandidato"),
    @NamedQuery(name = "TblCandidato.findByArchivocurriculum", query = "SELECT t FROM TblCandidato t WHERE t.archivocurriculum = :archivocurriculum"),
    @NamedQuery(name = "TblCandidato.findByCorreocandidato", query = "SELECT t FROM TblCandidato t WHERE t.correocandidato = :correocandidato"),
    @NamedQuery(name = "TblCandidato.findByContrasenacandidato", query = "SELECT t FROM TblCandidato t WHERE t.contrasenacandidato = :contrasenacandidato"),
    @NamedQuery(name = "TblCandidato.findByEstadocandidato", query = "SELECT t FROM TblCandidato t WHERE t.estadocandidato = :estadocandidato")})
public class TblCandidato implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    private BigDecimal idcandidato;
    @Basic(optional = false)
    private String nombrecandidato;
    @Basic(optional = false)
    private String apellidocandidato;
    @Basic(optional = false)
    private String telefono1candidato;
    private String telefono2candidato;
    private Character generocandidato;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechanacimientocandidato;
    @Basic(optional = false)
    private String archivocurriculum;
    @Basic(optional = false)
    private String correocandidato;
    @Basic(optional = false)
    private String contrasenacandidato;
    @Basic(optional = false)
    private Character estadocandidato;
    @JoinColumn(name = "IDPUESTOTRABAJO", referencedColumnName = "IDPUESTOTRABAJO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblPuestotrabajo idpuestotrabajo;
    @JoinColumn(name = "IDPAIS", referencedColumnName = "IDPAIS")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblPais idpais;
    
    private @Setter String idpaistbl;
    private @Setter String idpuestotrabajotbl;

    public TblCandidato() {
    }

    public TblCandidato(BigDecimal idcandidato) {
        this.idcandidato = idcandidato;
    }

    public TblCandidato(BigDecimal idcandidato, String nombrecandidato, String apellidocandidato, String telefono1candidato, String archivocurriculum, String correocandidato, String contrasenacandidato, Character estadocandidato) {
        this.idcandidato = idcandidato;
        this.nombrecandidato = nombrecandidato;
        this.apellidocandidato = apellidocandidato;
        this.telefono1candidato = telefono1candidato;
        this.archivocurriculum = archivocurriculum;
        this.correocandidato = correocandidato;
        this.contrasenacandidato = contrasenacandidato;
        this.estadocandidato = estadocandidato;
    }

    public BigDecimal getIdcandidato() {
        return idcandidato;
    }

    public void setIdcandidato(BigDecimal idcandidato) {
        this.idcandidato = idcandidato;
    }

    public String getNombrecandidato() {
        return nombrecandidato;
    }

    public void setNombrecandidato(String nombrecandidato) {
        this.nombrecandidato = nombrecandidato;
    }

    public String getApellidocandidato() {
        return apellidocandidato;
    }

    public void setApellidocandidato(String apellidocandidato) {
        this.apellidocandidato = apellidocandidato;
    }

    public String getTelefono1candidato() {
        return telefono1candidato;
    }

    public void setTelefono1candidato(String telefono1candidato) {
        this.telefono1candidato = telefono1candidato;
    }

    public String getTelefono2candidato() {
        return telefono2candidato;
    }

    public void setTelefono2candidato(String telefono2candidato) {
        this.telefono2candidato = telefono2candidato;
    }

    public Character getGenerocandidato() {
        return generocandidato;
    }

    public void setGenerocandidato(Character generocandidato) {
        this.generocandidato = generocandidato;
    }

    public Date getFechanacimientocandidato() {
        return fechanacimientocandidato;
    }

    public void setFechanacimientocandidato(Date fechanacimientocandidato) {
        this.fechanacimientocandidato = fechanacimientocandidato;
    }

    public String getArchivocurriculum() {
        return archivocurriculum;
    }

    public void setArchivocurriculum(String archivocurriculum) {
        this.archivocurriculum = archivocurriculum;
    }

    public String getCorreocandidato() {
        return correocandidato;
    }

    public void setCorreocandidato(String correocandidato) {
        this.correocandidato = correocandidato;
    }

    public String getContrasenacandidato() {
        return contrasenacandidato;
    }

    public void setContrasenacandidato(String contrasenacandidato) {
        this.contrasenacandidato = contrasenacandidato;
    }

    public Character getEstadocandidato() {
        return estadocandidato;
    }

    public void setEstadocandidato(Character estadocandidato) {
        this.estadocandidato = estadocandidato;
    }

    public TblPuestotrabajo getIdpuestotrabajo() {
        return idpuestotrabajo;
    }

    public void setIdpuestotrabajo(TblPuestotrabajo idpuestotrabajo) {
        this.idpuestotrabajo = idpuestotrabajo;
    }

    public TblPais getIdpais() {
        return idpais;
    }

    public void setIdpais(TblPais idpais) {
        this.idpais = idpais;
    }

    public String getIdpuestotrabajotbl() {
        return String.valueOf(idpuestotrabajo.getIdpuestotrabajo());
    }

    public String getIdpaistbl() {
        return String.valueOf(idpais.getIdpais());
    }
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcandidato != null ? idcandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCandidato)) {
            return false;
        }
        TblCandidato other = (TblCandidato) object;
        if ((this.idcandidato == null && other.idcandidato != null) || (this.idcandidato != null && !this.idcandidato.equals(other.idcandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hayteguacho.entity.TblCandidato[ idcandidato=" + idcandidato + " ]";
    }
    
}
