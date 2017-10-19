/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;


import com.admin.hayteguacho.form.EmpresaForm;
import com.hayteguacho.entity.TblEmpresa;
import com.hayteguacho.util.facade.AbstractFacade;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Stateless
public class EmpresaFacade extends AbstractFacade<TblEmpresa, EmpresaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public EmpresaFacade() {
        super(TblEmpresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<EmpresaForm> obtenerEmpresa(String idempresa) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_empresa where idempresa=?", TblEmpresa.class);
        q.setParameter(1, new BigInteger(idempresa));
        List<TblEmpresa> listaEntity;
        List<EmpresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<EmpresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new EmpresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<EmpresaForm>();
        }

        return listaEntityForm;
    }
    
    
    public List<EmpresaForm> obtenerEmpresaById(String idempresa) {
        String sql="select idempresa, idciudad, idcategoria, idtipologia, idcargoempresa,\n" +
"                nombreempresa, razonsocialemp, nitempresa, codpostalempresa,\n" +
"                direccionempresa, numtrabajadoresempresa, descripcionempresa,\n" +
"                paginawebempresa, logoempresa, nombrecontactoempresa,\n" +
"                apellcontactoempresa, telefono1contactoempresa,\n" +
"                telefono2contactoempresa, telefono3contactoempresa,\n" +
"                correocontactoempresa,\n" +
"                (SELECT QB_ENCRIPCION.FB_DESCENCRIPTAR(contrasenacontactoempresa)from dual) contrasenacontactoempresa,\n" +
"                estadoempresa, idpais from tbl_empresa where idempresa=?";
        
        Query q = getEntityManager().createNativeQuery(sql, TblEmpresa.class);
        q.setParameter(1, new BigInteger(idempresa));
        List<TblEmpresa> listaEntity;
        List<EmpresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<EmpresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new EmpresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<EmpresaForm>();
        }

        return listaEntityForm;
    }

    /*
    public List<CargoEmpresaForm> obtenerCargos() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_cargoempresa", TblCargoempresa.class);
        List<TblCargoempresa> listaEntity;
        List<CargoEmpresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CargoEmpresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CargoEmpresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CargoEmpresaForm>();
        }

        return listaEntityForm;
    }
        */

    public String actualizarEmpresa(EmpresaForm eform, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_EMPRESA (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(eform.getIdempresa()));
            cs.setInt(2, new Integer(eform.getIdciudad()));
            cs.setInt(3, new Integer(eform.getIdcategoria()));
            cs.setInt(4, new Integer(eform.getIdtipologia()));
            cs.setInt(5, new Integer(eform.getIdcargoempresa()));
            cs.setString(6, eform.getNombreempresa().toUpperCase());
            cs.setString(7, eform.getRazonsocial());
            cs.setString(8, eform.getIdtributaria());
            cs.setString(9, eform.getCodigopostal());
            cs.setString(10, eform.getDireccionempresa());
            cs.setInt(11, new Integer(eform.getNumtrabajadores()));
            cs.setString(12, eform.getDescripcionempresa());
            cs.setString(13, eform.getPaginaweb());
            cs.setString(14, eform.getLogo());
            cs.setString(15, eform.getNombrecontacto());
            cs.setString(16, eform.getApellidocontacto());
            cs.setString(17, eform.getTelefono1());
            cs.setString(18, eform.getTelefono2());
            cs.setString(19, eform.getTelefono3());
            cs.setString(20, eform.getEmail());
            cs.setString(21, eform.getPassword());
            cs.setInt(22, new Integer(eform.getIdpais()));
            cs.setString(23, op);
            cs.registerOutParameter(24, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(24);
            System.out.println(flag);
            System.out.println("El password ingresado fué: "+ eform.getPassword());
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
