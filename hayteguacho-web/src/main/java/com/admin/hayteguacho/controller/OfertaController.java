/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.DepartamentoForm;
import com.admin.hayteguacho.form.MunicipioForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.form.PuestoTrabajoForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.MunicipioFacade;
import com.hayteguacho.facade.OfertaFacade;
import com.hayteguacho.facade.PaisFacade;
import com.hayteguacho.facade.PuestoTrabajoFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@ViewScoped
@ManagedBean(name = "ofertaController")
public class OfertaController {

    @EJB
    OfertaFacade ofertaFacade;
    
    @EJB
    @Getter
    @Setter
    ValidationBean validationBean;
    
    @EJB
    PaisFacade paisFacade;
    
    @EJB
    DepartamentoFacade deptoFacade;
    
    @EJB
    MunicipioFacade muniFacade;
    
    @EJB
    PuestoTrabajoFacade puestoFacade;
    
    @Inject
    LoginController loginBean;
    
    
    private @Getter @Setter String idempresa;
    
    private @Getter @Setter String idcategoria;
    
    private @Getter @Setter String idcategoriafilter;
    
    private @Getter @Setter String estadofilter;
    
    private @Getter @Setter String iddepto;
    
    private @Getter @Setter String pais;
    
    private @Getter @Setter boolean chequeado;

    private @Getter @Setter List<OfertaForm> listaOfertas = new ArrayList<>();
    private @Getter @Setter List<OfertaForm> listaOfertasEliminar = new ArrayList<>();
    private @Getter @Setter OfertaForm oferta = new OfertaForm();
    
    private @Getter @Setter List<PuestoTrabajoForm> listaPuestos = new ArrayList<>();
    
    private @Getter @Setter List<DepartamentoForm> listaDepto= new ArrayList<>();
    private @Getter @Setter List<MunicipioForm> listaMunicipio= new ArrayList<>();
    
    private @Getter @Setter boolean salarioConvenir = true;
    

    @PostConstruct
    public void init() {
        pais= loginBean.getPais();
        List<PaisForm> tmp= paisFacade.obtenerPaisesPorNombre(pais.toUpperCase());
        if(!tmp.isEmpty()){
            listaDepto= deptoFacade.obtenerDepartamentos(tmp.get(0).getIdpais());
        }
        oferta.setIdofertalaboral("0");
        if(loginBean.getUserLog()!=null){
            idempresa= loginBean.getUserLog().getIdentificador();
            listaOfertas = ofertaFacade.obtenerOfertasByIdEmpresa(pais,idempresa);
        }
    }
    
     
    public void cambiarCategoria(){
        if(!idcategoria.equals("")){
            listaPuestos= puestoFacade.obtenerPuestosByIdCategoria(idcategoria);
        }
        else{
            listaPuestos= new ArrayList<>();
        }
        validationBean.updateComponent("ofertasForm:puestoCmb");
    }
    
    public void cambiarCategoriaByFilter(){
        if(!idcategoriafilter.equals("")){
            listaOfertas= ofertaFacade.obtenerOfertasByCategoria(pais,idcategoriafilter, idempresa);
        }
        else{
            listaOfertas = ofertaFacade.obtenerOfertasByIdEmpresa(pais,idempresa);
            //listaOfertas= ofertaFacade.obtenerOfertas();
        }
        setChequeado(false);
        listaOfertasEliminar.clear();
        validationBean.updateComponent("ofertasForm:ofertaTbl");
        setEstadofilter("");
        validationBean.updateComponent("ofertasForm:estadoCmb");
    }
    
    public void cambiarEstadoByFilter(){
        if(!estadofilter.equals("")){
            listaOfertas= ofertaFacade.obtenerOfertasByIdEstado(pais,estadofilter,idempresa);
        }
        else{
            //listaOfertas= ofertaFacade.obtenerOfertas();
            listaOfertas = ofertaFacade.obtenerOfertasByIdEmpresa(pais,idempresa);
        }
        setChequeado(false);
        listaOfertasEliminar.clear();
        validationBean.updateComponent("ofertasForm:ofertaTbl");
        setIdcategoriafilter("");
        validationBean.updateComponent("ofertasForm:categoriaCmb");
    } 
    
    public void selectCheckbox(OfertaForm obj){
        if(chequeado){
            listaOfertasEliminar.add(obj);
        }
        else{
            if(listaOfertasEliminar.contains(obj))
                listaOfertasEliminar.remove(obj);
        }
    }
    
