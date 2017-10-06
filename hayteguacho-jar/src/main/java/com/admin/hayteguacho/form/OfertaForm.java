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
public class OfertaForm {
    private @Getter @Setter String idofertalaboral;
    private @Getter @Setter String idempresa_tbl;
    private @Getter @Setter String idjornadalaboral;
    private @Getter @Setter String idtipocontrato;
    private @Getter @Setter String idpuestotrabajo;
    private @Getter @Setter String idciudad;
    private @Getter @Setter String tituloofertalaboral;
    private @Getter @Setter String fechavigenciaofertalaboral;
    private @Getter @Setter String fechahoraofertalaboral;
    private @Getter @Setter String fechacontratacionofertalaboral;
    private @Getter @Setter String cantidadvacante;
    private @Getter @Setter String salariominofertalaboral;
    private @Getter @Setter String salariomaxofertalaboral;
    private @Getter @Setter String descripcionofertalaboral;
    private @Getter @Setter String requerimientosofertalaboral;
    private @Getter @Setter String habilidadesofertalaboral;
    private @Getter @Setter String conocimientoofertalaboral;
    private @Getter @Setter String estadoofertalaboral;

    public OfertaForm(String idofertalaboral, String idempresa_tbl, String idjornadalaboral, String idtipocontrato, String idpuestotrabajo, String idciudad, String tituloofertalaboral, String fechavigenciaofertalaboral, String fechahoraofertalaboral, String fechacontratacionofertalaboral, String cantidadvacante, String salariominofertalaboral, String salariomaxofertalaboral, String descripcionofertalaboral, String requerimientosofertalaboral, String habilidadesofertalaboral, String conocimientoofertalaboral, String estadoofertalaboral) {
        this.idofertalaboral = idofertalaboral;
        this.idempresa_tbl=idempresa_tbl;
        this.idjornadalaboral = idjornadalaboral;
        this.idtipocontrato = idtipocontrato;
        this.idpuestotrabajo = idpuestotrabajo;
        this.idciudad = idciudad;
        this.tituloofertalaboral = tituloofertalaboral;
        this.fechavigenciaofertalaboral = fechavigenciaofertalaboral;
        this.fechahoraofertalaboral = fechahoraofertalaboral;
        this.fechacontratacionofertalaboral = fechacontratacionofertalaboral;
        this.cantidadvacante = cantidadvacante;
        this.salariominofertalaboral = salariominofertalaboral;
        this.salariomaxofertalaboral = salariomaxofertalaboral;
        this.descripcionofertalaboral = descripcionofertalaboral;
        this.requerimientosofertalaboral = requerimientosofertalaboral;
        this.habilidadesofertalaboral = habilidadesofertalaboral;
        this.conocimientoofertalaboral = conocimientoofertalaboral;
        this.estadoofertalaboral = estadoofertalaboral;
    }
    
    public OfertaForm(){
        
    }
    

}
