/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;
import com.admin.hayteguacho.form.MembresiaForm;
import com.admin.hayteguacho.form.TipoPeriodoMembresiaForm;
import com.hayteguacho.entity.TblMembresia;
import com.hayteguacho.entity.TblTipoperidomembresia;
import com.hayteguacho.util.facade.AbstractFacade;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author dell
 */
@Stateless
public class MembresiaFacade extends AbstractFacade<TblMembresia, MembresiaForm>{
   
     @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public MembresiaFacade() {
        super(TblMembresia.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public List<MembresiaForm> obtenerMembresiasOrden() {
        Query q = getEntityManager().createNativeQuery("SELECT MEM.* FROM TBL_MEMBRESIA MEM, TBL_TIPOPERIDOMEMBRESIA TIPO\n" +
"WHERE MEM.IDTIPOPERIODOMEMBRESIA= TIPO.IDTIPOPERIODOMEMBRESIA AND TIPO.NOMBRETIPOPERIODOMEMBRESIA='ANUAL' \n" +
"order by decode(MEM.titulomembresia,'Gold', 1,'Platinum', 2,'Classic',3,'Free',4,5)", TblMembresia.class);
        List<TblMembresia> listaEntity;
        List<MembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MembresiaForm>();
        }

        return listaEntityForm;
    }
    
     public List<MembresiaForm> obtenerMembresiasOrdenOld() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_membresia order by decode(titulomembresia,'Gold', 1,'Platinum', 2,'Classic',3,'Free',4,5)", TblMembresia.class);
        List<TblMembresia> listaEntity;
        List<MembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MembresiaForm>();
        }

        return listaEntityForm;
    }
       public List<MembresiaForm> obtenerMembresiasPorTitulo(String titulo) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_membresia where titulomembresia = ?", TblMembresia.class);
        q.setParameter(1, titulo);
        List<TblMembresia> listaEntity;
        List<MembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MembresiaForm>();
        }

        return listaEntityForm;
    }
     
 
     
    
     public List<MembresiaForm> obtenerMembresias() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_membresia", TblMembresia.class);
        List<TblMembresia> listaEntity;
        List<MembresiaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<MembresiaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new MembresiaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<MembresiaForm>();
        }

        return listaEntityForm;
    }
     
     public String actualizarMembresia(MembresiaForm m, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call hayteguacho.PROC_ACTUALIZAR_MEMBRESIA(?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(m.getIdtipoperiodomembresia()));
            cs.setString(2, m.getTitulomembresia().toUpperCase());
            cs.setString(3, m.getDescripcionmembresia().toUpperCase());
            cs.setString(4, m.getVentajasdemembresia().toUpperCase());
            cs.setFloat(5, new Float(m.getPreciomembresia()));
            cs.setFloat(6, new Float(m.getPrecioxoferta()));
            cs.setInt(7, new Integer(m.getCantidadoferta()));
            cs.setInt(8, new Integer(m.getCantidaduser()));
            cs.setString(9, op);
            cs.setInt(10, new Integer(m.getIdmembresia()));
            cs.registerOutParameter(11, Types.VARCHAR);
           cs.execute();
           flag = cs.getString(11);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
     /*
    public List<MembresiaForm> obtenerMembresiasOrdenOld() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
     
}
