/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.MembresiaForm;
import com.admin.hayteguacho.form.TipoPeriodoMembresiaForm;
import com.hayteguacho.entity.TblTipoperidomembresia;
import com.hayteguacho.util.facade.AbstractFacade;
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
 * @author dell
 */
@Stateless
public class TipoPeriodoMembresiaFacade extends AbstractFacade<TblTipoperidomembresia,TipoPeriodoMembresiaForm>{

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;
    
    public TipoPeriodoMembresiaFacade(){
     super(TblTipoperidomembresia.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
    return em;
    }
    
    public List<TipoPeriodoMembresiaForm> obtenerTiposPeriodosMembresia(){
    Query q = getEntityManager().createNativeQuery("select * from tbl_tipoperidomembresia",TblTipoperidomembresia.class);
    List<TblTipoperidomembresia> listaEntity;
    List<TipoPeriodoMembresiaForm> listaForm;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaForm = new ArrayList<TipoPeriodoMembresiaForm>();
            } else {
             listaForm = this.entityToDtoList(listaEntity, new TipoPeriodoMembresiaForm());
            }
        } catch (Exception e) {
            listaForm = new ArrayList<TipoPeriodoMembresiaForm>();
        }
    
    
    return listaForm;
    }
    
    public String actualizarTipoPeriodoMembresia(TipoPeriodoMembresiaForm tpm, String op){
    String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call hayteguacho.proc_actualizar_tipoperiodo(?,?,?,?)}");
            cs.setString(1, tpm.getNombretipoperiodomembresia());
            cs.setString(2, op);
            cs.setInt(3, new Integer(tpm.getIdtipoperiodomembresia().toUpperCase()));
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
        } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.TipoPeriodoMembresiaFacade.actualizarTipoPeriodoMembresia()");
            e.printStackTrace();
        }
    
    return flag;
    }
    public String getNombreTipoPeriodoMembresia(MembresiaForm mf){
        String sql = "select * from tbl_tipoperidomembresia where idtipoperiodomembresia = " + mf.getIdtipoperiodomembresia();
     Query q = getEntityManager().createNativeQuery(sql, TblTipoperidomembresia.class);
        List<TblTipoperidomembresia> listaEntity;
        List<TipoPeriodoMembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<TipoPeriodoMembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new TipoPeriodoMembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<TipoPeriodoMembresiaForm>();
        }

        return listaEntityForm.get(0).getNombretipoperiodomembresia();
     }
    
    public  TipoPeriodoMembresiaForm getTipoPeriodoMembresia(MembresiaForm mf){
        String sql = "select * from tbl_tipoperidomembresia where idtipoperiodomembresia = " + mf.getIdtipoperiodomembresia();
     Query q = getEntityManager().createNativeQuery(sql, TblTipoperidomembresia.class);
        List<TblTipoperidomembresia> listaEntity;
        List<TipoPeriodoMembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<TipoPeriodoMembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new TipoPeriodoMembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<TipoPeriodoMembresiaForm>();
        }

        return listaEntityForm.get(0);
     }
}
