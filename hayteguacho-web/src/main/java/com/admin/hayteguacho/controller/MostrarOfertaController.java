/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.AplicarOfertaForm;
import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.admin.hayteguacho.form.TotalCategoriasForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.EmpresaFacade;
import com.hayteguacho.facade.MunicipioFacade;
import com.hayteguacho.facade.OfertaFacade;
import com.hayteguacho.facade.TotalFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */

@ManagedBean (name = "mostrarController")
@SessionScoped
public class MostrarOfertaController {
    @Inject
    LoginController loginBean;
    
    @EJB
    OfertaFacade ofertaFacade;
    
    @EJB
    
    EmpresaFacade empresaFacade;
    
    @EJB
    
    DepartamentoFacade deptoFacade;
    
    @EJB
    
    MunicipioFacade muniFacade;
    
    @EJB
    TotalFacade totalFacade;
    
    private @Getter @Setter String pretension="0";
    private @Getter @Setter boolean isTrabajando;
    
    @EJB
    @Getter @Setter
    ValidationBean validationBean;
    
    private @Getter @Setter List<OfertaForm> listaOferta= new ArrayList<>();
    
    private @Getter @Setter List<TotalCategoriasForm> listaTotalCategoria= new ArrayList<>();
    private @Getter @Setter List<TotalCategoriasForm> listaTotalCurriculum= new ArrayList<>();
    
    private @Getter @Setter int count=5;
    
    private @Getter @Setter int ini=0;
    
    private @Getter @Setter int numreg;
    
    private @Getter @Setter OfertaForm ofertaForm;
    
    private @Getter @Setter EmpresaForm empresaForm;
    
    private @Getter @Setter String deptoMun;
    
    private @Getter @Setter String idcategoriafilter;
    
    private @Getter @Setter String iddeptofilter;
    
    private @Getter @Setter String[] cssClases= new String[6];
    
    private @Setter boolean puedeAplicar;
    //private @Getter @Setter int contador=0;
    
    @PostConstruct
    public void init(){
        listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
        numreg= ofertaFacade.contarOfertas();
        listaTotalCategoria= totalFacade.totalCategorias();
        listaTotalCurriculum= totalFacade.totalCategoriasCurriculum();
        /*
        cssClases[0]="red result-text";
        cssClases[1]="teal result-text";
        cssClases[2]="green result-text";
        cssClases[3]="blue result-text";
        cssClases[4]="teal result-text";
        cssClases[5]="green result-text";
        */
        
    }
    
    public String cadenaConEnter(String cad, int top){
        String tmp="";
        for(int i=0; i< cad.length();i++){
            if(i%top==0){
                tmp= tmp+"</br>"+String.valueOf(cad.charAt(i));
            }
            else{
                if(i==57){
                  tmp= tmp+"...";
                  return tmp;
                }
                tmp= tmp+String.valueOf(cad.charAt(i));
            }
        }
        return tmp;
    }
    
    
    public String cadenaConEnterSinTop(String cad, int top){
        String tmp="";
        for(int i=0; i< cad.length();i++){
            if(i%top==0){
                tmp= tmp+"</br>"+String.valueOf(cad.charAt(i));
            }
            else{
                tmp= tmp+String.valueOf(cad.charAt(i));
            }
        }
        return tmp;
    }
    
    public void cambiarCategoriaByFilter(){
        if(!idcategoriafilter.equals("")){
            count=5;
            ini=0;
            listaOferta= ofertaFacade.obtenerOfertasByCategoriaRange(idcategoriafilter, ini, count);
            numreg= ofertaFacade.contarOfertasByCategoriaRange(idcategoriafilter);
        }
        else{
            numreg= ofertaFacade.contarOfertas();
            count=5;
            ini=0;
            listaOferta= listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
        }
        setIddeptofilter("");
    }
    
    public void cambiarCategoriaByFilterHome(String categoria){
        idcategoriafilter= categoria;
        if(!idcategoriafilter.equals("")){
            count=5;
            ini=0;
            listaOferta= ofertaFacade.obtenerOfertasByCategoriaRange(idcategoriafilter, ini, count);
            numreg= ofertaFacade.contarOfertasByCategoriaRange(idcategoriafilter);
        }
        else{
            numreg= ofertaFacade.contarOfertas();
            count=5;
            ini=0;
            listaOferta= listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
        }
        setIddeptofilter("");
        loginBean.redireccionar("/mostrarOfertas.xhtml?opcion=0");
    }
    
    public void cambiarDeptoByFilter(){
        if(!iddeptofilter.equals("")){
            count=5;
            ini=0;
            listaOferta= ofertaFacade.obtenerOfertasByDepartamentoRange(iddeptofilter, ini, count);
            numreg= ofertaFacade.contarOfertasByDeptoRange(iddeptofilter);
        }
        else{
            numreg= ofertaFacade.contarOfertas();
            listaOferta= listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
            count=5;
            ini=0;
        }
        setIdcategoriafilter("");
    }
    
