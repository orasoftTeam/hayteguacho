/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;


import com.admin.hayteguacho.form.PuestoTrabajoForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.PuestoTrabajoFacade;
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
@ManagedBean(name = "puestoController")
public class PuestoTrabajoController {

    @EJB
    PuestoTrabajoFacade puestoFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<PuestoTrabajoForm> listaPuestos = new ArrayList<>();
    
    private @Getter @Setter String idCategoria;
    
    //private @Getter @Setter List<CategoriaEmpresaForm> listaCategorias= new ArrayList<>();
    private @Getter @Setter PuestoTrabajoForm puesto = new PuestoTrabajoForm();

    @PostConstruct
    public void init() {
        //listaCargos = cargoFacade.obtenerCargos();
        puesto.setIdpuestotrabajo("0");
    }

    public void actualizarPuesto() {
        
        String flag=""; 
        if(     validationBean.validarSeleccion(idCategoria, "warn", "titlePuestoTrabajo", "lblSelectRegCategoria")
                &&
                validationBean.validarCampoVacio(puesto.getNombrepuestotrabajo(), "warn", "titlePuestoTrabajo", "lblNombreReqPuestoTrabajo")
                && validationBean.validarSoloLetras(puesto.getNombrepuestotrabajo().replace(" ", ""), "warn", "titlePuestoTrabajo", "lblSoloLetras")
                && validationBean.validarLongitudCampo(puesto.getNombrepuestotrabajo(), 4, 25,"warn", "titlePuestoTrabajo", "lblLongitudPuestoTrabajo")){
            if(puesto.getIdpuestotrabajo()==null || puesto.getIdpuestotrabajo().equals("0")){
                puesto.setIdcategoria(idCategoria);
               flag= puestoFacade.actualizarPuesto(puesto, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titlePuestoTrabajo", "lblGuardarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            else{
                flag= puestoFacade.actualizarPuesto(puesto, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titlePuestoTrabajo", "lblEditarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleMuni", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleMuni", "lblErrorTransact");
                   }
            }
            //limpiar();
            listaPuestos= puestoFacade.obtenerPuestosByIdCategoria(idCategoria);
            puesto.setNombrepuestotrabajo("");
        }
    }
    
    public void validarEliminar(){
        if(puesto.getIdpuestotrabajo()==null || puesto.getIdpuestotrabajo().equals("0")){
            validationBean.lanzarMensaje("warn", "titlePuestoTrabajo", "lblPuestoSelectReq");
        }
        else{
            validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void cerrarDialogo(){
        puesto.setIdpuestotrabajo("0");
        puesto.setNombrepuestotrabajo("");
       // validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        //validationBean.updateComponent("puestosForm:puestoTbl");
    }
    
    public void eliminar(){
        String flag= puestoFacade.actualizarPuesto(puesto, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titlePuestoTrabajo", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titlePuestoTrabajo", "lblEliminarError");
        //validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaPuestos= puestoFacade.obtenerPuestosByIdCategoria(idCategoria);
        //validationBean.updateComponent("puestosForm:puestoTbl");
        puesto.setIdpuestotrabajo("0");
        puesto.setNombrepuestotrabajo("");
        
    }
    
    public void cambiarCategoria(){
        if(!idCategoria.equals("")){
            listaPuestos= puestoFacade.obtenerPuestosByIdCategoria(idCategoria);
        }
        else{
            listaPuestos= new ArrayList<>();
        }
        puesto.setIdcategoria(idCategoria);
        validationBean.updateComponent("puestosForm:puestoTbl");
    }
/*
    public void onSelect(PuestoTrabajoForm obj) {
        puesto.setIdpuestotrabajo(obj.getIdpuestotrabajo());
        puesto.setNombrepuestotrabajo(obj.getNombrepuestotrabajo());
        validationBean.updateComponent("puestosForm:puestoTbl");
    }*/
    
    public void onSelect(int index) {
        PuestoTrabajoForm obj= listaPuestos.get(index);
        puesto.setIdpuestotrabajo(obj.getIdpuestotrabajo());
        puesto.setNombrepuestotrabajo(obj.getNombrepuestotrabajo());
        
    }
    public void unSelect() {
        limpiar();
        validationBean.updateComponent("puestosForm:puestoTbl");
    }

    public void limpiar() {
        puesto.setIdpuestotrabajo("0");
        puesto.setNombrepuestotrabajo("");
        listaPuestos= new ArrayList<>();
        idCategoria="";
    }

}
