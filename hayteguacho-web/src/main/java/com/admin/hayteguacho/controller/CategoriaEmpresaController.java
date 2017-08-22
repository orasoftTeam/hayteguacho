/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.util.ValidationBean;
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
@ManagedBean(name = "categoriaEmpresaController")
public class CategoriaEmpresaController {

    @EJB
    CategoriaEmpresaFacade catFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter
    @Setter
    List<CategoriaEmpresaForm> listaCategorias = new ArrayList<>();
    private @Getter @Setter CategoriaEmpresaForm cemp = new CategoriaEmpresaForm();

    @PostConstruct
    public void init() {
        listaCategorias = catFacade.obtenerCategorias();
        cemp.setIdcategoria("0");
    }

    public void actualizarPais() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(cemp.getNombrecategoria(), "warn", "titleCategoriaEmpresa", "lblReqNombreCategoria")
                && validationBean.validarSoloLetras(cemp.getNombrecategoria(), "warn", "titleCategoriaEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(cemp.getNombrecategoria(), 4, 25,"warn", "titleCategoriaEmpresa", "lblLongitudNombreCategoria")){
            if(cemp.getIdcategoria()==null || cemp.getIdcategoria().equals("0")){
               flag= catFacade.actualizarCategoria(cemp, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCategoriaEmpresa", "lblGuardarSuccess");
            }
            else{
                flag= catFacade.actualizarCategoria(cemp, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCategoriaEmpresa", "lblEditarSuccess");
            }
            limpiar();
            listaCategorias = catFacade.obtenerCategorias();
        }
    }
    
    public void validareliminar(){
        if(cemp.getIdcategoria()==null || cemp.getIdcategoria().equals("0")){
            validationBean.lanzarMensaje("warn", "titleCategoriaEmpresa", "lblSelectRegCategoria");
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
        String flag= catFacade.actualizarCategoria(cemp, "D");
        if(flag.equals("0"))
            validationBean.lanzarMensaje("info", "titleCategoriaEmpresa", "lblEliminarSuccess");
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleCategoriaEmpresa", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaCategorias = catFacade.obtenerCategorias();
        limpiar();
        
    }

    public void onSelect(CategoriaEmpresaForm obj) {
        cemp.setIdcategoria(obj.getIdcategoria());
        cemp.setNombrecategoria(obj.getNombrecategoria());
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
       cemp.setIdcategoria("0");
       cemp.setNombrecategoria("");
    }

}
