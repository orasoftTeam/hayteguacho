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
public class TotalCategoriasForm {
    private @Getter @Setter String total;
    private @Getter @Setter String totalCategoria;
    private @Getter @Setter String categoria;
    private @Getter @Setter String idCategoria;

    public TotalCategoriasForm(String total, String totalCategoria, String categoria, String idCategoria) {
        this.total = total;
        this.totalCategoria = totalCategoria;
        this.categoria = categoria;
        this.idCategoria=idCategoria;
    }
    
    public TotalCategoriasForm(){
        
    }
}
