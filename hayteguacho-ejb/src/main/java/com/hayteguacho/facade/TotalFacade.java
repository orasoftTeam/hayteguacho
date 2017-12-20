/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CurriculumForm;
import com.admin.hayteguacho.form.InscritoForm;
import com.admin.hayteguacho.form.OfertaAplicaForm;
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
    
    public List<InscritoForm> dashEmpresaInscrito(String pais, String idempresa){
        String sql = "select oferta.TITULOOFERTALABORAL as titulo, \n" +
"candidato.NOMBRECANDIDATO || ' ' || candidato.APELLIDOCANDIDATO as candidato,\n" +
"candidato.CORREOCANDIDATO as email,\n" +
"candidato.TELEFONO1CANDIDATO || ', ' || candidato.TELEFONO2CANDIDATO as tels,\n" +
"candidato.ARCHIVOCURRICULUM as curriculum\n" +
"from tbl_candidatoxofertalaboral aplica\n" +
"inner join tbl_ofertalaboral oferta\n" +
"on oferta.IDOFERTALABORAL = aplica.IDOFERTALABORAL\n" +
"inner join tbl_empresa empresa\n" +
"on empresa.IDEMPRESA = oferta.IDEMPRESA\n" +
"inner join tbl_candidato candidato\n" +
"on aplica.IDCANDIDATO = candidato.IDCANDIDATO\n" +
"inner join tbl_ciudad ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"inner join tbl_departamento depto\n" +
"on depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"inner join tbl_pais pais\n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"where aplica.ESTADOCANDIDATOXOFERTALABORAL = 'PO'\n" +
"and empresa.IDEMPRESA = ?\n" +
"and upper(pais.NOMBREPAIS) = upper(?)";
        Query q = em.createNativeQuery(sql, "InscritoMapping");
        q.setParameter(2, pais);
        q.setParameter(1, idempresa);
        //q.setMaxResults(5);
        //q.setFirstResult(0);
        List<InscritoForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<InscritoForm>() : temp;
    }
    
    public List<OfertaAplicaForm> dashEmpresa(String pais, String idempresa){
        String sql = "select oferta.TITULOOFERTALABORAL as titulo, count(aplica.IDCANDIDATOXOFERTALABORAL)\n" +
"as inscritos from tbl_ofertalaboral oferta\n" +
"full join tbl_candidatoxofertalaboral aplica\n" +
"on aplica.IDOFERTALABORAL = oferta.IDOFERTALABORAL\n" +
"full join tbl_ciudad ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"full join tbl_departamento depto\n" +
"on depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"full join tbl_pais pais\n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"where oferta.IDEMPRESA = ?\n" +
"and upper(pais.NOMBREPAIS) = upper(?)\n"+ 
" and oferta.estadoofertalaboral = 'A'" +
"group by oferta.tituloofertalaboral";
        Query q = em.createNativeQuery(sql, "OfertaAplicaMapping");
        q.setParameter(2, pais);
        q.setParameter(1, idempresa);
        //q.setMaxResults(5);
        //q.setFirstResult(0);
        List<OfertaAplicaForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<OfertaAplicaForm>() : temp;
    }
    
    public List<CurriculumForm> dashCandidato(String pais, String idcandidato){
        String sql = "select aplica.IDCANDIDATOXOFERTALABORAL, \n" +
"oferta.IDOFERTALABORAL,\n" +
"categoria.NOMBRECATEGORIA as categoria,\n" +
"oferta.TITULOOFERTALABORAL as titulo, "
                + "empresa.NOMBREEMPRESA as nombre,\n" +
"puesto.NOMBREPUESTOTRABAJO as puesto,\n" +
"candidato.NOMBRECANDIDATO || ' ' || candidato.APELLIDOCANDIDATO as nombrecandidato\n" +
"from TBL_CANDIDATOXOFERTALABORAL aplica\n" +
"inner join TBL_OFERTALABORAL oferta\n" +
"on oferta.IDOFERTALABORAL = aplica.IDOFERTALABORAL\n" +
"inner join TBL_CIUDAD ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"inner join TBL_DEPARTAMENTO depto\n" +
"on depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"inner join TBL_PAIS pais\n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"inner join TBL_PUESTOTRABAJO puesto\n" +
"on oferta.IDPUESTOTRABAJO = puesto.IDPUESTOTRABAJO\n" +
"inner join tbl_categoriaempresa categoria\n" +
"on categoria.idcategoria = puesto.IDCATEGORIA\n" +
"inner join tbl_empresa empresa\n" +
"on empresa.IDEMPRESA = oferta.IDEMPRESA\n" +
"inner join TBL_CANDIDATOXOFERTALABORAL aplica\n" +
"on aplica.IDOFERTALABORAL = oferta.IDOFERTALABORAL\n" +
"inner join tbl_candidato candidato\n" +
"on candidato.IDCANDIDATO = aplica.IDCANDIDATO\n" +
"where oferta.estadoofertalaboral='A'\n" +
"and categoria.idcategoria = categoria.idcategoria\n" +
"and aplica.ESTADOCANDIDATOXOFERTALABORAL = 'CV'\n" +
"AND UPPER(nombrepais)= UPPER(?)\n" +
"and candidato.IDCANDIDATO = ?\n" +
"group by aplica.IDCANDIDATOXOFERTALABORAL,oferta.IDOFERTALABORAL, categoria.NOMBRECATEGORIA, empresa.NOMBREEMPRESA,\n" +
"puesto.NOMBREPUESTOTRABAJO, candidato.NOMBRECANDIDATO || ' ' || candidato.APELLIDOCANDIDATO, oferta.FECHAHORAOFERTALABORAL, oferta.TITULOOFERTALABORAL\n" +
"order by oferta.FECHAHORAOFERTALABORAL desc";
        Query q = em.createNativeQuery(sql, "DashCandidato");
        q.setParameter(1, pais);
        q.setParameter(2, idcandidato);
        q.setMaxResults(5);
        q.setFirstResult(0);
        List<CurriculumForm> temp = q.getResultList();
        return temp.isEmpty() ? new ArrayList<CurriculumForm>() : temp;
    }

    public List<TotalCategoriasForm> totalCategorias(String pais) {
        /*
        String sql = "SELECT CATEGORIA.IDCATEGORIA idCategoria, (SELECT COUNT(*) FROM TBL_PUESTOTRABAJO) total, DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA))\n"
                + "totalCategoria, CATEGORIA.NOMBRECATEGORIA categoria FROM TBL_CATEGORIAEMPRESA CATEGORIA, TBL_PUESTOTRABAJO PUESTO\n"
                + "WHERE CATEGORIA.IDCATEGORIA = PUESTO.IDCATEGORIA (+)\n"
                + "GROUP BY CATEGORIA.IDCATEGORIA, CATEGORIA.NOMBRECATEGORIA";
        */
        /*String sql="SELECT CATEGORIA.IDCATEGORIA idCategoria, \n" +
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
"                GROUP BY CATEGORIA.IDCATEGORIA, CATEGORIA.NOMBRECATEGORIA";*/
        String sql = "  select CATEGORIA.IDCATEGORIA,( select count(*)  from tbl_pais pais \n" +
"                inner join tbl_departamento depto\n" +
"                on pais.IDPAIS = depto.IDPAIS\n" +
"                inner join tbl_ciudad ciudad\n" +
"                on depto.iddepartamento = ciudad.iddepartamento\n" +
"                inner join TBL_OFERTALABORAL oferta\n" +
"                on oferta.IDCIUDAD = ciudad.IDCIUDAD"
                + " where UPPER(pais.NOMBREPAIS) = UPPER(?)) as total,\n" +
"                count(*) as totalCategoria,\n" +
"                categoria.NOMBRECATEGORIA as categoria\n" +
"                from TBL_CATEGORIAEMPRESA categoria\n" +
"                inner join TBL_PUESTOTRABAJO puesto\n" +
"                on categoria.IDCATEGORIA = puesto.IDCATEGORIA\n" +
"                inner join TBL_OFERTALABORAL oferta\n" +
"                on oferta.IDPUESTOTRABAJO = puesto.IDPUESTOTRABAJO\n" +
"                inner join tbl_ciudad ciudad \n" +
"                on ciudad.idciudad = oferta.idciudad\n" +
"                inner join tbl_departamento depto\n" +
"                on depto.iddepartamento = ciudad.iddepartamento\n" +
"                inner join TBL_PAIS pais\n" +
"                on pais.idpais = depto.idpais\n" +
"                where UPPER(pais.NOMBREPAIS) = UPPER(?)\n" +
"                group by categoria.IDCATEGORIA,categoria.NOMBRECATEGORIA";
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
        
      /*  String sql="select CATEGORIA.IDCATEGORIA, (\n" +
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
"            DECODE(COUNT(CATEGORIA.IDCATEGORIA),1,0,COUNT(CATEGORIA.IDCATEGORIA)) totalCategoria, \n" +
"            --COUNT(CATEGORIA.IDCATEGORIA) totalCategoria,\n" +
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
"            group by categoria.IDCATEGORIA, categoria.NOMBRECATEGORIA";*/
      
     /* String sql = "select CATEGORIA.IDCATEGORIA, (\n" +
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
"            group by categoria.IDCATEGORIA, categoria.NOMBRECATEGORIA"; */
       String sql = "       select CATEGORIA.IDCATEGORIA, (select count(*) from TBL_CANDIDATOXOFERTALABORAL aplica\n" +
"inner join TBL_OFERTALABORAL oferta\n" +
"on oferta.IDOFERTALABORAL = aplica.IDOFERTALABORAL\n" +
"inner join TBL_CIUDAD ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"inner join TBL_DEPARTAMENTO depto\n" +
"on depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"inner join TBL_PAIS pais\n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"where oferta.estadoofertalaboral='A'\n" +
" AND UPPER(nombrepais)= UPPER(?)) \n" +
"as total \n" +
"            ,count(*) as totalCategoria,\n" +
"            categoria.NOMBRECATEGORIA as categoria\n" +
"            from TBL_CANDIDATOXOFERTALABORAL aplica\n" +
"inner join TBL_OFERTALABORAL oferta\n" +
"on oferta.IDOFERTALABORAL = aplica.IDOFERTALABORAL\n" +
"inner join TBL_CIUDAD ciudad\n" +
"on ciudad.IDCIUDAD = oferta.IDCIUDAD\n" +
"inner join TBL_DEPARTAMENTO depto\n" +
"on depto.IDDEPARTAMENTO = ciudad.IDDEPARTAMENTO\n" +
"inner join TBL_PAIS pais\n" +
"on pais.IDPAIS = depto.IDPAIS\n" +
"inner join TBL_PUESTOTRABAJO puesto\n" +
"on oferta.IDPUESTOTRABAJO = puesto.IDPUESTOTRABAJO\n" +
"inner join tbl_categoriaempresa categoria\n" +
"on categoria.idcategoria = puesto.IDCATEGORIA\n" +
"where oferta.estadoofertalaboral='A'\n" +
"and categoria.idcategoria = categoria.idcategoria\n" +
" AND UPPER(nombrepais)= UPPER(?)\n" +
" group by CATEGORIA.IDCATEGORIA, categoria.NOMBRECATEGORIA";
      
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
