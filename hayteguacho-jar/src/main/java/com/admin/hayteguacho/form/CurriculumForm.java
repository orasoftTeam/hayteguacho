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
public class CurriculumForm {
    
    private @Getter @Setter String idcandidatoxofertalaboral;
    private @Getter @Setter String idofertalaboral;
    private @Getter @Setter String categoria;
    private @Getter @Setter String titulo;
    private @Getter @Setter String nombre;
    private @Getter @Setter String puesto;
    private @Getter @Setter String nombrecandidato;

    public CurriculumForm(String idcandidatoxofertalaboral, String idofertalaboral, String categoria, String titulo, String nombre, String puesto, String nombrecandidato) {
        this.idcandidatoxofertalaboral = idcandidatoxofertalaboral;
        this.idofertalaboral = idofertalaboral;
        this.categoria = categoria;
        this.titulo = titulo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.nombrecandidato = nombrecandidato;
    }

   

    public CurriculumForm() {
    }
    
    
    
}
