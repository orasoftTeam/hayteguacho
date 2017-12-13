/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.form.CurriculumForm;
import com.admin.hayteguacho.form.PuestoTrabajoForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CandidatoFacade;
import com.hayteguacho.facade.PuestoTrabajoFacade;
import com.hayteguacho.facade.TotalFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "dashboardCandidato")
public class DashboardCandidatoController {
    @EJB
    ValidationBean vb;
    
    @EJB
    CandidatoFacade cf;
    
    @EJB
    PuestoTrabajoFacade ptf;
    
    @Inject
    LoginController login;
    
    @EJB
    TotalFacade tf;
    
   private @Getter
    @Setter
    CandidatoForm loggedCand = new CandidatoForm();
   
   private @Getter @Setter List<CurriculumForm> listaCV = new ArrayList<>();
    
    @PostConstruct
    public void init(){
    loggedCand = cf.obtenerCand(login.getUserLog().getIdentificador());
    listaCV = tf.dashCandidato(login.getPais(), loggedCand.getIdcandidato());
    }
    
    public String getFullName(){
    
     return  loggedCand.getNombrecandidato().toUpperCase().substring(0,1) 
             + loggedCand.getNombrecandidato().toLowerCase().substring(1)
             + " " 
             + loggedCand.getApellidocandidato().toUpperCase().substring(0,1)
             + loggedCand.getApellidocandidato().toLowerCase().substring(1);
    }
    
    public String getPuesto(){
     PuestoTrabajoForm puestoForm = ptf.entityToDto(ptf.find(new BigDecimal(loggedCand.getIdpuestotrabajotbl())), new PuestoTrabajoForm());
     return puestoForm.getNombrepuestotrabajo();
    }
    
    
    
}
