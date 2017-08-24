/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CargoEmpresaFacade;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
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
@ManagedBean(name = "cargoEmpresaController")
public class CargoEmpresaController {

    @EJB
    CargoEmpresaFacade cargoFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<CargoEmpresaForm> listaCargos = new ArrayList<>();
    private @Getter @Setter CargoEmpresaForm cemp = new CargoEmpresaForm();

    @PostConstruct
    public void init() {
        listaCargos = cargoFacade.obtenerCargos();
        cemp.setIdcargoempresa("0");
    }

    public void actualizarPais() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(cemp.getNombrecargoempresa().replace(" ", ""), "warn", "titleCargoEmpresa", "lblNombreReqCargoEmpresa")
                && validationBean.validarSoloLetras(cemp.getNombrecargoempresa().replace(" ", ""), "warn", "titleCargoEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(cemp.getNombrecargoempresa().replace(" ", ""), 4, 25,"warn", "titleCargoEmpresa", "lblLongitudNombreCargo")){
            if(cemp.getIdcargoempresa()==null || cemp.getIdcargoempresa().equals("0")){
               flag= cargoFacade.actualizarCargo(cemp, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCargoEmpresa", "lblGuardarSuccess");
            }
            else{
                flag= cargoFacade.actualizarCargo(cemp, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCargoEmpresa", "lblEditarSuccess");
            }
            limpiar();
            listaCargos = cargoFacade.obtenerCargos();
        }
    }
    
    public void validareliminar(){
        if(cemp.getIdcargoempresa()==null || cemp.getIdcargoempresa().equals("0")){
            validationBean.lanzarMensaje("warn", "titleCargoEmpresa", "lblSelectRegCargo");
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
        String flag= cargoFacade.actualizarCargo(cemp, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titleCargoEmpresa", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleCargoEmpresa", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaCargos = cargoFacade.obtenerCargos();
        limpiar();
        
    }

    public void onSelect(CargoEmpresaForm obj) {
        cemp.setIdcargoempresa(obj.getIdcargoempresa());
        cemp.setNombrecargoempresa(obj.getNombrecargoempresa());
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
       cemp.setIdcargoempresa("0");
       cemp.setNombrecargoempresa("");
    }

}
