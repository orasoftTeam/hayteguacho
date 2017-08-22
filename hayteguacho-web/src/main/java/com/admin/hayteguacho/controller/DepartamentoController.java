/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.DepartamentoForm;
import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.DepartamentoFacade;
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
@ManagedBean(name = "deptoController")
public class DepartamentoController {

    @EJB
    DepartamentoFacade deptoFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter @Setter List<DepartamentoForm> listaDepto = new ArrayList<>();
    private @Getter @Setter DepartamentoForm depto = new DepartamentoForm();
    
    private @Getter @Setter String idpais;

    @PostConstruct
    public void init() {
        //listaDepto = deptoFacade.obtenerPaises(idpais);
        depto.setIddepartamento("0");
    }

    public void actualizarDepartamento() {
        
        String flag=""; 
        if(idpais==null || idpais.equals("")){
            validationBean.lanzarMensaje("warn", "titleDepto", "lblPaisSelectRegDepartamento");
        }
        
        else
            if(validationBean.validarCampoVacio(depto.getNombredepartamento(), "warn", "titleDepto", "lblReqNombreDepto")
                    && validationBean.validarSoloLetras(depto.getNombredepartamento(), "warn", "titleDepto", "lblSoloLetras")
                    && validationBean.validarLongitudCampo(depto.getNombredepartamento(), 4, 25,"warn", "titleDepto", "lblLongitudNombreDepto")){
                if(depto.getIddepartamento()==null || depto.getIddepartamento().equals("0")){
                   flag= deptoFacade.actualizarDepartamento(depto, idpais, "A");
                   if(flag.equals("0")){
                       validationBean.lanzarMensaje("info", "titleDepto", "lblGuardarSuccess");
                       //listaDepto= new ArrayList<DepartamentoForm>();
                        //actualizarComponentes();   
                   }
                }
                else{
                   flag= deptoFacade.actualizarDepartamento(depto, idpais, "U");
                   if(flag.equals("0")){
                       validationBean.lanzarMensaje("info", "titleDepto", "lblEditarSuccess");
                       //listaDepto= new ArrayList<DepartamentoForm>();
                      // actualizarComponentes();
                   }
                }
                limpiar();
                
                
            }
            
    }
    
    public void actualizarComponentes(){
        listaDepto=deptoFacade.obtenerDepartamentos(idpais);
        validationBean.updateComponent("deptoForm:deptoTbl");  
        validationBean.updateComponent("deptoForm:nomDepto"); 
        limpiar();
    }
    
    public void validareliminar(){
        if(depto.getIddepartamento()==null || depto.getIddepartamento().equals("0")){
            validationBean.lanzarMensaje("warn", "titleDepto", "lblSelectReg");
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
        String flag= "";
       //flag= paisFacade.actualizarPais(pais, "D");
       flag= deptoFacade.actualizarDepartamento(depto, idpais, "D");
        if(flag.equals("0")){
            validationBean.lanzarMensaje("info", "titleDepto", "lblEliminarSuccess");
            listaDepto= new ArrayList<DepartamentoForm>();
        }
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleDepto", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        limpiar();
        
    }

    public void onSelectPais(PaisForm obj) {
        setIdpais(obj.getIdpais());
        listaDepto=deptoFacade.obtenerDepartamentos(idpais);
        validationBean.updateComponent("deptoForm:deptoTbl");
    }

    public void unSelectPais() {
        //limpiar();
    }
    
    public void onSelectDepto(DepartamentoForm obj) {
        depto.setIddepartamento(obj.getIddepartamento());
        depto.setNombredepartamento(obj.getNombredepartamento());
        //RequestContext.getCurrentInstance().update("deptoTbl");
    }

    public void unSelectDepto() {
        limpiar();
    }

    public void limpiar() {
        setIdpais("");
        depto.setIddepartamento("0");
        depto.setNombredepartamento("");
        listaDepto= new ArrayList<DepartamentoForm>();
    }

}
