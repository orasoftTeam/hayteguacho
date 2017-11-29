/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CandidatoxofertaMostrarFacade;
import com.hayteguacho.facade.UserFacade;
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
@ManagedBean(name = "contacto")
public class ContactenosController {
    
    @EJB
    CandidatoxofertaMostrarFacade cxO;
    
    @EJB
    ValidationBean vb;
    
    private @Setter @Getter String correo;
    private @Setter @Getter String asunto;
    private @Setter @Getter String mensaje;
    
    public void enviar(){
    
        if (vb.validarCampoVacio(correo, "warn","lblContactoForm" , "lblContactoEmailVacio")
            && vb.validarEmail(correo, "warn", "lblContactoForm", "lblContactoEmailValido")
            && vb.validarLongitudCampo(correo, 10, 50, "warn", "lblContactoForm", "lblContactoEmailLongitud")
            && vb.validarCampoVacio(asunto, "warn","lblContactoForm" , "lblContactoAsuntoVacio")
            && vb.validarLongitudCampo(asunto, 4, 50, "warn", "lblContactoForm", "lblContactoAsuntoLongitud")
            && vb.validarCampoVacio(mensaje, "warn","lblContactoForm" , "lblContactoMensajeVacio")
            && vb.validarLongitudCampo(mensaje, 10, 300, "warn","lblContactoForm" , "lblContactoMensajeLongitud")
                ) {
            
            try {
               cxO.enviarCorreo("hayteguachoprueba@gmail.com", asunto,"<"+correo+"> "+mensaje);
               vb.lanzarMensaje("info", "lblContactoForm", "lblContactoConfirmacion"); 
            } catch (Exception e) {
                System.out.println("com.admin.hayteguacho.controller.ContactenosController.enviar()");
                e.printStackTrace();
                vb.lanzarMensaje("error", "lblContactoForm", "lblContactoFail");
            }
            limpiar(); 
        }
        
    
    }
    
    public void limpiar(){
    correo = "";
    asunto = "";
    mensaje = "";
    }
    
}