    public EmpresaForm obtenerEmpresa(String idEmpresa){
        return empresaFacade.obtenerEmpresa(idEmpresa).get(0);
    }
    
    public void siguiente(){
        if(listaOferta.size()>0)
            if(count<= numreg){
                count= count+6;
                ini= ini+6;
                if(count>numreg){
                    if(!getIdcategoriafilter().equals("")){
                        listaOferta= ofertaFacade.obtenerOfertasByCategoriaRange(idcategoriafilter, ini, numreg);
                    }
                    else if(!getIddeptofilter().equals("")){
                        listaOferta= ofertaFacade.obtenerOfertasByDepartamentoRange(iddeptofilter, ini, numreg);
                    } 
                    else{
                      listaOferta= ofertaFacade.obtenerOfertasByRange(ini, numreg);  
                    }
                   //listaOferta= ofertaFacade.obtenerOfertasByRange(ini, numreg);
                }
                else{
                    listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
                }
            }
    }
    
    public void aplicarOferta(){
        AplicarOfertaForm ao= new AplicarOfertaForm();
        String flag="";
        ao.setIdaplica("0");
        ao.setIdcandidato(loginBean.getUserLog().getIdentificador());
        ao.setIdoferta(ofertaForm.getIdofertalaboral());
        ao.setPretension(getPretension());
        if(isTrabajando())
            ao.setTrabajando("S");
        else
            ao.setTrabajando("N");
        flag=ofertaFacade.aplicarOferta(ao, "A");
        switch(flag){
            case "0":
                
                validationBean.lanzarMensaje("info", "titleAplicaOferta", "lblAplicaExito");
                break;
            case "-1":
                validationBean.lanzarMensaje("info", "titleAplicaOferta", "lblAplicaExist");
                break;
            case "-2":
                validationBean.lanzarMensaje("info", "titleAplicaOferta", "lblAplicaError");
                break;
        }
        validationBean.ejecutarJavascript("$('.aplicarOferta').modal('hide'); ");
        pretension="0";
        isTrabajando=false;
        ofertaForm= new OfertaForm();
        
    }
    
    public void atras(){
        if(listaOferta.size()>0)
            if(count>6 && ini>1){
                count= count-6;
                ini= ini-6;
                    if(!getIdcategoriafilter().equals("")){
                        listaOferta= ofertaFacade.obtenerOfertasByCategoriaRange(idcategoriafilter, ini, count);
                    }
                    else if(!getIddeptofilter().equals("")){
                        listaOferta= ofertaFacade.obtenerOfertasByDepartamentoRange(iddeptofilter, ini, count);
                    } 
                    else{
                      listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);  
                    }
                //listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
            }
    }
    
    public boolean getPuedeAplicar(){
       return loginBean.isLoggedIn() && loginBean.getUserLog()!=null 
               && loginBean.getUserLog().getIdentificador()!=null && loginBean.getUserLog().getTipo().equals("C");
    }
    
    /*
    public void aumentar(){
        contador++;
        if(contador>6){
            contador=6;
        }
        System.out.println("El valor del contador es:" + contador);
    }
    
    public void atrasar(){
        contador--;
        if(contador<0){
            contador=0;
        }
        System.out.println("El valor del contador es:" + contador);
    }
    */
    
    
    public void cerrarDialogo(){
        //limpiar();
        ofertaForm= new OfertaForm();
        validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public void seleccionar(OfertaForm oform){
            ofertaForm= oform;
            empresaForm= obtenerEmpresa(oform.getIdempresa_tbl());
            deptoMun= deptoFacade.obtenerDepartamentoByIdCiudad(oform.getIdciudad()).get(0).getNombredepartamento();
            deptoMun= deptoMun +"  ,  " +muniFacade.obtenerMunicipio(oform.getIdciudad()).get(0).getNombreciudad();
            validationBean.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('destroy');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('index');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('jump',"+contador+");");
           // validationBean.ejecutarJavascript("myFlipster.flipster('destroy'); myFlipster.flipster(); myFlipster.flipster('jump',"+contador+");");
    }
    
    public void seleccionarAplicacion(OfertaForm oform){
            ofertaForm= oform;
            validationBean.ejecutarJavascript("$('.aplicarOferta').modal('show');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('destroy');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('index');");
            //validationBean.ejecutarJavascript("$('#coverflow').flipster('jump',"+contador+");");
           // validationBean.ejecutarJavascript("myFlipster.flipster('destroy'); myFlipster.flipster(); myFlipster.flipster('jump',"+contador+");");
    }
    
    public boolean deshabilitarSiguiente(){
        if(numreg <= 6){
            return true;
        }
        else if(count >= numreg){
            return true;
        }
        else{
          return false;  
        }
    }
}
