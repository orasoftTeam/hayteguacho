/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.admin.hayteguacho.form.JornadaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CargoEmpresaFacade;
import com.hayteguacho.facade.JornadaFacade;
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
@ManagedBean(name = "jornadaController")
public class JornadaController {

    @EJB
    JornadaFacade jornadaFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<JornadaForm> listaJornadas = new ArrayList<>();
    private @Getter @Setter JornadaForm jornada = new JornadaForm();

    @PostConstruct
    public void init() {
        listaJornadas = jornadaFacade.obtenerJornadas();
        jornada.setIdjornadalaboral("0");
    }

    public void actualizarJornada() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(jornada.getNombrejornadalaboral().replace(" ", ""), "warn", "titleJornadaEmpresa", "lblNombreReqJornadaEmpresa")
                && validationBean.validarSoloLetras(jornada.getNombrejornadalaboral().replace(" ", ""), "warn", "titleJornadaEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(jornada.getNombrejornadalaboral().replace(" ", ""), 4, 25,"warn", "titleJornadaEmpresa", "lblLongitudNombreJornada")){
            if(jornada.getIdjornadalaboral()==null || jornada.getIdjornadalaboral().equals("0")){
               flag= jornadaFacade.actualizarJornada(jornada, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleJornadaEmpresa", "lblGuardarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            else{
                flag= jornadaFacade.actualizarJornada(jornada, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleJornadaEmpresa", "lblEditarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            limpiar();
            listaJornadas = jornadaFacade.obtenerJornadas();
        }
    }
    
    public void validareliminar(){
        if(jornada.getIdjornadalaboral()==null || jornada.getIdjornadalaboral().equals("0")){
            validationBean.lanzarMensaje("warn", "titleJornadaEmpresa", "lblSelectRegJornada");
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
        String flag= jornadaFacade.actualizarJornada(jornada, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titleJornadaEmpresa", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleJornadaEmpresa", "lblEliminarError");
        //validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaJornadas = jornadaFacade.obtenerJornadas();
        limpiar();
        
    }

    public void onSelect(int index) {
        JornadaForm obj= listaJornadas.get(index);
        jornada.setIdjornadalaboral(obj.getIdjornadalaboral());
        jornada.setNombrejornadalaboral(obj.getNombrejornadalaboral());
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
       jornada.setIdjornadalaboral("0");
       jornada.setNombrejornadalaboral("");
    }

}
