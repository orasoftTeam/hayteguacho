/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.MembresiaxEmpresaForm;

import com.hayteguacho.entity.TblMembresiaxempresa;
import com.hayteguacho.util.facade.AbstractFacade;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dell
 */
@Stateless
public class MembresiaxempresaFacade extends AbstractFacade<TblMembresiaxempresa, MembresiaxEmpresaForm>{
  @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public MembresiaxempresaFacade() {
        super(TblMembresiaxempresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }   
    
    public String actualizarMembresiaxEmpresa(MembresiaxEmpresaForm mef){
    String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZAR_MEMXEMPRESA(?,?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(mef.getIdmembresia()));
            cs.setInt(2, new Integer(mef.getIdempresa()));
            cs.setDouble(3, new Double(mef.getPrecioxofertasextra()));
            cs.setInt(4, new Integer(mef.getCantidadofertasextra()));
            cs.setInt(5, new Integer(mef.getCantidadcontratada()));
            cs.setString(6, mef.getFechainicio());
            cs.setString(7, mef.getFechavencimiento());
            cs.registerOutParameter(8, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(8);
            System.out.println(flag);
            cs.close();
            cn.close();
       } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.MembresiaxempresaFacade.actualizarMembresiaxEmpresa()");
            e.printStackTrace();
        }
    
        return flag;
    }
}
