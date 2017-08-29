/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.TipoContratoLaboralForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.TipoContratoLaboralFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "tipoContratoLaboralController")
public class TipoContratoLaboralController {
    
    @EJB
    TipoContratoLaboralFacade contratoFacade;
    
    @EJB
    ValidationBean validationBean;
    
    private @Getter @Setter TipoContratoLaboralForm tcf = new TipoContratoLaboralForm();
    private @Getter @Setter List<TipoContratoLaboralForm> listaTiposContrato = new ArrayList<>();
    
    @PostConstruct
    public void init(){
      listaTiposContrato = contratoFacade.obtenerTiposContrato();
      tcf.setIdtipocontrato("0");
    }
    
    public void actualizarTipoContrato(){
     String flag = "";
        if (validationBean.validarCampoVacio(tcf.getNombretipocontrato().replace(" ", ""), "warn", "titleTipoContratoLaboral", "lblNombreReqTipoContrato")
                && validationBean.validarSoloLetras(tcf.getNombretipocontrato().replace(" ", ""), "warn", "titleTipoContratoLaboral", "lblSoloLetras")
                && validationBean.validarLongitudCampo(tcf.getNombretipocontrato().replace(" ", ""), 4, 25, "warn", "titleTipoContratoLaboral", "lblLongitudNombreTipoContrato")){
            if (tcf.getIdtipocontrato()== null || tcf.getIdtipocontrato().equals("0")) {
                flag = contratoFacade.actualizarCargo(tcf, "A");
                if (flag.equals("0")) {
                    validationBean.lanzarMensaje("info", "titleTipoContratoLaboral", "lblGuardarSuccess");
                }
            }
            else{
             flag = contratoFacade.actualizarCargo(tcf, "U");
                if (flag.equals("0")) {
                    validationBean.lanzarMensaje("info", "titleTipoContratoLaboral", "lblEditarSuccess");
                }
            }
            limpiar();
            listaTiposContrato = contratoFacade.obtenerTiposContrato();
        }
    }
    
    public void validareliminar(){
        if (tcf.getIdtipocontrato() == null || tcf.getIdtipocontrato().equals("0")) {
            validationBean.lanzarMensaje("warn", "titleTipoContratoLaboral", "lblSelectRegTipoContrato");
        }
        else{
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void cerrarDialogo(){
    limpiar();
    validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void eliminar(){
    String flag = contratoFacade.actualizarCargo(tcf, "D");
        if (flag.equals("0")) {
            validationBean.lanzarMensaje("info", "titleTipoContratoLaboral", "lblEliminarSuccess");
            listaTiposContrato = contratoFacade.obtenerTiposContrato();
            limpiar();
        }else if(flag.equals("-1") || flag.equals("-2")){
        validationBean.lanzarMensaje("error", "titleTipoContratoLaboral", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaTiposContrato = contratoFacade.obtenerTiposContrato();
        limpiar();
        }
    }
    
    public void onSelect(TipoContratoLaboralForm obj){
    tcf.setIdtipocontrato(obj.getIdtipocontrato());
    tcf.setNombretipocontrato(obj.getNombretipocontrato());
        System.out.println("com.admin.hayteguacho.controller.TipoContratoLaboralController.onSelect()");
    }
    
    public void unSelect(){
    limpiar();
    }
    
    public void limpiar(){
    tcf.setIdtipocontrato("0");
    tcf.setNombretipocontrato("");
        System.out.println("com.admin.hayteguacho.controller.TipoContratoLaboralController.limpiar()");
    }
}
