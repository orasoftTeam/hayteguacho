
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;
import com.admin.hayteguacho.util.ValidationBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import com.admin.hayteguacho.form.UserForm;
import com.hayteguacho.facade.CandidatoxofertaMostrarFacade;
import com.hayteguacho.facade.UserFacade;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
/**
 *
 * @author orasoft
 */

@ViewScoped
@ManagedBean(name = "recuperar")

public class RecuperarBean {
    @EJB
    CandidatoxofertaMostrarFacade cxOm;
    @EJB
    ValidationBean validationBean;
     
     
     @EJB
    private UserFacade userFacade;
     
     private @Getter
    @Setter
    String usuario;
     private @Getter
    @Setter
    String asunto="Recuperacion de cuenta";
     private @Getter
    @Setter String codigo;
      private @Getter
    @Setter String codigoV;
      
      private @Getter
    @Setter UserForm usuariorec;
      @Inject 
      LoginController lc;
      
      private @Getter @Setter boolean mostrar = false;
      
    @PostConstruct
    public void init() {
       
         
    }
     
     public void modal(){
         validationBean.ejecutarJavascript("$('.recuperarCuenta').modal('show');");
     }
     public void regis(){
         validationBean.ejecutarJavascript("$('.seleccion').modal('show');");
     }
    public void cuenta(){
          usuariorec = userFacade.getRecuperaacion(getUsuario());
          codigoV=validationBean.generarCodigo();
         if (usuariorec.getCorreo() != null) {
            System.err.println("Enviado");
             try {
                 cxOm.enviarCorreo(usuariorec.getCorreo(), asunto,"Utilice este codigo para restablecer su cuenta"+"  "+ codigoV);
            validationBean.lanzarMensaje("info", "lblRecuperarCuenta", "lblExito");
            mostrar = true;
            validationBean.updateComponent("recuperarForm:btnCorreo");
            validationBean.updateComponent("recuperarForm:btnFooter");
                   // validationBean.updateComponent("formGrl:growl");
                   
             } catch (Exception e) {
                 limpiar();
                 e.printStackTrace();
             }
        } else if (usuariorec.getCorreo() == null) {
            
            
            System.err.println("Entro nulo");
            validationBean.lanzarMensaje("warn", "lblRecuperarCuenta", "lblCorreoV");
            limpiar();
            validationBean.updateComponent("recuperarForm:btnCorreo");
            //validationBean.updateComponent("formGrl:growl");
        }
         
         
       
    }
    
    public void verificador(){
         if(codigoV.equals(codigo)){
             validationBean.lanzarMensaje("info", "lblRecuperarCuenta", "lblExitoV");
            lc.setUsuario(usuariorec.getCorreo());
            lc.setPassword(usuariorec.getContrasena());
            lc.logearR();
             mostrar = false;
             limpiar();
             //validationBean.updateComponent("formGrl:growl");
             //validationBean.redirecionar();
         }else{
             validationBean.lanzarMensaje("error", "lblRecuperarCuenta", "lblErrorV");
             //validationBean.updateComponent("formGrl:growl");
             mostrar = false;
             limpiar();
         }
        
    }
    
    public void limpiar(){
    usuario = "";
    codigoV = "";
    codigo = "";
    usuariorec = new UserForm();
    }
}
