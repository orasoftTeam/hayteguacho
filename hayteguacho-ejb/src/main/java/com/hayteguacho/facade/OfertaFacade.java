/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.AplicarOfertaForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.hayteguacho.entity.TblOfertalaboral;
import com.hayteguacho.util.facade.AbstractFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Stateless
public class OfertaFacade extends AbstractFacade<TblOfertalaboral, OfertaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public OfertaFacade() {
        super(TblOfertalaboral.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<OfertaForm> obtenerOfertasByRange(String pais,int ini, int cantPag) {
        int[] range = {ini, cantPag};
        String sql = "select oferta.* from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta\n" +
        "where UPPER(nombrepais)= UPPER(?) \n" +
        "and pais.idpais=depto.idpais \n" +
        "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
        "and oferta.idciudad= ciudad.idciudad\n" +
        "and oferta.estadoofertalaboral='A'";
        
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        //Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where rownum>=? and rownum<=?", TblOfertalaboral.class);
        //q.setParameter(1, ini);
        //q.setParameter(2, cantPag);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            //listaEntity= findRange(range);
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertas(String pais) {
        String sql="select oferta.*  from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta \n" +
"        where UPPER(nombrepais)= UPPER(?) \n" +
"        and pais.idpais=depto.idpais \n" +
"        and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"        and oferta.idciudad= ciudad.idciudad\n" +
"        and oferta.estadoofertalaboral='A'";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertas(String pais, String idempresa) {
        String sql="select oferta.*  from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta \n" +
"        where UPPER(nombrepais)= UPPER(?) \n" +
"        and pais.idpais=depto.idpais \n" +
"        and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"        and oferta.idciudad= ciudad.idciudad\n" +
"        and oferta.idempresa=?\n" +
"        and oferta.estadoofertalaboral='A'";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setParameter(2, new BigInteger(idempresa));
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertasByCategoria(String pais,String idcategoria, String idempresa) {
        /*String sql = "select oferta.* from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n"
                + "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO and oferta.idempresa=?";
        */
        String sql="select oferta.*  from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta,\n" +
"        TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto\n" +
"        where UPPER(nombrepais)= UPPER(?) \n" +
"        and pais.idpais=depto.idpais \n" +
"        and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"        and oferta.idciudad= ciudad.idciudad\n" +
"        and cemp.IDCATEGORIA=?\n" +
"        and cemp.IDCATEGORIA= puesto.IDCATEGORIA\n" +
"        and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO\n" +
"        and oferta.idempresa=?\n" +
"        and oferta.estadoofertalaboral='A'";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setParameter(2, idcategoria);
        q.setParameter(3, new BigInteger(idempresa));
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertasByCategoriaRange(String pais, String idcategoria, int ini, int cantPag) {
        int[] range = {ini, cantPag};
        String sql = "select oferta.* from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
                "TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto,\n" +
                "tbl_ofertalaboral oferta\n" +
                "where UPPER(nombrepais)= UPPER(?) \n" +
                "and pais.idpais=depto.idpais \n" +
                "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
                "and oferta.idciudad= ciudad.idciudad\n" +
                "and cemp.IDCATEGORIA=? \n" +
                "and cemp.IDCATEGORIA= puesto.IDCATEGORIA \n" +
                "and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO\n" +
                "and oferta.estadoofertalaboral='A'";
                
                /*
                "select oferta.* from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n"
                + "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO"
                + " and oferta.estadoofertalaboral='A'";*/
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setParameter(2, idcategoria);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }
    
    
    public List<OfertaForm> obtenerOfertasByCategoriaDeptoRange(String pais, String depto, String idcategoria, int ini, int cantPag) {
        int[] range = {ini, cantPag};
        String sql = "select oferta.* from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
                "TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto,\n" +
                "tbl_ofertalaboral oferta\n" +
                "where UPPER(nombrepais)= UPPER(?) \n" +
                "and pais.idpais=depto.idpais \n" +
                "and depto.iddepartamento=? \n" +
                "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
                "and oferta.idciudad= ciudad.idciudad\n" +
                "and cemp.IDCATEGORIA=? \n" +
                "and cemp.IDCATEGORIA= puesto.IDCATEGORIA \n" +
                "and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO\n" +
                "and oferta.estadoofertalaboral='A'";
                
                /*
                "select oferta.* from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n"
                + "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO"
                + " and oferta.estadoofertalaboral='A'";*/
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setParameter(2, depto);
        q.setParameter(3, idcategoria);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }
    

    public List<OfertaForm> obtenerOfertasByDepartamentoRange(String iddepto, int ini, int cantPag) {
        int[] range = {ini, cantPag};
        String sql = "select oferta.* from TBL_DEPARTAMENTO depto, TBL_CIUDAD mun, TBL_OFERTALABORAL oferta\n"
                + "where depto.iddepartamento=? and depto.iddepartamento= mun.iddepartamento and mun.idciudad= oferta.IDCIUDAD"
                + " and oferta.estadoofertalaboral='A'";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, iddepto);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    /*
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }    
    
     */
    public List<OfertaForm> obtenerOfertasByIdEmpresa(String pais,String idempresa) {
        String sql="select oferta.*  from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta \n" +
"        where UPPER(nombrepais)= UPPER(?) \n" +
"        and pais.idpais=depto.idpais \n" +
"        and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"        and oferta.idciudad= ciudad.idciudad\n" +
"        and oferta.idempresa=?\n" +
"        and oferta.estadoofertalaboral='A'";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, pais);
        q.setParameter(2, new BigInteger(idempresa));
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertasByIdCandidato(String idcandidato) {
        String sql = "select aplica.IDCANDIDATOXOFERTALABORAL idofertalaboral, (select nombreempresa from tbl_empresa where idempresa=oferta.idempresa) idempresa_tbl, oferta.idjornadalaboral, oferta.idtipocontrato,\n"
                + "        oferta.idpuestotrabajo, oferta.idciudad, oferta.tituloofertalaboral, to_char(oferta.fechahoraofertalaboral,'dd/mm/yyyy') fechahoraofertalaboral,\n"
                + "        to_char(oferta.fechavigenciaofertalaboral,'dd/mm/yyyy') fechavigenciaofertalaboral,\n"
                + "        to_char( oferta.fechacontratacionofertalaboral,'dd/mm/yyyy') fechacontratacionofertalaboral, oferta.cantidadvacante,\n"
                + "        oferta.salariominofertalaboral, oferta.salariomaxofertalaboral, oferta.descripcionofertalaboral,\n"
                + "        oferta.requerimientosofertalaboral, oferta.habilidadesofertalaboral, oferta.conocimientoofertalaboral,\n"
                + "        oferta.personacondiscapacidad, \n"
                + "        DECODE(aplica.estadocandidatoxofertalaboral,'PO','1',(DECODE(aplica.estadocandidatoxofertalaboral,'CV','2',\n"
                + "        (DECODE(aplica.estadocandidatoxofertalaboral,'PR','3',(DECODE(aplica.estadocandidatoxofertalaboral,'FI','4'))))))) \n"
                + "        estadoofertalaboral\n"
                + "        from tbl_candidatoxofertalaboral aplica, tbl_ofertalaboral oferta\n"
                + "        where aplica.IDCANDIDATO=? and oferta.IDOFERTALABORAL= aplica.IDOFERTALABORAL and oferta.ESTADOOFERTALABORAL='A' AND aplica.estadocandidatoxofertalaboral <>'EL'";

        Query q = getEntityManager().createNativeQuery(sql, "OfertaMapping");
        q.setParameter(1, new BigInteger(idcandidato));
        //List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntityForm = q.getResultList();
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }
        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertasByIdCandidato(String estado, String idcandidato) {
        String sql = "select aplica.IDCANDIDATOXOFERTALABORAL idofertalaboral, (select nombreempresa from tbl_empresa where idempresa=oferta.idempresa) idempresa_tbl, oferta.idjornadalaboral, oferta.idtipocontrato,\n"
                + "        oferta.idpuestotrabajo, oferta.idciudad, oferta.tituloofertalaboral, to_char(oferta.fechahoraofertalaboral,'dd/mm/yyyy') fechahoraofertalaboral,\n"
                + "        to_char(oferta.fechavigenciaofertalaboral,'dd/mm/yyyy') fechavigenciaofertalaboral,\n"
                + "        to_char( oferta.fechacontratacionofertalaboral,'dd/mm/yyyy') fechacontratacionofertalaboral, oferta.cantidadvacante,\n"
                + "        oferta.salariominofertalaboral, oferta.salariomaxofertalaboral, oferta.descripcionofertalaboral,\n"
                + "        oferta.requerimientosofertalaboral, oferta.habilidadesofertalaboral, oferta.conocimientoofertalaboral,\n"
                + "        oferta.personacondiscapacidad, \n"
                + "        DECODE(aplica.estadocandidatoxofertalaboral,'PO','1',(DECODE(aplica.estadocandidatoxofertalaboral,'CV','2',\n"
                + "        (DECODE(aplica.estadocandidatoxofertalaboral,'PR','3',(DECODE(aplica.estadocandidatoxofertalaboral,'FI','4'))))))) \n"
                + "        estadoofertalaboral\n"
                + "        from tbl_candidatoxofertalaboral aplica, tbl_ofertalaboral oferta\n"
                + "        where aplica.IDCANDIDATO=? and oferta.IDOFERTALABORAL= aplica.IDOFERTALABORAL and oferta.ESTADOOFERTALABORAL='A' and aplica.ESTADOCANDIDATOXOFERTALABORAL=?";

        Query q = getEntityManager().createNativeQuery(sql, "OfertaMapping");
        q.setParameter(1, new BigInteger(idcandidato));
        q.setParameter(2, estado);
        // List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntityForm = q.getResultList();
            /*
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
             */
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public List<OfertaForm> obtenerOfertasByIdEstado(String pais,String estado, String idempresa) {
        String sql=" select oferta.*  from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta \n" +
"        where UPPER(nombrepais)= UPPER(?) \n" +
"        and pais.idpais=depto.idpais \n" +
"        and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
"        and oferta.idciudad= ciudad.idciudad\n" +
"        and oferta.idempresa=?\n" +
"        and oferta.estadoofertalaboral=?";
       // Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where estadoofertalaboral=? and idempresa=?", TblOfertalaboral.class);
       Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class); 
       q.setParameter(1, pais);
        q.setParameter(2, new BigInteger(idempresa));
        q.setParameter(3, estado);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<OfertaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new OfertaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<OfertaForm>();
        }

        return listaEntityForm;
    }

    public String actualizarOferta(OfertaForm oferta, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZAR_OFERTALABORAL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(oferta.getIdofertalaboral()));
            cs.setInt(2, new Integer(oferta.getIdempresa_tbl()));
            cs.setInt(3, new Integer(oferta.getIdjornadalaboral()));
            cs.setInt(4, new Integer(oferta.getIdtipocontrato()));
            cs.setInt(5, new Integer(oferta.getIdpuestotrabajo()));
            cs.setInt(6, new Integer(oferta.getIdciudad()));
            cs.setString(7, oferta.getTituloofertalaboral().toUpperCase());
            cs.setString(8, oferta.getFechacontratacionofertalaboral());
            cs.setInt(9, new Integer(oferta.getCantidadvacante()));
            cs.setInt(10, new Integer(new BigDecimal(oferta.getSalariominofertalaboral()).intValueExact()));
            cs.setInt(11, new Integer(new BigDecimal(oferta.getSalariomaxofertalaboral()).intValueExact()));
            cs.setString(12, oferta.getDescripcionofertalaboral().toUpperCase());
            cs.setString(13, oferta.getRequerimientosofertalaboral().toUpperCase());
            cs.setString(14, oferta.getHabilidadesofertalaboral().toUpperCase());
            cs.setString(15, oferta.getConocimientoofertalaboral().toUpperCase());
            cs.setString(16, op);
            cs.registerOutParameter(17, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(17);
            System.out.println(flag);
        } catch (Exception e) {
            flag="-2";
            e.printStackTrace();
        }
        return flag;
    }

    public String aplicarOferta(AplicarOfertaForm aform, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_APLICAOFERTA (?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(aform.getIdaplica()));
            cs.setInt(2, new Integer(aform.getIdoferta()));
            cs.setInt(3, new Integer(aform.getIdcandidato()));
            cs.setFloat(4, Float.parseFloat(aform.getPretension()));
            cs.setString(5, aform.getTrabajando());
            cs.setString(6, op);
            cs.registerOutParameter(7, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(7);
            System.out.println(flag);
        } catch (Exception e) {
            flag="-2";
            e.printStackTrace();
        }
        return flag;
    }
    
    /*
    cambiarle los filtros a las ofertas mostradas por categorias
    */

    public int contarOfertasByCategoriaRange(String pais, String idcategoria) {
        int numreg = 0;

        String sql ="select count(*) total from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
                "TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto,\n" +
                "tbl_ofertalaboral oferta\n" +
                "where UPPER(nombrepais)= UPPER(?) \n" +
                "and pais.idpais=depto.idpais \n" +
                "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
                "and oferta.idciudad= ciudad.idciudad\n" +
                "and cemp.IDCATEGORIA=? \n" +
                "and cemp.IDCATEGORIA= puesto.IDCATEGORIA \n" +
                "and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO\n" +
                "and oferta.estadoofertalaboral='A'";
                
                
                /*"select count(*) total from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n"
                + "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO"
                + " and oferta.estadoofertalaboral='A'";*/
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1, pais);
            preparedStatement.setInt(2, Integer.parseInt(idcategoria));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numreg= rs.getInt("total");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("error al contar las categorias");
        }

        return numreg;
    }
    
    public int contarOfertasByCategoriaDeptoRange(String pais, String depto, String idcategoria) {
        int numreg = 0;

        String sql ="select count(*) total from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad,\n" +
                "TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto,\n" +
                "tbl_ofertalaboral oferta\n" +
                "where UPPER(nombrepais)= UPPER(?) \n" +
                "and pais.idpais=depto.idpais \n" +
                "and depto.iddepartamento=? \n" +
                "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
                "and oferta.idciudad= ciudad.idciudad\n" +
                "and cemp.IDCATEGORIA=? \n" +
                "and cemp.IDCATEGORIA= puesto.IDCATEGORIA \n" +
                "and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO\n" +
                "and oferta.estadoofertalaboral='A'";
                
                
                /*"select count(*) total from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n"
                + "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO"
                + " and oferta.estadoofertalaboral='A'";*/
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1, pais);
            preparedStatement.setInt(2, Integer.parseInt(depto));
            preparedStatement.setInt(3, Integer.parseInt(idcategoria));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numreg= rs.getInt("total");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("error al contar las categorias");
        }

