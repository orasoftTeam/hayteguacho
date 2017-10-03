/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.hayteguacho.entity.TblCandidato;
import com.hayteguacho.entity.TblCargoempresa;
import com.hayteguacho.util.facade.AbstractFacade;
import java.math.BigDecimal;
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
public class CandidatoFacade extends AbstractFacade<TblCandidato, CandidatoForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public CandidatoFacade() {
        super(TblCandidato.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<CandidatoForm> obtenerCandidatos() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_candidato", TblCandidato.class);
        List<TblCandidato> listaEntity;
        List<CandidatoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CandidatoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CandidatoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CandidatoForm>();
        }

        return listaEntityForm;
    }
    
    public List<CandidatoForm> obtenerCandidatoById(String id) {
        String sql="select idcandidato, idpais, idpuestotrabajo, nombrecandidato, apellidocandidato, telefono1candidato, telefono2candidato, \n" +
"                generocandidato, fechanacimientocandidato, archivocurriculum, correocandidato,(SELECT QB_ENCRIPCION.FB_DESCENCRIPTAR(contrasenacandidato)from dual) contrasenacandidato,\n" +
"                estadocandidato from tbl_candidato\n" +
"                where idcandidato=?";
        
        Query q = getEntityManager().createNativeQuery(sql, TblCandidato.class);
        q.setParameter(1, new BigDecimal(id));
        List<TblCandidato> listaEntity;
        List<CandidatoForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CandidatoForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CandidatoForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CandidatoForm>();
        }

        return listaEntityForm;
    }
    
    

    public String actualizarCandidato(CandidatoForm candidato, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_CANDIDATO (?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, new Integer(candidato.getIdcandidato()));
            cs.setInt(2, new Integer(candidato.getIdpaistbl()));
            cs.setInt(3, new Integer(candidato.getIdpuestotrabajotbl()));
            cs.setString(4, candidato.getNombrecandidato());
            cs.setString(5, candidato.getApellidocandidato());
            cs.setString(6, candidato.getTelefono1candidato());
            cs.setString(7, candidato.getTelefono2candidato());
            cs.setString(8, candidato.getGenerocandidato());
            cs.setString(9, candidato.getArchivocurriculum());
            cs.setString(10, candidato.getCorreocandidato());
            cs.setString(11, candidato.getContrasenacandidato());
            cs.setString(12, op);
            cs.registerOutParameter(13, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(13);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
}
