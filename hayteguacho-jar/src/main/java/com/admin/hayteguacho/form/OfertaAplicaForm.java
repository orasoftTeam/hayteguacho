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
public class OfertaAplicaForm {
    private @Getter @Setter String titulo;
    private @Getter @Setter String inscritos;

    public OfertaAplicaForm() {
    }

    public OfertaAplicaForm(String titulo, String inscritos) {
        this.titulo = titulo;
        this.inscritos = inscritos;
    }
    
    
}
