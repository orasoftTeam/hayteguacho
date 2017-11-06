/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.TipoContratoLaboralForm;
import com.hayteguacho.entity.TblTipocontratolaboral;
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
public class TipoContratoLaboralFacade extends AbstractFacade<TblTipocontratolaboral,TipoContratoLaboralForm>{
    
    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;
    
    public TipoContratoLaboralFacade(){
        super(TblTipocontratolaboral.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<TipoContratoLaboralForm> obtenerTiposContrato(){
    List<TipoContratoLaboralForm> listaEntityForm;
    List<TblTipocontratolaboral> listaEntity;
    Query q = getEntityManager().createNativeQuery("select * from tbl_tipocontratolaboral",TblTipocontratolaboral.class);
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm =  new ArrayList<TipoContratoLaboralForm>();
            } else {
             listaEntityForm = this.entityToDtoList(listaEntity, new TipoContratoLaboralForm());
                System.out.println("com.hayteguacho.facade.TipoContratoLaboralFacade.obtenerTiposContrato()");
            }
        } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.TipoContratoLaboralFacade.obtenerCargos()");
            listaEntityForm = new ArrayList<TipoContratoLaboralForm>();
        }
    return listaEntityForm;
    }
    
    public String actualizarCargo(TipoContratoLaboralForm tcf, String op){
    String flag = "";
    String cargo="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            if(op.equals("D")){
                cargo=tcf.getNombretipocontrato();
            }
            else{
                cargo= tcf.getNombretipocontrato().substring(0,1).toUpperCase().concat(tcf.getNombretipocontrato().substring(1, tcf.getNombretipocontrato().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call hayteguacho.proc_actualizar_tipocontrato(?,?,?,?)}");
            cs.setString(1, cargo);
            cs.setString(2, op);
            cs.setInt(3, new Integer(tcf.getIdtipocontrato()));
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
        } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.TipoContratoLaboralFacade.actualizarCargo()");
            e.printStackTrace();
        }
    
    return flag;
    }
}
