/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.EmpresaFacade;
import com.hayteguacho.facade.MunicipioFacade;
import com.hayteguacho.facade.OfertaFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */

@ManagedBean (name = "mostrarController")
@SessionScoped
public class MostrarOfertaController {
    
    @EJB
    OfertaFacade ofertaFacade;
    
    @EJB
    
    EmpresaFacade empresaFacade;
    
    @EJB
    
    DepartamentoFacade deptoFacade;
    
    @EJB
    
    MunicipioFacade muniFacade;
    
    @EJB
    @Getter @Setter
    ValidationBean validationBean;
    
    private @Getter @Setter List<OfertaForm> listaOferta= new ArrayList<>();
    
    private @Getter @Setter int count=5;
    
    private @Getter @Setter int ini=0;
    
    private @Getter @Setter int numreg;
    
    private @Getter @Setter OfertaForm ofertaForm;
    
    private @Getter @Setter EmpresaForm empresaForm;
    
    private @Getter @Setter String deptoMun;
    
    private @Getter @Setter String idcategoriafilter;
    
    private @Getter @Setter String iddeptofilter;
    
    private @Getter @Setter String[] cssClases= new String[6];
    
    //private @Getter @Setter int contador=0;
    
    @PostConstruct
    public void init(){
        listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
        numreg= ofertaFacade.count();
        cssClases[0]="red result-text";
        cssClases[1]="teal result-text";
        cssClases[2]="green result-text";
        cssClases[3]="blue result-text";
        cssClases[4]="teal result-text";
        cssClases[5]="green result-text";
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
            listaOferta= ofertaFacade.obtenerOfertasByCategoriaRange(idcategoriafilter, ini, count);
        }
        else{
            listaOferta= listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
        }
        setIddeptofilter("");
    }
    
    public void cambiarDeptoByFilter(){
        if(!iddeptofilter.equals("")){
            listaOferta= ofertaFacade.obtenerOfertasByDepartamentoRange(iddeptofilter, ini, count);
        }
        else{
            listaOferta= listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
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
                   listaOferta= ofertaFacade.obtenerOfertasByRange(ini, numreg);
                }
                else{
                    listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
                }
            }
    }
    
    public void atras(){
        if(listaOferta.size()>0)
            if(count>6 && ini>1){
                count= count-6;
                ini= ini-6;
                listaOferta= ofertaFacade.obtenerOfertasByRange(ini, count);
            }
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
}
