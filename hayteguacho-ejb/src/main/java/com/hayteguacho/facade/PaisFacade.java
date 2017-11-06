/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.PaisForm;
import com.hayteguacho.entity.TblPais;
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
public class PaisFacade extends AbstractFacade<TblPais, PaisForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public PaisFacade() {
        super(TblPais.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<PaisForm> obtenerPaises() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_pais", TblPais.class);
        List<TblPais> listaEntity;
        List<PaisForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<PaisForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new PaisForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<PaisForm>();
        }

        return listaEntityForm;
    }
    
    public List<PaisForm> obtenerPaisesPorNombre(String nompais) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_pais where nombrepais=?", TblPais.class);
        q.setParameter(1, nompais);
        List<TblPais> listaEntity;
        List<PaisForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<PaisForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new PaisForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<PaisForm>();
        }

        return listaEntityForm;
    }

    public String actualizarPais(PaisForm pf, String op) {
        String flag = "";
        String nompais="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            if(!op.equals("D")){
             nompais= pf.getNombrepais().substring(0, 1).toUpperCase().concat(pf.getNombrepais().substring(1, pf.getNombrepais().length()).toLowerCase());  
            }
            else{
                nompais=pf.getNombrepais();
            }
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_PAIS (?,?,?,?)}");
            //cs.setString(1, pf.getNombrepais().toUpperCase());
            cs.setString(1, nompais);
            cs.setString(2, op);
            cs.setInt(3, new Integer(pf.getIdpais()));
            /*
            cs.setString(1, uf.getNombreusuario());
            cs.setString(2, uf.getContrausuario());
            cs.setString(3, uf.getCorreousuario());
            cs.setString(4, uf.getFechacreacion());
            */
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
            System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(OK)");
        } catch (Exception e) {
            System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(ERROR)");
            e.printStackTrace();
        }
        return flag;
    }
    /*
    public boolean agregarEmpleadoEspecialidad(){
        boolean flag=true;
        try{
            List<TblEmpleadoxespecialidad> esp= empleado.getTblEmpleadoxespecialidadList();
           if(esp!=null && !esp.isEmpty()) {
               TblEmpleadoxespecialidad obj= esp.get(0);
               obj.setIdespecialidad(especialidad);
               edit(obj);
           }
           else{
              TblEmpleadoxespecialidad obj= new TblEmpleadoxespecialidad();
              obj.setIdempleado(empleado);
              obj.setIdespecialidad(especialidad);
               create(obj);
           }
        }
        catch(Exception ex){
            //getEntityManager().getTransaction().rollback();
            flag=false;
        }
        return flag;
    }

     */
}
