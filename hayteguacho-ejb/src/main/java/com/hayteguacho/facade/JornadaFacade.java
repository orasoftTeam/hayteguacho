/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;


import com.admin.hayteguacho.form.JornadaForm;
import com.hayteguacho.entity.TblJornadalaboral;
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
public class JornadaFacade extends AbstractFacade<TblJornadalaboral, JornadaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public JornadaFacade() {
        super(TblJornadalaboral.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<JornadaForm> obtenerJornadas() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_jornadalaboral", TblJornadalaboral.class);
        List<TblJornadalaboral> listaEntity;
        List<JornadaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<JornadaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new JornadaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<JornadaForm>();
        }

        return listaEntityForm;
    }

    public String actualizarJornada(JornadaForm jorf, String op) {
        String flag = "";
        String nomJornada="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            if(op.equals("D")){
                nomJornada= jorf.getNombrejornadalaboral();
            }
            else{
                nomJornada= jorf.getNombrejornadalaboral().substring(0, 1).concat(jorf.getNombrejornadalaboral().substring(1, jorf.getNombrejornadalaboral().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_JORNADA (?,?,?,?)}");
            cs.setString(1, nomJornada);
            cs.setString(2, op);
            cs.setInt(3, new Integer(jorf.getIdjornadalaboral()));
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
        } catch (Exception e) {
            flag="-2";
            e.printStackTrace();
        }
        return flag;
    }
}
