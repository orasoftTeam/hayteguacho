/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.DepartamentoForm;
import com.admin.hayteguacho.form.MunicipioForm;
import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.MunicipioFacade;
import java.util.ArrayList;
import java.util.List;
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
@ManagedBean(name = "muniController")
public class MunicipioController {

    @EJB
    MunicipioFacade muniFacade;
   
    @EJB
    DepartamentoFacade deptoFacade;
    
    @EJB
    ValidationBean validationBean;

    private @Getter @Setter List<MunicipioForm> listaMuni = new ArrayList<>();
    private @Getter @Setter List<DepartamentoForm> listaDepto = new ArrayList<>();
    private @Getter @Setter MunicipioForm muni = new MunicipioForm();
    
    private @Getter @Setter String idpais;
    private @Getter @Setter String iddepto;

    @PostConstruct
    public void init() {
        //listaDepto = deptoFacade.obtenerPaises(idpais);
        muni.setIdciudad("0");
    }

    public void actualizarMunicipio() {
        
        String flag=""; 
        if(idpais==null || idpais.equals("")){
            validationBean.lanzarMensaje("warn", "titleMuni", "lblPaisSelectReg");
            limpiar();
        }
        else if(iddepto==null || iddepto.equals("")){
            validationBean.lanzarMensaje("warn", "titleMuni", "lblDepartamentoSelectReg");
            limpiar();
        }
        
        else
            if(validationBean.validarCampoVacio(muni.getNombreciudad(), "warn", "titleMuni", "lblReqNombreMuni")
                    && validationBean.validarSoloLetras(muni.getNombreciudad(), "warn", "titleMuni", "lblSoloLetras")
                    && validationBean.validarLongitudCampo(muni.getNombreciudad(), 4, 25,"warn", "titleMuni", "lblLongitudNombreMuni")){
                if(muni.getIdciudad()==null || muni.getIdciudad().equals("0")){
                    flag=muniFacade.actualizarMunicipios(muni, iddepto, "A");
                   if(flag.equals("0")){
                       validationBean.lanzarMensaje("info", "titleMuni", "lblGuardarSuccess");
                       //listaMuni= new ArrayList<MunicipioForm>();
                       //listaDepto= new ArrayList<>();
                        //actualizarComponentes();   
                   }
                }
                else{
                   flag=muniFacade.actualizarMunicipios(muni, iddepto, "U");
                   if(flag.equals("0")){
                       validationBean.lanzarMensaje("info", "titleMuni", "lblEditarSuccess");
                       //listaMuni= new ArrayList<MunicipioForm>();
                       //listaDepto= new ArrayList<>();
                      // actualizarComponentes();
                   }
                }
                limpiar();
                
                
            }
            
    }
    
    public void actualizarComponentes(){
        /*
        //listaDepto=deptoFacade.obtenerDepartamentos(idpais);
        validationBean.updateComponent("deptoForm:deptoTbl");  
        validationBean.updateComponent("deptoForm:nomDepto"); 
        limpiar();
        */
    }
    
    public void validareliminar(){
        if(muni.getIdciudad()==null || muni.getIdciudad().equals("0")){
            validationBean.lanzarMensaje("warn", "titleMuni", "lblSelectRegMunicipio");
        }
        else{
            validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void cerrarDialogo(){
        limpiar();
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void eliminar(){
        String flag= "";
        flag=muniFacade.actualizarMunicipios(muni, iddepto, "D");
        if(flag.equals("0")){
            validationBean.lanzarMensaje("info", "titleMuni", "lblEliminarSuccess");
            /*
            listaMuni= new ArrayList<MunicipioForm>();
            listaDepto= new ArrayList<>();
            */
        }
        
        else if(flag.equals("-1") || flag.equals("-2"))
            validationBean.lanzarMensaje("error", "titleMuni", "lblEliminarError");
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        limpiar();
        
    }

    public void onSelectPais(PaisForm obj) {
        setIdpais(obj.getIdpais());
        listaDepto=deptoFacade.obtenerDepartamentos(idpais);
        listaMuni= new ArrayList<MunicipioForm>();
        validationBean.updateComponent("muniForm:deptoTbl");
        validationBean.updateComponent("muniForm:muniTbl");
    }

    public void unSelectPais() {
        //limpiar();
    }
    
    public void onSelectDepto(DepartamentoForm obj) {
        setIddepto(obj.getIddepartamento());
        listaMuni= muniFacade.obtenerMunicipios(iddepto);
        validationBean.updateComponent("muniForm:muniTbl");
    }

    public void unSelectDepto() {
        //limpiar();
    }
    
    public void onSelectMuni(MunicipioForm obj) {
        muni.setIdciudad(obj.getIdciudad());
        muni.setNombreciudad(obj.getNombreciudad());
    }

    public void unSelectMuni() {
        limpiar();
    }

    public void limpiar() {
        setIdpais("");
        setIddepto("");
        muni.setIdciudad("0");
        muni.setNombreciudad("");
        listaMuni= new ArrayList<MunicipioForm>();
        listaDepto= new ArrayList<>();
    }

}
