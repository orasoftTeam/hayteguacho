/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.PaisFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LAP
 */
@ViewScoped
@ManagedBean(name = "paisController")
public class PaisController {

    @EJB
    PaisFacade paisFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<PaisForm> listaPais = new ArrayList<>();
    private @Getter
    @Setter
    PaisForm pais = new PaisForm();

    @PostConstruct
    public void init() {
        listaPais = paisFacade.obtenerPaises();
        pais.setIdpais("0");
    }

    public void actualizarPais() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(pais.getNombrepais().replace(" ", ""), "warn", "titlePais", "lblReqNombrePais")
                && validationBean.validarSoloLetras(pais.getNombrepais().replace(" ", ""), "warn", "titlePais", "lblSoloLetras")
                && validationBean.validarLongitudCampo(pais.getNombrepais().replace(" ", ""), 4, 25,"warn", "titlePais", "lblLongitudNombrePais")){
            if(pais.getIdpais()==null || pais.getIdpais().equals("0")){
               flag= paisFacade.actualizarPais(pais, "A"); 
               if(flag.equals("0")){
                   validationBean.lanzarMensaje("info", "titlePais", "lblGuardarSuccess");
               }
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            else{
               flag= paisFacade.actualizarPais(pais, "U"); 
               if(flag.equals("0")){
                   validationBean.lanzarMensaje("info", "titlePais", "lblEditarSuccess");
               }
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            limpiar();
            listaPais = paisFacade.obtenerPaises();
        }
    }
    
    public void validareliminar(){
        if(pais.getIdpais()==null || pais.getIdpais().equals("0")){
            validationBean.lanzarMensaje("warn", "titlePais", "lblSelectRegPais");
        }
        else{
            validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void cerrarDialogo(){
        limpiar();
        //validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void eliminar(){
        String flag= paisFacade.actualizarPais(pais, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titlePais", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titlePais", "lblEliminarError");
        //validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaPais = paisFacade.obtenerPaises();
        limpiar();
        
    }

    public void onSelect(int index) {
        PaisForm obj = listaPais.get(index);
        pais.setIdpais(obj.getIdpais());
        pais.setNombrepais(obj.getNombrepais());
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
        pais.setIdpais("0");
        pais.setNombrepais("");
    }

}
