/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.MembresiaForm;
import com.admin.hayteguacho.form.MembresiaxEmpresaForm;
import com.admin.hayteguacho.form.TipoPeriodoMembresiaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.MembresiaFacade;
import com.hayteguacho.facade.MembresiaxempresaFacade;
import com.hayteguacho.facade.TipoPeriodoMembresiaFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "mostrarMembresia")
public class MostrarMembresiaController {
    @EJB
    MembresiaFacade membresiafacade;
    @EJB
    ValidationBean vb;
    @EJB
    TipoPeriodoMembresiaFacade tpmfacade;
    @EJB
    MembresiaxempresaFacade mefacade;
    
    private @Getter @Setter boolean modalInfo = false;
    private @Getter @Setter boolean modalContratar = false;
    private @Getter @Setter boolean modalConfirmar = false;
    private @Getter @Setter String opentag="<div data-carousel-3d>";
    private @Getter @Setter String closetag="</div>";
    private @Getter @Setter List<MembresiaForm> listaMembresias = new ArrayList<>();
    private @Getter @Setter List<MembresiaForm> listaMembresiasPorPeriodo = new ArrayList<>();
    private @Getter @Setter MembresiaForm membresia = new MembresiaForm();
    private @Getter @Setter List<TipoPeriodoMembresiaForm> listaTipoPeriodoMembresia = new ArrayList<>();
    private @Getter @Setter String periodo="";
    private @Getter @Setter MembresiaxEmpresaForm mef = new MembresiaxEmpresaForm();
    
    @Inject
    private LoginController login;
    
     @PostConstruct
    public void init(){
        
        listaMembresias = membresiafacade.obtenerMembresiasOrden();
        listaTipoPeriodoMembresia = tpmfacade.obtenerTiposPeriodosMembresia();
    }
    
    public String getNombreTipoPeriodoMembresia(MembresiaForm mf){
    return tpmfacade.getNombreTipoPeriodoMembresia(mf);
    }
    
  public String colorMem(String title){
  String color = "black";
  switch(title.toLowerCase())
  {
      case "gold":
          color = "#b29500";
          break;
      case "platinum":
          color = "#697e86";
          break;
      case "classic":
          color = "#009ea7";
          break;
      case "free":
          color = "#64aa07";
          break;    
  }
  
  return color;
  }
  
  public String colorMemDarker(String title){
  String color = "black";
  switch(title.toLowerCase())
  {
      case "gold":
          color = "#967E05";
          break;
      case "platinum":
          color = "#5C6C73";
          break;
      case "classic":
          color = "#037A81";
          break;
      case "free":
          color = "#4C8006";
          break;    
  }
  
  return color;
  }
  
   public  String parse(double num) {
    if((int) num == num) return Integer.toString((int) num);
    return String.valueOf(num); 
}
   
    public void mostrarInfo(MembresiaForm m){
    this.membresia = m;
    this.modalInfo = true;
    this.modalContratar = false;
    this.modalConfirmar = false;
    this.periodo = getNombreTipoPeriodoMembresia(m);
    vb.ejecutarJavascript("$('.modalPseudoClass').modal('show'); ");
    }
    
    public void mostrarCon(MembresiaForm m){
    this.membresia = m;
    this.modalInfo = false;
    this.modalContratar = true;
    this.modalConfirmar = false;
    this.periodo = getNombreTipoPeriodoMembresia(m);
    vb.ejecutarJavascript("$('.modalPseudoClass').modal('show'); ");
    }
    
  public void confirmar(){
  this.modalInfo = false;
    this.modalContratar = false;
    this.modalConfirmar = true;
    //vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    //vb.ejecutarJavascript("$('.modalPseudoClass').modal('show'); ");
  
  }
   
  public void contratar(){
  Date fechainicio = new Date();
  Calendar c = Calendar.getInstance();
  c.setTime(fechainicio);
  //String periodo = getNombreTipoPeriodoMembresia(membresia);
  int mes = 0;
      try {
           if (periodo.toLowerCase().equals("mensual")) {
          mes = 1;
      }else if (periodo.toLowerCase().equals("trimestral")) {
       mes = 3;   
      }else if (periodo.toLowerCase().equals("semestral")) {
          mes = 6;
      }else if (periodo.toLowerCase().equals("anual")) {
          mes = 12;
      }
      c.add(Calendar.MONTH, mes);
      System.out.println(c.toString());
  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
  mef.setEstado("A");
      if (mef.getCantidadofertasextra().equals("")) {
          mef.setCantidadcontratada(membresia.getCantidadoferta());
          mef.setCantidadofertasextra("0");
      }else{
       mef.setCantidadcontratada(String.valueOf(Integer.parseInt(membresia.getCantidadoferta()) + Integer.parseInt(mef.getCantidadofertasextra())));
      }
  mef.setFechainicio(sdf.format(fechainicio));
  mef.setIdmembresia(membresia.getIdmembresia());
  mef.setIdempresa(login.getUserLog().getIdentificador()); //AQUI SE SETEARA EL ID DE LA EMPRESA LOGEADA
  mef.setPrecioxofertasextra(membresia.getPrecioxoferta());
  mef.setFechavencimiento(sdf.format(c.getTime()));
      System.out.println(mef);
      String flag = mefacade.actualizarMembresiaxEmpresa(mef);
      if (flag.equals("0") || flag.equals("-1")) {
          System.out.println("exito!");
          login.setMembresia(true);
          
          ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
          
          context.redirect(context.getRequestContextPath()+"/pages/oferta/mantenimientoOferta.xhtml");
          vb.lanzarMensaje("info", "lblMembresia", "lblMemConfirm");
      }else{
      System.out.println("fail!");
      }
      } catch (Exception e) {
          System.out.println("com.admin.hayteguacho.controller.MostrarMembresiaController.contratar()");
          e.printStackTrace();
      }
  limpiarVista();
  }
    
  public void limpiarVista(){
  mef = new MembresiaxEmpresaForm();
  }
   
   public List<MembresiaForm> obtenerMembresiasPorTitulo(String titulo){
  listaMembresiasPorPeriodo = membresiafacade.obtenerMembresiasPorTitulo(titulo);
  return listaMembresiasPorPeriodo;
  }
   
 
   public void cerrarDialogo(){
       // limpiar();
   // vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    limpiarVista();
    }
    
   
   public void cambiarPeriodo(int index,MembresiaForm per){
       listaMembresias.remove(index);
       listaMembresias.add(index, per);
       System.out.println("com.admin.hayteguacho.controller.MostrarMembresiaController.cambiarPeriodo()"  + getNombreTipoPeriodoMembresia(per));
       
       //vb.updateComponent("@form");
   }
}
