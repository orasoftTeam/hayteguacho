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
 * @author dell
 */
public class InscritoForm {
    private @Getter @Setter String titulo;
    private @Getter @Setter String candidato;
    private @Getter @Setter String email;
    private @Getter @Setter String tels;
    private @Getter @Setter String curriculum;

    public InscritoForm() {
    }

    public InscritoForm(String titulo, String candidato, String email, String tels, String curriculum) {
        this.titulo = titulo;
        this.candidato = candidato;
        this.email = email;
        this.tels = tels;
        this.curriculum = curriculum;
    }
    
    
}
