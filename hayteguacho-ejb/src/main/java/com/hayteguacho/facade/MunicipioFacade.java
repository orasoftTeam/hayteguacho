/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.DepartamentoForm;
import com.admin.hayteguacho.form.MunicipioForm;
import com.hayteguacho.entity.TblCiudad;
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
public class MunicipioFacade extends AbstractFacade<TblCiudad, MunicipioForm> {
    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public MunicipioFacade() {
        super(TblCiudad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
    public List<MunicipioForm> obtenerMunicipios(String iddepto) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ciudad where iddepartamento=?", TblCiudad.class);
        q.setParameter(1, new BigDecimal(iddepto));
        List<TblCiudad> listaEntity;
        List<MunicipioForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MunicipioForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MunicipioForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MunicipioForm>();
        }

        return listaEntityForm;
    }
    
    public List<MunicipioForm> obtenerMunicipio(String idmuni) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ciudad where idciudad=?", TblCiudad.class);
        q.setParameter(1, new BigDecimal(idmuni));
        List<TblCiudad> listaEntity;
        List<MunicipioForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MunicipioForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MunicipioForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MunicipioForm>();
        }

        return listaEntityForm;
    }
    
    public String actualizarMunicipios(MunicipioForm mf,String iddepto, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_CIUDAD (?,?,?,?,?)}");
            cs.setString(1, mf.getNombreciudad().toUpperCase());
            cs.setString(2, op);
            cs.setInt(3, new Integer(iddepto));
            cs.setInt(4, new Integer(mf.getIdciudad()));
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(5);
            System.out.println(flag);
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(OK)");
        } catch (Exception e) {
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(ERROR)");
            e.printStackTrace();
        }
        return flag;
    }
    
}
