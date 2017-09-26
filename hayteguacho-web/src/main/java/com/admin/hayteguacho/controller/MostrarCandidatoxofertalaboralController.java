/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CandidatoFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "mostrarCandxOfertaController")
public class MostrarCandidatoxofertalaboralController {

    @EJB
    CandidatoFacade candidatoFacade;

    @EJB
    ValidationBean vb;

    private @Getter
    @Setter
    List<CandidatoForm> listaCandidatos = new ArrayList<>();
    
    private @Getter
    @Setter String pdf = "";
    
    private @Getter
    @Setter boolean pdfshow = false;
    
    @PostConstruct
    public void init() {
        listaCandidatos = candidatoFacade.obtenerCandidatos();
    }

    public void dialogo(CandidatoForm cf) {
        pdf =  cf.getArchivocurriculum();
        pdfshow = true;
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
    }
}
