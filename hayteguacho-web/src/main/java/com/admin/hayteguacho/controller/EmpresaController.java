/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

/**
 *
 * @author LAP
 */

import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.DepartamentoForm;
import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.form.MunicipioForm;
import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.form.TipologiaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CargoEmpresaFacade;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.EmpresaFacade;
import com.hayteguacho.facade.MunicipioFacade;
import com.hayteguacho.facade.PaisFacade;
import com.hayteguacho.facade.TipologiaFacade;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@ViewScoped
@ManagedBean(name = "empresaController")
public class EmpresaController {
    private @Getter @Setter EmpresaForm empresa= new EmpresaForm();
    
    @EJB
    PaisFacade paisFacade;
    
    @EJB
    DepartamentoFacade deptoFacade;
    
    @EJB
    MunicipioFacade muniFacade;
    
    @EJB
    ValidationBean validationBean;
    
    @EJB
    CategoriaEmpresaFacade catFacade;
    
    @EJB
    CargoEmpresaFacade cargoFacade;
    
    @EJB
    TipologiaFacade tipologiaFacade;
    
    @EJB 
    EmpresaFacade empresaFacade;
    
    private @Getter @Setter List<DepartamentoForm> listaDepto;
    private @Getter @Setter List<MunicipioForm> listaMuni;
    private @Getter @Setter String idDepto;
    private @Getter @Setter String idMuni;
    private @Getter @Setter List<CategoriaEmpresaForm> listaCategoria;
    private @Getter @Setter List<CargoEmpresaForm> listaCargo;
    private @Getter @Setter String idCategoria;
    private @Getter @Setter String idCargo;
    private @Getter @Setter List<TipologiaForm> listaTipologia;
    private @Getter @Setter  String idTipologia;
    
    
    @PostConstruct
    public void init(){
        String pais= Locale.getDefault().getDisplayCountry();
        empresa.setNompais(pais.toUpperCase());
        List<PaisForm> tmp= paisFacade.obtenerPaisesPorNombre(pais.toUpperCase());
        if(!tmp.isEmpty()){
            empresa.setIdpais(tmp.get(0).getIdpais());
            listaDepto= deptoFacade.obtenerDepartamentos(tmp.get(0).getIdpais());
        }
        listaCategoria= catFacade.obtenerCategorias();
        listaCargo= cargoFacade.obtenerCargos();
        listaTipologia= tipologiaFacade.obtenerTipologias();
        empresa.setIdempresa("0");
    }
    
    public void changeDepartamento(){
        listaMuni= muniFacade.obtenerMunicipios(idDepto);
        validationBean.updateComponent("cargosForm:cmbMuni");
    }
    
    public void actualizarEmpresa() {
        
        String flag=""; 
        empresa.setIdcargoempresa(idCargo);
        empresa.setIdcategoria(idCategoria);
        empresa.setIdciudad(idMuni);
        empresa.setIdtipologia(idTipologia);
        if(validationBean.validarCampoVacio(empresa.getEmail().replace(" ", ""), "warn", "titleEmpresa", "lblEmailReqEmpresa")
                && validationBean.validarEmail(empresa.getEmail(), "warn", "titleEmpresa", "lblEmailValido")
                &&
                validationBean.validarCampoVacio(empresa.getPassword().replace(" ", ""), "warn", "titleEmpresa", "lblClaveReqEmpresa")
                && validationBean.validarLongitudCampo(empresa.getPassword().replace(" ", ""), 5, 10,"warn", "titleEmpresa", "lblLongitudClaveEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getNombrecontacto().replace(" ", ""), "warn", "titleEmpresa", "lblNomContactoReqEmpresa")
                && validationBean.validarSoloLetras(empresa.getNombrecontacto().replace(" ", ""), "warn", "titleEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(empresa.getNombrecontacto().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudNomContactoEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getApellidocontacto().replace(" ", ""), "warn", "titleEmpresa", "lblApeContactoReqEmpresa")
                && validationBean.validarSoloLetras(empresa.getApellidocontacto().replace(" ", ""), "warn", "titleEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(empresa.getApellidocontacto().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudApellidoContactoEmpresa")
                && validationBean.validarSeleccion(empresa.getIdcargoempresa(),"warn", "titleEmpresa", "lblSelectRegCargo")
                && validationBean.validarLongitudCampo(empresa.getTelefono1(), 8, 8, "warn", "titleEmpresa", "lblTelReq")
                &&
                validationBean.validarCampoVacio(empresa.getNombreempresa().replace(" ", ""), "warn", "titleEmpresa", "lblNameReqEmpresa")
                && validationBean.validarLongitudCampo(empresa.getNombreempresa().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudNombreEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getRazonsocial().replace(" ", ""), "warn", "titleEmpresa", "lblRazonReqEmpresa")
                && validationBean.validarLongitudCampo(empresa.getRazonsocial().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudRazonEmpresa")
                &&
                validationBean.validarLongitudCampo(empresa.getIdtributaria().replace(" ", ""), 8, 15,"warn", "titleEmpresa", "lblLongitudTributarioEmpresa")
                &&
                validationBean.validarSeleccion(empresa.getIdpais(), "warn", "titleEmpresa", "lblSelectPais")
                &&
                validationBean.validarSeleccion(empresa.getIdciudad(), "warn", "titleEmpresa", "lblSelectRegMunicipio")
                &&
                validationBean.validarCampoVacio(empresa.getDireccionempresa(), "warn", "titleEmpresa", "lblDirReqEmpresa")
                &&
                 validationBean.validarSeleccion(empresa.getIdcategoria(), "warn", "titleEmpresa", "lblSelectRegCategoria")
                &&
                validationBean.validarCampoVacio(empresa.getNumtrabajadores(), "warn", "titleEmpresa", "lblNumTrabReqEmpresa")
                &&
                validationBean.validarSeleccion(empresa.getIdtipologia(), "warn", "titleEmpresa", "lblSelectRegTipologia")
                &&
                validationBean.validarLongitudCampo(empresa.getDescripcionempresa(), 15, 255,"warn", "titleEmpresa", "lblLongitudDescripcionEmpresa")){
            
            if(empresa.getIdempresa()==null || empresa.getIdempresa().equals("0")){
               flag= empresaFacade.actualizarEmpresa(empresa, "A"); 
               if(flag.equals("0")){
                   validationBean.lanzarMensaje("info", "titleEmpresa", "lblGuardarSuccess");
                   limpiar();
               }
               else{
                   validationBean.lanzarMensaje("info", "titleEmpresa", "lblGuardarError");
               }
            }
            
            
        }
    }
    public void limpiar() {
       empresa= new EmpresaForm();
       empresa.setIdempresa("0");
    }
}
