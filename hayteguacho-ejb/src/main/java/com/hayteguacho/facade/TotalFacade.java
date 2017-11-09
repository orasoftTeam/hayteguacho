/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.TotalCategoriasForm;
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
public class TotalFacade {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public TotalFacade() {

    }

    public List<TotalCategoriasForm> totalCategorias(String pais) {
        /*
        String sql = "SELECT CATEGORIA.IDCATEGORIA idCategoria, (SELECT COUNT(*) FROM TBL_PUESTOTRABAJO) total, DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA))\n"
                + "totalCategoria, CATEGORIA.NOMBRECATEGORIA categoria FROM TBL_CATEGORIAEMPRESA CATEGORIA, TBL_PUESTOTRABAJO PUESTO\n"
                + "WHERE CATEGORIA.IDCATEGORIA = PUESTO.IDCATEGORIA (+)\n"
                + "GROUP BY CATEGORIA.IDCATEGORIA, CATEGORIA.NOMBRECATEGORIA";
        */
        String sql="SELECT CATEGORIA.IDCATEGORIA idCategoria, \n" +
"            (SELECT COUNT(*) FROM tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta\n" +
"            WHERE UPPER(pais.nombrepais)= UPPER(?)\n" +
"                and pais.idpais=depto.idpais \n" +
"                and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"                and oferta.idciudad= ciudad.idciudad\n" +
"            ) \n" +
"                total, DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA))\n" +
"                totalCategoria, CATEGORIA.NOMBRECATEGORIA categoria FROM TBL_CATEGORIAEMPRESA CATEGORIA,\n" +
"                ((select puesto.IDCATEGORIA, puesto.IDPUESTOTRABAJO, puesto.NOMBREPUESTOTRABAJO \n" +
"                from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
"                tbl_ofertalaboral oferta, tbl_puestotrabajo puesto\n" +
"                where oferta.idpuestotrabajo= puesto.IDPUESTOTRABAJO (+)\n" +
"                AND UPPER(nombrepais)= UPPER(?) \n" +
"                and pais.idpais=depto.idpais \n" +
"                and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"                and oferta.idciudad= ciudad.idciudad\n" +
"                )) puesto\n" +
"                WHERE CATEGORIA.IDCATEGORIA = PUESTO.IDCATEGORIA (+)\n" +
"                GROUP BY CATEGORIA.IDCATEGORIA, CATEGORIA.NOMBRECATEGORIA";
        Query q = getEntityManager().createNativeQuery(sql, "ContadorMapping");
        q.setParameter(1, pais);
        q.setParameter(2, pais);
        q.setMaxResults(5);
        q.setFirstResult(0);
        List<TotalCategoriasForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<TotalCategoriasForm>() : temp;
    }
    
    public List<TotalCategoriasForm> totalCategoriasCurriculum(String pais) {
        /*
        String sql = "select CATEGORIA.IDCATEGORIA, (select count(*) from tbl_candidatoxofertalaboral) total, DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA)) totalCategoria, CATEGORIA.NOMBRECATEGORIA categoria from tbl_categoriaempresa categoria, \n" +
        "((select puesto.IDCATEGORIA, puesto.IDPUESTOTRABAJO, puesto.NOMBREPUESTOTRABAJO from tbl_ofertalaboral oferta, tbl_candidatoxofertalaboral aplica, tbl_puestotrabajo puesto\n" +
        "where oferta.idofertalaboral=aplica.idofertalaboral and oferta.idpuestotrabajo= puesto.IDPUESTOTRABAJO (+))) puesto\n" +
        "where categoria.IDCATEGORIA= puesto.IDCATEGORIA (+)\n" +
        "group by categoria.IDCATEGORIA, categoria.NOMBRECATEGORIA";
        */
        
        String sql="select CATEGORIA.IDCATEGORIA, (\n" +
"            select count(*) from tbl_pais pais, tbl_departamento depto, \n" +
"            tbl_ciudad ciudad, tbl_candidatoxofertalaboral aplica,\n" +
"            tbl_ofertalaboral oferta\n" +
"            where oferta.idofertalaboral=aplica.idofertalaboral\n" +
"            AND UPPER(nombrepais)= UPPER(?) \n" +
"            and pais.idpais=depto.idpais \n" +
"            and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"            and oferta.idciudad= ciudad.idciudad    \n" +
"            and oferta.estadoofertalaboral='A'\n" +
"            ) total, \n" +
"            --DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA)) totalCategoria, \n" +
"            COUNT(CATEGORIA.IDCATEGORIA) totalCategoria,\n" +
"            CATEGORIA.NOMBRECATEGORIA categoria from tbl_categoriaempresa categoria, \n" +
"            ((select puesto.IDCATEGORIA, puesto.IDPUESTOTRABAJO, puesto.NOMBREPUESTOTRABAJO \n" +
"            from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
"            tbl_ofertalaboral oferta, tbl_candidatoxofertalaboral aplica, tbl_puestotrabajo puesto\n" +
"            where oferta.idofertalaboral=aplica.idofertalaboral \n" +
"            and oferta.idpuestotrabajo= puesto.IDPUESTOTRABAJO (+)\n" +
"            AND UPPER(nombrepais)= UPPER(?) \n" +
"            and pais.idpais=depto.idpais \n" +
"            and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"            and oferta.idciudad= ciudad.idciudad \n" +
"            )) puesto\n" +
"            where categoria.IDCATEGORIA= puesto.IDCATEGORIA (+)\n" +
"            group by categoria.IDCATEGORIA, categoria.NOMBRECATEGORIA";
        Query q = getEntityManager().createNativeQuery(sql, "ContadorMapping");
        q.setParameter(1, pais);
        q.setParameter(2, pais);
        q.setMaxResults(5);
        q.setFirstResult(0);
        List<TotalCategoriasForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<TotalCategoriasForm>() : temp;
    }
    /*
    public List<MenuForm> obtenerMenus(String modulo) {
        String sql = "select idmenuxmodulo identificador, nombremenu, opcion from tbl_menus_x_modulo\n"
                + "where idmodulo=" + modulo + " and estadomenu='A'";
        Query q = getEntityManager().createNativeQuery(sql, "MenuMapping");
        List<MenuForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<MenuForm>() : temp;
    }
    */
}
