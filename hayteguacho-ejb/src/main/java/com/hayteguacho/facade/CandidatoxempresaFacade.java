/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CandidatoxempresaForm;
import com.admin.hayteguacho.form.CandidatoxofertaForm;
import com.hayteguacho.entity.TblCandidatoxempresa;
import com.hayteguacho.entity.TblCandidatoxofertalaboral;
import com.hayteguacho.util.facade.AbstractFacade;
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
public class CandidatoxempresaFacade extends AbstractFacade<TblCandidatoxempresa,CandidatoxempresaForm>{
    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public CandidatoxempresaFacade() {
        super(TblCandidatoxempresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<CandidatoxempresaForm> obtenerCandidatosxempresa(String idempresa) {
        Query q = getEntityManager().createNativeQuery("select * from tbl_candidatoxempresa where idempresa = ?", TblCandidatoxempresa.class);
        q.setParameter(1, idempresa);
        List<TblCandidatoxempresa> listaEntity;
        List<CandidatoxempresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CandidatoxempresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CandidatoxempresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CandidatoxempresaForm>();
        }

        return listaEntityForm;
    }
    
    public String actualizarCxempresa(CandidatoxempresaForm cxemp){
    return "";
    }
}
