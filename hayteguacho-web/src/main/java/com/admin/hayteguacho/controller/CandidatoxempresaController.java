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
//import com.hayteguacho.facade.CandidatoxempresaFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private @Getter
    @Setter
    CandidatoxempresaForm selectedCxempresa = new CandidatoxempresaForm();
    private @Getter
    @Setter
    String ideliminar = "";
    private @Getter
    @Setter
    CandidatoForm selectedCand = new CandidatoForm();
    private @Getter
    @Setter
    boolean pdfshow = false;
    private @Getter
    @Setter
    String wordRoot = "";

    @PostConstruct
    public void init() {
        listaCxempresa = cxef.obtenerCandidatosxempresa(login.getUserLog().getIdentificador());
        /* for (CandidatoxempresaForm cxempresa : listaCxempresa) {
             listaCandidatos.add(cf.obtenerCand(cxempresa.getIdcandidato()));
         }*/
        System.out.println("com.admin.hayteguacho.controller.CandidatoxempresaController.init()");

    }

    public CandidatoForm obtenerCandidato(String idcand) {
        return cf.obtenerCand(idcand);
    }

    public String fullName(CandidatoForm cand) {
        return cand.getNombrecandidato() + ' ' + cand.getApellidocandidato();
    }

    public void dialogo(CandidatoForm cf) {

        if (cf.getArchivocurriculum().contains(".docx")) {
            System.out.println("es word");
            //cargarCandidatosxoferta(selectedOferta);
            //vb.updateComponent("CxofertaForm:panelCxoferta");
            wordRoot = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + cf.getArchivocurriculum();
            vb.updateComponent("CxempresaForm:archivo");
            //vb.redirecionar(selectedCandidato.getArchivocurriculum());

        } else {
            selectedCand = cf;
            //pdf =  cf.getArchivocurriculum();
            //String res = "";
            pdfshow = true;
            vb.updateComponent("CxempresaForm:amodal");
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }

    }

    public void validarEliminar(CandidatoxempresaForm coxf) {
        ideliminar = coxf.getIdcandidatoxempresa();
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
    }

    public void eliminar() {
        try {
            cxef.remove(cxef.find(BigDecimal.valueOf(Double.parseDouble(ideliminar))));
            vb.lanzarMensaje("info", "lblCxEmpresa", "lblCxEmpresaEliminar");
            listaCxempresa = cxef.obtenerCandidatosxempresa(login.getUserLog().getIdentificador());
            vb.updateComponent("CxempresaForm");
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.controller.CandidatoxempresaController.eliminar()");
            e.printStackTrace();
        }

    }
}
