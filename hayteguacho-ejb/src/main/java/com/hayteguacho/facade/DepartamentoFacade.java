/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.DepartamentoForm;
import com.hayteguacho.entity.TblDepartamento;
import com.hayteguacho.util.facade.AbstractFacade;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LAP
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<TblDepartamento, DepartamentoForm> {
    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public DepartamentoFacade() {
        super(TblDepartamento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
    public List<DepartamentoForm> obtenerDepartamentos(String idpais) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_departamento where idpais=?", TblDepartamento.class);
        q.setParameter(1, new BigDecimal(idpais));
        List<TblDepartamento> listaEntity;
        List<DepartamentoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<DepartamentoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new DepartamentoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<DepartamentoForm>();
        }

        return listaEntityForm;
    }
    
    
    public List<DepartamentoForm> obtenerDepartamentoByIdCiudad(String idciudad) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_departamento dep, tbl_ciudad mun where dep.iddepartamento= mun.iddepartamento and mun.idciudad=?", TblDepartamento.class);
        q.setParameter(1, new BigDecimal(idciudad));
        List<TblDepartamento> listaEntity;
        List<DepartamentoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<DepartamentoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new DepartamentoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<DepartamentoForm>();
        }

        return listaEntityForm;
    }
    
    public String actualizarDepartamento(DepartamentoForm df,String idpais, String op) {
        String flag = "";
        String nomDepto="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            if(op.equals("D")){
                nomDepto= df.getNombredepartamento();
            }
            else{
                nomDepto= df.getNombredepartamento().substring(0,1).toUpperCase().concat(df.getNombredepartamento().substring(1, df.getNombredepartamento().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_DEPARTAMENTO (?,?,?,?,?)}");
            cs.setString(1, nomDepto);
            cs.setString(2, op);
            cs.setInt(3, new Integer(idpais));
            cs.setInt(4, new Integer(df.getIddepartamento()));
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(5);
            System.out.println(flag);
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(OK)");
        } catch (Exception e) {
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(ERROR)");
            flag="-2";
            e.printStackTrace();
        }
        return flag;
    }
    
}
