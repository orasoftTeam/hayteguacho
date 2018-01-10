/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.PaisForm;
import com.hayteguacho.entity.TblCategoriaempresa;
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
public class CategoriaEmpresaFacade extends AbstractFacade<TblCategoriaempresa, CategoriaEmpresaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public CategoriaEmpresaFacade() {
        super(TblCategoriaempresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<CategoriaEmpresaForm> obtenerCategorias() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_categoriaempresa", TblCategoriaempresa.class);
        List<TblCategoriaempresa> listaEntity;
        List<CategoriaEmpresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CategoriaEmpresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CategoriaEmpresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CategoriaEmpresaForm>();
        }

        return listaEntityForm;
    }

    public String actualizarCategoria(CategoriaEmpresaForm cef, String op) {
        String flag = "";
        String nomCategoria="";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            if(op.equals("D")){
                nomCategoria= cef.getNombrecategoria();
            }
            else{
                nomCategoria= cef.getNombrecategoria().substring(0,1).toUpperCase().concat(cef.getNombrecategoria().substring(1, cef.getNombrecategoria().length()).toLowerCase());
            }
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_CAT_EMP (?,?,?,?)}");
            cs.setString(1, nomCategoria);
            cs.setString(2, op);
            cs.setInt(3, new Integer(cef.getIdcategoria()));
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
    
    public List<CategoriaEmpresaForm> getCategoriasDeOfertasEmpresa(String idempresa, String pais){
        List<CategoriaEmpresaForm> formLs = new ArrayList<>();
        Query q = em.createNativeQuery("select distinct categoria.IDCATEGORIA,categoria.NOMBRECATEGORIA \n" +
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
"and upper(pais.NOMBREPAIS) = upper(?)", TblCategoriaempresa.class);
        q.setParameter(1, idempresa);
        q.setParameter(2, pais);
        try {
            formLs = entityToDtoList(q.getResultList(), new CategoriaEmpresaForm());
        } catch (Exception e) {
            System.out.println("com.hayteguacho.facade.CategoriaEmpresaFacade.getCategoriasDeOfertasEmpresa()");
            e.printStackTrace();
        }
        return formLs;
    }
}