        return numreg;
    }
    
    public int contarOfertasByDeptoRange(String iddepto) {
        int numreg = 0;
        String sql="select count(*) total from TBL_DEPARTAMENTO depto, TBL_CIUDAD mun, TBL_OFERTALABORAL oferta\n" +
        "where depto.iddepartamento=? and depto.iddepartamento= mun.iddepartamento and mun.idciudad= oferta.IDCIUDAD"
                + " and oferta.estadoofertalaboral='A'";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(iddepto));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numreg= rs.getInt("total");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("error al contar las categorias");
        }

        return numreg;
    }
    
    
    public int contarOfertasByPais(String pais) {
        int numreg = 0;
        String sql="select count(*) total from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta\n" +
        "where UPPER(nombrepais)= UPPER(?) \n" +
        "and pais.idpais=depto.idpais \n" +
        "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
        "and oferta.idciudad= ciudad.idciudad\n" +
        "and oferta.estadoofertalaboral='A'";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1, pais);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numreg= rs.getInt("total");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("error al contar las categorias");
        }

        return numreg;
    }
    /*
    
            String sql = "select oferta.* from tbl_pais pais, tbl_departamento depto, tbl_ciudad ciudad, tbl_ofertalaboral oferta\n" +
        "where UPPER(nombrepais)= UPPER(?) \n" +
        "and pais.idpais=depto.idpais \n" +
        "and ciudad.iddepartamento=depto.IDDEPARTAMENTO \n" +
        "and oferta.idciudad= ciudad.idciudad\n" +
        "and oferta.estadoofertalaboral='A'";
    */

}