    public void cambiarDepto(){
        if(!iddepto.equals("")){
            listaMunicipio=muniFacade.obtenerMunicipios(iddepto);
        }
        else{
            listaMunicipio= new ArrayList<>();
        }
        validationBean.updateComponent("ofertasForm:muniCmb");
    }
    
    public void cambiarSalario(){ //no se usa guardado por referencia
      validationBean.updateComponent("ofertasForm:salarioAConvenir");
       // if (salarioConvenir) {
            //oferta.setSalariomaxofertalaboral("0.00");
            //oferta.setSalariominofertalaboral("0.00");
            //salarioConvenir = false;
            //System.out.println("si "+salarioConvenir + oferta.getSalariomaxofertalaboral() + oferta.getSalariominofertalaboral());
       // }else{
            //salarioConvenir = true;
            /*oferta.setSalariomaxofertalaboral("");
            oferta.setSalariominofertalaboral("");
            validationBean.updateComponent("ofertasForm:salariominimo");
            validationBean.updateComponent("ofertasForm:salariomaximo");*/
            //System.out.println("no "+salarioConvenir+ oferta.getSalariomaxofertalaboral() + oferta.getSalariominofertalaboral());
       // }
    }
    
    
    public void actualizaOferta(){
        String flag="";
        
        if (salarioConvenir) {
            oferta.setSalariomaxofertalaboral("0.00");
            oferta.setSalariominofertalaboral("0.00");
        }
       
        if(validationBean.validarSeleccion(oferta.getIdjornadalaboral(), "warn", "titleOfertaEmpresa", "lblSelectRegJornada")
                &&
                validationBean.validarSeleccion(oferta.getIdtipocontrato(), "warn", "titleOfertaEmpresa", "lblSelectRegTipoContrato")
                &&
                validationBean.validarSeleccion(oferta.getIdpuestotrabajo(), "warn", "titleOfertaEmpresa", "lblPuestoSelectReq")
                &&
                validationBean.validarSeleccion(oferta.getIdciudad(), "warn", "titleOfertaEmpresa", "lblSelectRegMunicipio")
                &&
                validationBean.validarCampoVacio(oferta.getTituloofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqNombreOferta")
                
                &&
                
                validationBean.validarLongitudCampo(oferta.getTituloofertalaboral(), 10, 40, "warn", "titleOfertaEmpresa", "lblLongitudOfertaTitulo")
                //&&
                //validationBean.validarFecha(oferta.getFechacontratacionofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaFechaContra")               
                &&
                validationBean.validarFecha(oferta.getFechacontratacionofertalaboral(),"warn", "titleOfertaEmpresa", "lblReqOfertaFechaContra")
                //validationBean.validarFecha(validationBean.formatearFechaGuion(oferta.getFechacontratacionofertalaboral()), "warn", "titleOfertaEmpresa", "lblReqOfertaFechaContra") 
                &&
                validationBean.validarCampoVacio(oferta.getCantidadvacante(), "warn", "titleOfertaEmpresa", "lblReqOfertaCantidadVacante")
                &&
                validationBean.validarCampoVacio(oferta.getSalariominofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaSalarioMinimo")
                &&
                validationBean.validarCampoVacio(oferta.getSalariomaxofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaSalarioMaximo")
                &&
                validationBean.validarCampoVacio(oferta.getDescripcionofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaDescripcion")
                &&
                validationBean.validarCampoVacio(oferta.getRequerimientosofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaRequerimiento")
                &&
                validationBean.validarCampoVacio(oferta.getHabilidadesofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaHabilidades")
                &&
                validationBean.validarCampoVacio(oferta.getConocimientoofertalaboral(), "warn", "titleOfertaEmpresa", "lblReqOfertaConocimientos")
                &&
                validationBean.validarLongitudCampo(oferta.getDescripcionofertalaboral(), 60, 255, "warn", "titleOfertaEmpresa", "lblLongitudOfertaDescripcion")
                &&
                validationBean.validarLongitudCampo(oferta.getRequerimientosofertalaboral(), 60, 255, "warn", "titleOfertaEmpresa", "lblLongitudOfertaRequerimiento")
                &&
                validationBean.validarLongitudCampo(oferta.getHabilidadesofertalaboral(), 60, 255, "warn", "titleOfertaEmpresa", "lblLongitudOfertaHabilidades")
                &&
                validationBean.validarLongitudCampo(oferta.getConocimientoofertalaboral(), 60, 255, "warn", "titleOfertaEmpresa", "lblLongitudOfertaConocimientos")
                ){
               if(oferta.getIdofertalaboral()==null || oferta.getIdofertalaboral().equals("0")){
                   oferta.setIdempresa_tbl(idempresa);
                   //oferta.setFechacontratacionofertalaboral(validationBean.formatearFechaGuion(oferta.getFechacontratacionofertalaboral()));
                    flag= ofertaFacade.actualizarOferta(oferta, "A"); 
                    if(flag.equals("0"))
                        validationBean.lanzarMensaje("info", "titleOfertaEmpresa", "lblGuardarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleOfertaEmpresa", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleOfertaEmpresa", "lblErrorTransact");
                   }
                   else if(flag.equals("-3")){
                       validationBean.lanzarMensaje("error", "titleOfertaEmpresa", "lblErrorLimit");
                   }
                }
               else{
                    flag= ofertaFacade.actualizarOferta(oferta, "U"); 
                    if(flag.equals("0"))
                        validationBean.lanzarMensaje("info", "titleOfertaEmpresa", "lblEditarSuccess");
                   else if(flag.equals("-1")){
                       validationBean.lanzarMensaje("warning", "titleOfertaEmpresa", "lblExistReg");
                   }
                   else if(flag.equals("-2")){
                       validationBean.lanzarMensaje("error", "titleOfertaEmpresa", "lblErrorTransact");
                   }
               }
               
               limpiar();
               listaOfertas = ofertaFacade.obtenerOfertasByIdEmpresa(pais,idempresa);
               
        }
    }
    
    public void validarEliminar(){
        if(listaOfertasEliminar.isEmpty()){
            validationBean.lanzarMensaje("warn", "titleCategoriaEmpresa", "lblSelectRegOfertas");
        }
        else{
            validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
            setChequeado(false);
        }
        
    }
    
    public void cerrarDialogo(){
        limpiar();
        setChequeado(false);
        listaOfertasEliminar= new ArrayList<>();
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void eliminar(){
        for(OfertaForm ofer: listaOfertasEliminar){
            String flag= ofertaFacade.actualizarOferta(ofer, "D");
            if(flag.equals("0"))
                validationBean.lanzarMensaje("info", "titleCategoriaEmpresa", "lblEliminarSuccess");

            else if(flag.equals("-1") || flag.equals("-2"))
                validationBean.lanzarMensaje("error", "titleCategoriaEmpresa", "lblEliminarError");            
        }
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        listaOfertasEliminar= new ArrayList<>();
        listaOfertas= ofertaFacade.obtenerOfertasByIdEmpresa(pais,idempresa);
        limpiar();
        
    }

    public void onSelect(OfertaForm obj) {
        oferta=obj;
        System.out.println("original: " + oferta.getFechacontratacionofertalaboral());
        oferta.setFechacontratacionofertalaboral(validationBean.formatearFecha(oferta.getFechacontratacionofertalaboral()));
        System.out.println("despues: " + oferta.getFechacontratacionofertalaboral());
        idcategoria= puestoFacade.obtenerPuestosByIdPuesto(oferta.getIdpuestotrabajo()).get(0).getIdcategoria();
        iddepto= deptoFacade.obtenerDepartamentoByIdCiudad(oferta.getIdciudad()).get(0).getIddepartamento();
        cambiarCategoria();
        cambiarDepto();
        if (!oferta.getSalariomaxofertalaboral().equals("0.0") && !oferta.getSalariominofertalaboral().equals("0.0")) {
            
            salarioConvenir = false;
            validationBean.ejecutarJavascript("$('.convenir').css('display','block');");
        }else{
        
            salarioConvenir = true;
            validationBean.ejecutarJavascript("$('.convenir').css('display','none');");
        }
        /*
        cemp.setIdcategoria(obj.getIdcategoria());
        cemp.setNombrecategoria(obj.getNombrecategoria());
        */
    }

    public void unSelect() {
        limpiar();
    }

    public void limpiar() {
        setIdcategoria("");
        setIddepto("");
        oferta= new OfertaForm();
        oferta.setIdofertalaboral("0");
        listaPuestos= new ArrayList<>();
        listaMunicipio= new ArrayList<>();
        salarioConvenir = true;
        validationBean.ejecutarJavascript("$('.convenir').css('display','none');");
        /*
       cemp.setIdcategoria("0");
       cemp.setNombrecategoria("");
       */
    }
    

}
