/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;


import com.admin.hayteguacho.form.PuestoTrabajoForm;
import com.hayteguacho.entity.TblPuestotrabajo;
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
public class PuestoTrabajoFacade extends AbstractFacade<TblPuestotrabajo, PuestoTrabajoForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public PuestoTrabajoFacade() {
        super(TblPuestotrabajo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<PuestoTrabajoForm> obtenerPuestosByIdCategoria(String idcategoria) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_puestotrabajo where idcategoria=?", TblPuestotrabajo.class);
        q.setParameter(1, new BigInteger(idcategoria));
        List<TblPuestotrabajo> listaEntity;
        List<PuestoTrabajoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<PuestoTrabajoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new PuestoTrabajoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<PuestoTrabajoForm>();
        }

        return listaEntityForm;
    }
    
    public List<PuestoTrabajoForm> obtenerPuestosByIdPuesto(String idpuesto) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_puestotrabajo where idpuestotrabajo=?", TblPuestotrabajo.class);
        q.setParameter(1, new BigInteger(idpuesto));
        List<TblPuestotrabajo> listaEntity;
        List<PuestoTrabajoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<PuestoTrabajoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new PuestoTrabajoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<PuestoTrabajoForm>();
        }

        return listaEntityForm;
    }
    
    public String actualizarPuesto(PuestoTrabajoForm obj, String op) {
        String flag = "";
        String nomPuesto="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            if(op.equals("D")){
                nomPuesto= obj.getNombrepuestotrabajo();
            }
            else{
                nomPuesto= obj.getNombrepuestotrabajo().substring(0,1).toUpperCase().concat(obj.getNombrepuestotrabajo().substring(1, obj.getNombrepuestotrabajo().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_PUESTOTRABAJO (?,?,?,?,?)}");
            cs.setString(1, nomPuesto);
            cs.setInt(2, new Integer(obj.getIdpuestotrabajo()));
            cs.setInt(3, new Integer(obj.getIdcategoria()));
            cs.setString(4, op);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(5);
            System.out.println(flag);
        } catch (Exception e) {
            flag="-2";
            e.printStackTrace();
        }
        return flag;
    }
    
    public List<PuestoTrabajoForm> getPuestoTrabajoPorOfertaEmpresa(String idempresa,String pais,String idcat){
    List<PuestoTrabajoForm> pueLs = new ArrayList<>();
    Query q = em.createNativeQuery("select puesto.IDCATEGORIA, puesto.IDPUESTOTRABAJO, puesto.NOMBREPUESTOTRABAJO\n" +
"from TBL_CATEGORIAEMPRESA categoria\n" +
"inner join TBL_PUESTOTRABAJO puesto\n" +
"on puesto.IDCATEGORIA = categoria.IDCATEGORIA\n" +
"inner join TBL_OFERTALABORAL oferta\n" +
"on oferta.IDPUESTOTRABAJO = puesto.IDPUESTOTRABAJO\n" +
"inner join TBL_CIUDAD ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"inner join TBL_DEPARTAMENTO depto\n" +
"on  depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"inner join TBL_PAIS pais \n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"where oferta.IDEMPRESA = ?\n" +
"and upper(pais.NOMBREPAIS) = upper(?)"+ 
" and puesto.IDCATEGORIA = ?", TblPuestotrabajo.class);
    q.setParameter(1, idempresa);
    q.setParameter(2, pais);
    q.setParameter(3, idcat);
        try {
            pueLs = entityToDtoList(q.getResultList(), new PuestoTrabajoForm());
        } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.PuestoTrabajoFacade.getPuestoTrabajoPorOfertaEmpresa()");
            e.printStackTrace();
        }
    
    return pueLs;
    }
    
}
