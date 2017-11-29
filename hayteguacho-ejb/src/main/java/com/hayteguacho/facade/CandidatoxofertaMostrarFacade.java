/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CandidatoxofertaForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.hayteguacho.entity.TblCandidatoxempresa;
import com.hayteguacho.entity.TblCandidatoxofertalaboral;
import com.hayteguacho.entity.TblOfertalaboral;
import com.hayteguacho.util.facade.AbstractFacade;
import java.sql.*;
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
public class CandidatoxofertaMostrarFacade extends AbstractFacade<TblCandidatoxofertalaboral,CandidatoxofertaForm >{
     @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public CandidatoxofertaMostrarFacade() {
        super(TblCandidatoxofertalaboral.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     public List<CandidatoxofertaForm> obtenerCandidatosxoferta() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_candidatoxofertalaboral", TblCandidatoxofertalaboral.class);
        List<TblCandidatoxofertalaboral> listaEntity;
        List<CandidatoxofertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CandidatoxofertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CandidatoxofertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CandidatoxofertaForm>();
        }

        return listaEntityForm;
    }
     
     public List<CandidatoxofertaForm> obtenerCandidatosxoferta(String idoferta, boolean esGratis) {
         String sql = "select * from tbl_candidatoxofertalaboral where idofertalaboral = " + idoferta + ""
                + " and estadocandidatoxofertalaboral != 'EL'";
         if (esGratis) {
             sql += " and rownum BETWEEN 1 and 20";
             
         }
        Query q = getEntityManager().createNativeQuery( sql, TblCandidatoxofertalaboral.class);
         
        List<TblCandidatoxofertalaboral> listaEntity;
        List<CandidatoxofertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CandidatoxofertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CandidatoxofertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CandidatoxofertaForm>();
        }

        return listaEntityForm;
    }
     
     
     public String actualizarEstado(
             String idcxoferta,
             String estado,
             String idempresa,
             String idcandidato){
        String flag = "";  
         try {
             Connection cn = em.unwrap(java.sql.Connection.class);
             CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACT_CXOFERTA (?,?,?,?,?)}");
             cs.setInt(1, new Integer(idcxoferta));
             cs.setString(2, estado);
             cs.setInt(3, new Integer(idempresa));
             cs.setInt(4, new Integer(idcandidato));
             cs.registerOutParameter(5, Types.VARCHAR);
             cs.execute();
             flag = cs.getString(5);
             System.out.println(flag);
         } catch (Exception e) {
             flag="-2";
             System.out.println("com.hayteguacho.facade.CandidatoxofertaMostrarFacade.actualizarEstado()");
             e.printStackTrace();
         }
         return flag;
     }
     
     public void enviarCorreo(String email, String asunto,String mensaje){
       Connection cn = em.unwrap(java.sql.Connection.class);
         try {
             CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ENVIAR_MAIL('chambitajobs@gmail.com',?,?,?)}");
             cs.setString(1, email);
             cs.setString(2, asunto);
             cs.setString(3, mensaje);
             cs.execute();
         } catch (Exception e) {
             System.out.println("com.hayteguacho.facade.CandidatoxofertaMostrarFacade.enviarCorreo()");
             e.printStackTrace();
         }
     }
     
      public boolean verificarCandidatosPorEmpresa(String idempresa,String idcandidato) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_candidatoxempresa where idempresa = "+idempresa+
                " and idcandidato = "+idcandidato+" and estadocandxemp = 'A'" , TblCandidatoxempresa.class);
        List<TblCandidatoxempresa> listaEntity;
       boolean flag = false;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                flag = true;
            } 
        } catch (Exception ex) {
            listaEntity = new ArrayList<>();
        }

        return flag;
    }
}
