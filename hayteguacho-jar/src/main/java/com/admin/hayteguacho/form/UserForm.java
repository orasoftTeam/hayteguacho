/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.form;


import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
public class UserForm {
    private @Getter @Setter String tipo;
    private @Getter @Setter String identificador;
    private @Getter @Setter String nombre;
    private @Getter @Setter String correo;
    private @Getter @Setter String contrasena;
    private @Getter @Setter String nombrerol;
    private @Getter @Setter String idrol;
    private @Getter @Setter String idrolusuario;

    public UserForm(String tipo, String identificador, String nombre, String correo, String contrasena, String nombrerol, String idrol, String idrolusuario) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombrerol = nombrerol;
        this.idrol = idrol;
        this.idrolusuario = idrolusuario;
    }
    
    public UserForm(){
        
    }
    
}
