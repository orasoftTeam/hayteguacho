/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.admin.hayteguacho.form.TipologiaForm;
import com.hayteguacho.entity.TblCargoempresa;
import com.hayteguacho.entity.TblTipologiaempresa;
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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Stateless
public class TipologiaFacade extends AbstractFacade<TblTipologiaempresa, TipologiaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public TipologiaFacade() {
        super(TblTipologiaempresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<TipologiaForm> obtenerTipologias() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_tipologiaempresa", TblTipologiaempresa.class);
        List<TblTipologiaempresa> listaEntity;
        List<TipologiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<TipologiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new TipologiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<TipologiaForm>();
        }

        return listaEntityForm;
    }

    public String actualizarTipologia(TipologiaForm tipologia, String op) {
        String flag = "";
        String nomtipologia="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            if(op.equals("D")){
                nomtipologia= tipologia.getNombretipologia();
            }
            else{
                nomtipologia= tipologia.getNombretipologia().substring(0,1).toUpperCase().concat(tipologia.getNombretipologia().substring(1, tipologia.getNombretipologia().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_TIPOLOGIA (?,?,?,?)}");
            cs.setString(1, nomtipologia);
            cs.setString(2, op);
            cs.setInt(3, new Integer(tipologia.getIdtipologia()));
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
