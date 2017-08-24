/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;


import com.admin.hayteguacho.form.TipologiaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.TipologiaFacade;
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
 * @author LAP
 */
@ViewScoped
@ManagedBean(name = "tipologiaController")
public class TipologiaController {

    @EJB
    TipologiaFacade tipologiaFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<TipologiaForm> listaTipologias = new ArrayList<>();
    private @Getter @Setter TipologiaForm tipologia = new TipologiaForm();

    @PostConstruct
    public void init() {
        listaTipologias = tipologiaFacade.obtenerTipologias();
        tipologia.setIdtipologia("0");
    }

    public void actualizarPais() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(tipologia.getNombretipologia().replace(" ", ""), "warn", "titleTipologiaEmpresa", "lblNombreReqTipologiaEmpresa")
                && validationBean.validarSoloLetras(tipologia.getNombretipologia().replace(" ", ""), "warn", "titleTipologiaEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(tipologia.getNombretipologia().replace(" ", ""), 4, 25,"warn", "titleTipologiaEmpresa", "lblLongitudNombreTipologia")){
            if(tipologia.getIdtipologia()==null || tipologia.getIdtipologia().equals("0")){
               flag= tipologiaFacade.actualizarTipologia(tipologia, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleTipologiaEmpresa", "lblGuardarSuccess");
            }
            else{
                flag= tipologiaFacade.actualizarTipologia(tipologia, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleTipologiaEmpresa", "lblEditarSuccess");
            }
            limpiar();
            listaTipologias = tipologiaFacade.obtenerTipologias();
        }
    }
    
    public void validareliminar(){
        if(tipologia.getIdtipologia()==null || tipologia.getIdtipologia().equals("0")){
            validationBean.lanzarMensaje("warn", "titleTipologiaEmpresa", "lblSelectRegTipologia");
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
        String flag= tipologiaFacade.actualizarTipologia(tipologia, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titleTipologiaEmpresa", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleTipologiaEmpresa", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaTipologias = tipologiaFacade.obtenerTipologias();
        limpiar();
        
    }

    public void onSelect(TipologiaForm obj) {
        tipologia.setIdtipologia(obj.getIdtipologia());
        tipologia.setNombretipologia(obj.getNombretipologia());
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
       tipologia.setIdtipologia("0");
       tipologia.setNombretipologia("");
    }

}
