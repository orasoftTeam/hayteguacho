/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.form.CandidatoxempresaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CandidatoFacade;
import com.hayteguacho.facade.CandidatoxempresaFacade;
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
@ManagedBean(name = "CxempresaController")
public class CandidatoxempresaController {
    
    @EJB
    ValidationBean vb;
    
    @EJB
    CandidatoFacade cf;
    
    @EJB
    CandidatoxempresaFacade cxef;
    
    @Inject
    LoginController login;
    
    private @Getter
    @Setter
    List<CandidatoxempresaForm> listaCxempresa = new ArrayList<>();
    List<CandidatoForm> listaCandidatos = new ArrayList<>();
    private @Getter @Setter CandidatoxempresaForm selectedCxempresa = new CandidatoxempresaForm();

     @PostConstruct
    public void init() {
       listaCxempresa = cxef.obtenerCandidatosxempresa(login.getUserLog().getIdentificador());
         for (CandidatoxempresaForm cxempresa : listaCxempresa) {
             listaCandidatos.add(cf.obtenerCand(cxempresa.getIdcandidato()));
         }
         System.out.println("com.admin.hayteguacho.controller.CandidatoxempresaController.init()");
         
    }
    
    public CandidatoForm obtenerCandidato(String idcand) {
        return cf.obtenerCand(idcand);
    }
  public String fullName(CandidatoForm cand) {
        return cand.getNombrecandidato() + ' ' + cand.getApellidocandidato();
    }
  

}
