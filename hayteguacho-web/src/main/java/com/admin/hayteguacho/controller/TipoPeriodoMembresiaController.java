/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.TipoPeriodoMembresiaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.TipoPeriodoMembresiaFacade;
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
@ManagedBean(name = "tipoPeriodoMembresiaController")
public class TipoPeriodoMembresiaController {
    
    @EJB
    TipoPeriodoMembresiaFacade tpmfacade;
    
    @EJB
    ValidationBean vb;
    
    private @Getter @Setter
    List<TipoPeriodoMembresiaForm> listaTipoPeriodoMembresia = new ArrayList<>();
    
    private @Getter @Setter TipoPeriodoMembresiaForm tpmform = new TipoPeriodoMembresiaForm();
    
    @PostConstruct
    public void init(){
    listaTipoPeriodoMembresia = tpmfacade.obtenerTiposPeriodosMembresia();
    tpmform.setIdtipoperiodomembresia("0");
    }
    
    public void actualizarTipoPeriodoMembresia(){
    String flag="";
        if (vb.validarCampoVacio(tpmform.getNombretipoperiodomembresia().replace(" ", ""), "warn", "titleTipoPeriodoMembresia", "lblNombreReqTipoPeriodoMembresia")
                && vb.validarSoloLetras(tpmform.getNombretipoperiodomembresia().replace(" ", ""), "warn","titleTipoPeriodoMembresia", "lblSoloLetras")
                && vb.validarLongitudCampo(tpmform.getNombretipoperiodomembresia().replace(" ", ""), 4, 25, "warn", "titleTipoPeriodoMembresia", "lblLongitudNombreTipoPeriodoMembresia")) {
            
            if (tpmform.getIdtipoperiodomembresia() == null || tpmform.getIdtipoperiodomembresia().equals("0")) {
                flag = tpmfacade.actualizarTipoPeriodoMembresia(tpmform, "A");
                if (flag.equals("0")) {
                    vb.lanzarMensaje("info", "titleTipoPeriodoMembresia","lblGuardarSuccess");
                }
            }else{
             flag = tpmfacade.actualizarTipoPeriodoMembresia(tpmform, "U");
                if (flag.equals("0")) {
                    vb.lanzarMensaje("info", "titleTipoPeriodoMembresia", "lblEditarSuccess");
                }
            }
            limpiar();
            listaTipoPeriodoMembresia = tpmfacade.obtenerTiposPeriodosMembresia();
        }
    
    }
    
    public void limpiar(){
     tpmform.setIdtipoperiodomembresia("0");
     tpmform.setNombretipoperiodomembresia("");
             
    }
    
    public void unSelect(){
    limpiar();
        System.out.println("com.admin.hayteguacho.controller.TipoPeriodoMembresiaController.unSelect()");
    }
    
    public void onSelect(int index){
        TipoPeriodoMembresiaForm tpmf = listaTipoPeriodoMembresia.get(index);
    tpmform.setIdtipoperiodomembresia(tpmf.getIdtipoperiodomembresia());
    tpmform.setNombretipoperiodomembresia(tpmf.getNombretipoperiodomembresia());
        System.out.println("com.admin.hayteguacho.controller.TipoPeriodoMembresiaController.onSelect()");
    }
    
    public void validarEliminar(){
        if (tpmform.getIdtipoperiodomembresia() == null || tpmform.getIdtipoperiodomembresia().equals("0")) {
            vb.lanzarMensaje("warn", "titleTipoPeriodoMembresia", "lblSelectRegTipoPeriodoMembresia");
        }else{
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void cerrarDialogo(){
    limpiar();
   // vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void eliminar(){
    String flag = tpmfacade.actualizarTipoPeriodoMembresia(tpmform, "D");
        if (flag.equals("0")) {
            vb.lanzarMensaje("info","titleTipoPeriodoMembresia" , "lblEliminarSuccess");
            listaTipoPeriodoMembresia = tpmfacade.obtenerTiposPeriodosMembresia();
            limpiar();
        }else if (flag.equals("-1")|| flag.equals("-2")) {
            vb.lanzarMensaje("error", "titleTipoPeriodoMembresia", "lblEliminarError");
            //vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        }
    }
    
    
}
