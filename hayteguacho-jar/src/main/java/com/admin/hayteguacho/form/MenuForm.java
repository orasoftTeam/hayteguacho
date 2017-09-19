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
public class MenuForm {
    private @Getter @Setter String identificador;
    private @Getter @Setter String nombremenu;
    private @Getter @Setter String opcion;

    public MenuForm(String identificador, String nombremenu, String opcion) {
        this.identificador = identificador;
        this.nombremenu = nombremenu;
        this.opcion = opcion;
    }


    
    public MenuForm(){
        
    }
}
