/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;


import com.admin.hayteguacho.form.OfertaForm;
import com.hayteguacho.entity.TblOfertalaboral;
import com.hayteguacho.util.facade.AbstractFacade;
import java.math.BigDecimal;
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
    

    public List<OfertaForm> obtenerOfertasByRange(int ini, int cantPag) {
        int[] range={ini, cantPag};
        //Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where rownum>=? and rownum<=?", TblOfertalaboral.class);
        //q.setParameter(1, ini);
        //q.setParameter(2, cantPag);
        List<TblOfertalaboral> listaEntity;
        List<OfertaForm> listaEntityForm;

        try {
            //listaEntity = q.getResultList();
            listaEntity= findRange(range);
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
    
    public List<OfertaForm> obtenerOfertas() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral ", TblOfertalaboral.class);
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
    
     public List<OfertaForm> obtenerOfertas(String idempresa) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where idempresa  = " + idempresa, TblOfertalaboral.class);
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
    
    
    public List<OfertaForm> obtenerOfertasByCategoria(String idcategoria, String idempresa) {
        String sql="select oferta.* from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n" +
                "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO and oferta.idempresa=?";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, idcategoria);
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
    
    public List<OfertaForm> obtenerOfertasByCategoriaRange(String idcategoria, int ini, int cantPag) {
        int[] range={ini, cantPag};
        String sql="select oferta.* from TBL_CATEGORIAEMPRESA cemp, TBL_PUESTOTRABAJO puesto, TBL_OFERTALABORAL oferta\n" +
                "where cemp.IDCATEGORIA=? and cemp.IDCATEGORIA= puesto.IDCATEGORIA and puesto.IDPUESTOTRABAJO= oferta.IDPUESTOTRABAJO";
        Query q = getEntityManager().createNativeQuery(sql, TblOfertalaboral.class);
        q.setParameter(1, idcategoria);
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
        int[] range={ini, cantPag};
        String sql="select oferta.* from TBL_DEPARTAMENTO depto, TBL_CIUDAD mun, TBL_OFERTALABORAL oferta\n" +
        "where depto.iddepartamento=? and depto.iddepartamento= mun.iddepartamento and mun.idciudad= oferta.IDCIUDAD";
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
    
    public List<OfertaForm> obtenerOfertasByIdEmpresa(String idempresa) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where idempresa=? and estadoofertalaboral='A'", TblOfertalaboral.class);
        q.setParameter(1, new BigInteger(idempresa));
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
    
    public List<OfertaForm> obtenerOfertasByIdEstado(String estado, String idempresa) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_ofertalaboral where estadoofertalaboral=? and idempresa=?", TblOfertalaboral.class);
        q.setParameter(1, estado);
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
            e.printStackTrace();
        }
        return flag;
    }
    
}
