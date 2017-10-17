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
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "membresiaController")
public class MembresiaController {
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
    private @Getter @Setter MembresiaxEmpresaForm mef = new MembresiaxEmpresaForm();
    private @Getter @Setter List<MembresiaForm> listaMembresias = new ArrayList<>();
    private @Getter @Setter MembresiaForm membresia = new MembresiaForm();
    private @Getter @Setter List<TipoPeriodoMembresiaForm> listaTipoPeriodoMembresia = new ArrayList<>();
    private @Getter @Setter String opentag="<div data-carousel-3d>";
    private @Getter @Setter String closetag="</div>";
    private @Getter @Setter String periodo="";
    
    @Inject
    private LoginController login;
    
    
    @PostConstruct
    public void init(){
    membresia.setIdmembresia("0");
    listaMembresias = membresiafacade.obtenerMembresiasOrden();
    listaTipoPeriodoMembresia = tpmfacade.obtenerTiposPeriodosMembresia();
    }
    
    public void actualizarMembresia(){
    String flag = "";
        if (vb.validarCampoVacio(membresia.getTitulomembresia().replace(" ", ""), "warn", "titleMembresia", "lblNombreReqMembresia")
            && vb.validarSoloLetras(membresia.getTitulomembresia().replace(" ", ""), "warn", "titleMembresia", "lblSoloLetras")
            && vb.validarLongitudCampo(membresia.getTitulomembresia().replace(" ", ""), 4, 25, "warn", "titleMembresia","lblLongitudNombreMembresia")    
            && vb.validarCampoVacio(membresia.getDescripcionmembresia().replace(" ", ""), "warn","titleMembresia" , "lblDescReqMembresia")
            && vb.validarLongitudCampo(membresia.getDescripcionmembresia().replace(" ", ""), 4, 500, "warn","titleMembresia" , "lblLongitudDescMembresia")
            && vb.validarCampoVacio(membresia.getVentajasdemembresia().replace(" ", ""), "warn", "titleMembresia", "lblVentajasReqMembresia")
            && vb.validarLongitudCampo(membresia.getVentajasdemembresia().replace(" ", ""), 4, 500, "warn","titleMembresia" , "lblVentajasReqMembresia")
            && vb.validarCampoVacio(membresia.getPreciomembresia().replace(" ", ""), "warn", "titleMembresia", "lblPrecioReqMembresia")
            && vb.validarSoloNumerosConPunto(membresia.getPreciomembresia().replace(" ", ""), "warn", "titleMembresia", "lblSoloNumerosPrecio")
            && vb.validarCampoVacio(membresia.getPrecioxoferta().replace(" ", ""), "warn", "titleMembresia", "lblPrecioOfertaReqMembresia")
            && vb.validarSoloNumerosConPunto(membresia.getPrecioxoferta().replace(" ", ""), "warn", "titleMembresia", "lblSoloNumerosPrecio")
            && vb.validarCampoVacio(membresia.getCantidadoferta().replace(" ", ""), "warn", "titleMembresia","lblCantidadOfertaReqMembresia")
            && vb.validarCampoVacio(membresia.getCantidaduser(), "warn", "titleMembresia", "lblReqCantidadUserMembresia")    ) {
            
            if (membresia.getIdmembresia() == null || membresia.getIdmembresia().equals("0")) {
                flag = membresiafacade.actualizarMembresia(membresia, "A");
                if (flag.equals("0")) {
                    vb.lanzarMensaje("info",  "titleMembresia", "lblGuardarSuccess");
                }
            }else{
             flag = membresiafacade.actualizarMembresia(membresia, "U");
                if (flag.equals("0")) {
                    vb.lanzarMensaje("info","titleMembresia", "lblEditarSuccess");
                }
            }
            limpiar();
            listaMembresias = membresiafacade.obtenerMembresias();
        }
    }
    
    public void limpiar(){
    membresia =  new MembresiaForm();
    membresia.setIdmembresia("0");
    }
    
    public void onSelect(int index){
        MembresiaForm mf = listaMembresias.get(index);
    membresia = mf;
        System.out.println("com.admin.hayteguacho.controller.MembresiaController.onSelect()");
    }
    
    public void deSelect(){
    limpiar();
        System.out.println("com.admin.hayteguacho.controller.MembresiaController.deSelect()");
    }
    public void validarEliminar(){
        if (membresia.getIdmembresia() == null || membresia.getIdmembresia().equals("0")) {
            vb.lanzarMensaje("warn", "titleMembresia", "lblSelectRegMembresia");
        }else{
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
        }
    }
    
    public void eliminar(){
    String flag = membresiafacade.actualizarMembresia(membresia, "D");
        if (flag.equals("0")) {
            vb.lanzarMensaje("info", "titleMembresia", "lblEliminarSuccess");
            limpiar();
            listaMembresias = membresiafacade.obtenerMembresias();
        }else if (flag.equals("-1")||flag.equals("-2")) {
            vb.lanzarMensaje("error", "titleMembresia", "lblEliminarError");
       // vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
        }
    }
    
    public void cerrarDialogo(){
        limpiar();
   // vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    limpiarVista();
    }
    
    
    
    public String getNombreTipoPeriodoMembresia(MembresiaForm mf){
    return tpmfacade.getNombreTipoPeriodoMembresia(mf);
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
  String periodo = getNombreTipoPeriodoMembresia(membresia);
  int mes = 0;
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
      }else{
      System.out.println("fail!");
      }
  limpiarVista();
  }
    
  public void limpiarVista(){
  mef = new MembresiaxEmpresaForm();
  }
  
  public  String parse(double num) {
    if((int) num == num) return Integer.toString((int) num);
    return String.valueOf(num); 
}
   
}
