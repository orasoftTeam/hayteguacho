/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
import com.hayteguacho.facade.EmpresaFacade;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "dashboardEmpresa")
public class DashboardEmpresaController {
    @EJB
    ValidationBean vb;
    
    @EJB
    EmpresaFacade ef;
    
    @Inject
    LoginController login;
    
    @EJB
    CategoriaEmpresaFacade cef;
    
    private @Getter
    @Setter
    EmpresaForm loggedEmp = new EmpresaForm();
    
    @PostConstruct
    public void init(){
    loggedEmp = ef.obtenerEmp(login.getUserLog().getIdentificador());
    }
    
    
    public String getCat(){
    CategoriaEmpresaForm catForm = cef.entityToDto(cef.find(new BigDecimal(loggedEmp.getIdcategoria())), new CategoriaEmpresaForm());
    return catForm.getNombrecategoria();
    }
}
