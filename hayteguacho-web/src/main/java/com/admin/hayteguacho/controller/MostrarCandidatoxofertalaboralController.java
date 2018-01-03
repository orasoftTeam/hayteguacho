
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.form.CandidatoxofertaForm;
import com.admin.hayteguacho.form.MembresiaForm;
import com.admin.hayteguacho.form.MembresiaxEmpresaForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CandidatoFacade;
import com.hayteguacho.facade.CandidatoxofertaMostrarFacade;
import com.hayteguacho.facade.MembresiaFacade;
import com.hayteguacho.facade.MembresiaxempresaFacade;
import com.hayteguacho.facade.OfertaFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "mostrarCandxOfertaController")
public class MostrarCandidatoxofertalaboralController {

    @EJB
    CandidatoFacade candidatoFacade;

    @EJB
    CandidatoxofertaMostrarFacade cxof;

    @EJB
    MembresiaFacade memf;

    @EJB
    MembresiaxempresaFacade mxef;

    @EJB
    OfertaFacade ofertaFacade;
    @EJB
    ValidationBean vb;

    private @Getter
    @Setter
    List<CandidatoForm> listaCandidatos = new ArrayList<>();
    private @Getter
    @Setter
    List<CandidatoxofertaForm> listaCandidatoxoferta = new ArrayList<>();
    private @Getter
    @Setter
    List<OfertaForm> listaOferta = new ArrayList<>();
    private @Setter
    String titulo = "";
    private @Getter
    @Setter
    CandidatoxofertaForm selectedCxoferta
            = new CandidatoxofertaForm();
    private @Getter
    @Setter
    MembresiaForm membresia;

    private @Getter
    @Setter
    OfertaForm selectedOferta
            = new OfertaForm();
    private @Getter
    @Setter
    CandidatoForm selectedCandidato
            = new CandidatoForm();

    private @Setter
    @Getter
    boolean mostrarGuardar;

    private @Getter
    @Setter
    boolean pdfshow = false;
    
    private @Getter @Setter String wordRoot = "";

    @Inject
    LoginController login;

    @PostConstruct
    public void init() {
        membresia = getMembresiaActual();
        mostrarGuardar = mostrarSave();
        listaOferta = ofertaFacade.obtenerOfertas(login.getPais(), login.getUserLog().getIdentificador());
        System.out.println("com.admin.hayteguacho.controller.MostrarCandidatoxofertalaboralController.init()");
    }

    public boolean mostrarSave() {
        boolean flag = false;
        if (membresia.getTitulomembresia().toLowerCase().equals("gold")) {
            flag = true;
        }

        return flag;
    }

    public void dialogo(
            CandidatoForm cf,
            CandidatoxofertaForm cxoferta) {
        selectedCandidato = cf;
        //pdf =  cf.getArchivocurriculum();
        String res = "";
        pdfshow = true;

        if (cxoferta.getEstadocandidatoxofertalaboral().equals("PO")) {
            res = cxof.actualizarEstado(cxoferta.getIdcandidatoxofertalaboral(), "CV", "0", "0");
            vb.lanzarMensaje("info", "lblVisorOfertas", "lblCVCxoferta");
            if (res.equals("0")) {

                enviarEmail(cf.getCorreocandidato(),
                        vb.getMsgBundle("lblAsuntoCV") + ' ' + fullName(cf),
                        vb.getMsgBundle("lblMensajeCV1") + ' '
                        + selectedOferta.getTituloofertalaboral()
                        + ' ' + vb.getMsgBundle("lblMensajeCV2"));
            }
        }
        if (res.equals("0") || (!cxoferta.getEstadocandidatoxofertalaboral().equals("PO"))) {

            if (cf.getArchivocurriculum().contains(".docx")) {
                System.out.println("es word");
                cargarCandidatosxoferta(selectedOferta);
                vb.updateComponent("CxofertaForm:panelCxoferta");
                wordRoot = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + selectedCandidato.getArchivocurriculum();
                vb.updateComponent("CxofertaForm:archivo");
                //vb.redirecionar(selectedCandidato.getArchivocurriculum());
                
            } else {
                vb.updateComponent("CxofertaForm:amodal");
                vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
                cargarCandidatosxoferta(selectedOferta);
                vb.updateComponent("CxofertaForm:panelCxoferta");
            }

        }
        limpiar();
    }

    public void cargarCandidatosxoferta(OfertaForm oferta) {
        selectedOferta = oferta;
        boolean esGratis = membresia.getTitulomembresia().toLowerCase().equals("free");
        listaCandidatoxoferta = cxof.obtenerCandidatosxoferta(oferta.getIdofertalaboral(), esGratis);
        System.out.println(oferta.getTituloofertalaboral());
        vb.updateComponent("CxofertaForm:panelCxoferta");

    }

    public CandidatoForm obtenerCandidato(String idcand) {
        return candidatoFacade.obtenerCand(idcand);
    }

    public String getSituation(String situation) {
        String situ = "";
        if (situation.equals("S")) {
            situ = vb.getMsgBundle("lblTrabaja");
        } else {
            situ = vb.getMsgBundle("lblDesempleado");
        }

        return situ;
    }

    public String getEstadoCxoferta(String estado) {
        String est = "";
        switch (estado) {
            case "PO":
                est = vb.getMsgBundle("lblPostulado");
                break;
            case "CV":
                est = vb.getMsgBundle("lblCVvisto");
                break;
            case "PR":
                est = vb.getMsgBundle("lblProceso");
                break;
            case "FI":
                est = vb.getMsgBundle("lblFinalizada");
                break;
            default:
                break;
        }

        return est;
    }
    
    public String getEstadoCxofertaIcono(String estado) {
        String est = "";
        switch (estado) {
            case "PO":
                est = "posted-item-icon-18551.png";
                break;
            case "CV":
                est = "Green_check.svg.png";
                break;
            case "PR":
                est = "load02-512.png";
                break;
            case "FI":
                est = "finish-flag.png";
                break;
            default:
                break;
        }

        return est;
    }

    public String fullName(CandidatoForm cand) {
        return cand.getNombrecandidato() + ' ' + cand.getApellidocandidato();
    }

    public void procesar() {
        String res = "";
        selectedCandidato = obtenerCandidato(selectedCxoferta.getIdcandidato());
        if (selectedCxoferta.getEstadocandidatoxofertalaboral().equals("CV")) {
            res = cxof.actualizarEstado(
                    selectedCxoferta.getIdcandidatoxofertalaboral(),
                    "PR",
                    "0", "0");
            if (res.equals("0")) {
                cargarCandidatosxoferta(selectedOferta);
                //vb.updateComponent("CxofertaForm:panelCxoferta");
                vb.lanzarMensaje("info", "lblVisorOfertas", "lblPRCxoferta");

                enviarEmail(selectedCandidato.getCorreocandidato(),
                        vb.getMsgBundle("lblAsuntoCV") + ' ' + fullName(selectedCandidato),
                        vb.getMsgBundle("lblMensajeCV1") + ' '
                        + selectedOferta.getTituloofertalaboral()
                        + ' ' + vb.getMsgBundle("lblMensajePR2"));
                System.out.println("lblPRCxoferta");
                limpiar();
            } else {
                vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaError");
                System.out.println("lblCxofertaError");
                limpiar();
            }

        } else {
            vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaPRno");
            System.out.println("lblCxofertaPRno");
            limpiar();
        }

    }

    public void finalizar() {
        String res = "";
        selectedCandidato = obtenerCandidato(selectedCxoferta.getIdcandidato());
        if (selectedCxoferta.getEstadocandidatoxofertalaboral().equals("PR")) {
            res = cxof.actualizarEstado(
                    selectedCxoferta.getIdcandidatoxofertalaboral(),
                    "FI",
                    "0", "0");
            if (res.equals("0")) {
                cargarCandidatosxoferta(selectedOferta);
                vb.updateComponent("CxofertaForm:panelCxoferta");
                vb.lanzarMensaje("info", "lblVisorOfertas", "lblFICxoferta");

                enviarEmail(selectedCandidato.getCorreocandidato(), vb.getMsgBundle("lblAsuntoCV") + ' ' + fullName(selectedCandidato),
                        vb.getMsgBundle("lblMensajeCV1") + ' '
                        + selectedOferta.getTituloofertalaboral()
                        + ' ' + vb.getMsgBundle("lblMensajeFI2"));
                System.out.println("lblFICxoferta");
                limpiar();
            } else {
                vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaError");
                System.out.println("lblCxofertaError");
                limpiar();
            }

        } else {
            vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaFIno");
            System.out.println("lblCxofertaFIno");
            limpiar();
        }

    }

    public void validarEliminar() {

        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
    }

    public void cerrarDialogo() {

        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");

    }

    public void eliminar() {
        String res = "";
        selectedCandidato = obtenerCandidato(selectedCxoferta.getIdcandidato());

        res = cxof.actualizarEstado(
                selectedCxoferta.getIdcandidatoxofertalaboral(),
                "EL",
                "0", "0");
        if (res.equals("0")) {
            cargarCandidatosxoferta(selectedOferta);
            vb.updateComponent("CxofertaForm:panelCxoferta");
            vb.lanzarMensaje("info", "lblVisorOfertas", "lblELCxoferta");

            enviarEmail(selectedCandidato.getCorreocandidato(),
                    vb.getMsgBundle("lblAsuntoCV") + ' ' + fullName(selectedCandidato),
                    vb.getMsgBundle("lblMensajeCV1") + ' '
                    + selectedOferta.getTituloofertalaboral()
                    + ' ' + vb.getMsgBundle("lblMensajeEL2"));

            System.out.println("lblELCxoferta");
            limpiar();
        } else {
            vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaError");
            System.out.println("lblCxofertaError");
            limpiar();
        }

    }

    public void enviarEmail(String email, String asunto, String mensaje) {
        cxof.enviarCorreo(email, asunto, mensaje);
    }

    public void guardarEnBase() {
        String res = "";

        if (cxof.verificarCandidatosPorEmpresa(login.getUserLog().getIdentificador(), selectedCxoferta.getIdcandidato())) {
            res = cxof.actualizarEstado(
                    "0",
                    "GR",
                    login.getUserLog().getIdentificador(), selectedCxoferta.getIdcandidato());
            if (res.equals("1")) {
                vb.lanzarMensaje("info", "lblVisorOfertas", "lblGRguardado");
                limpiar();
            } else {
                vb.lanzarMensaje("error", "lblVisorOfertas", "lblCxofertaError");
            }
        } else {
            vb.lanzarMensaje("error", "lblVisorOfertas", "lblGRrepetido");
        }
    }

    public void onSelect(int index) {
        CandidatoxofertaForm cxoferta = listaCandidatoxoferta.get(index);
        selectedCxoferta = cxoferta;
        selectedCandidato = obtenerCandidato(cxoferta.getIdcandidato());
        vb.updateComponent("CxofertaForm:btns");
    }

    public int obtenerInscritos(OfertaForm oferta) {
        return cxof.obtenerCandidatosxoferta(oferta.getIdofertalaboral(), false).size();
    }

    public String obtenerFecha(String dateStr) {
        //String dateStr = oferta.getFechavigenciaofertalaboral();
        DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        DateFormat writeFormat = new SimpleDateFormat("dd MMMMM yyyy");
        Date date = null;
        try {
            date = (Date) readFormat.parse(dateStr);
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.controller.MostrarCandidatoxofertalaboralController.obtenerFecha()");
            e.printStackTrace();
        }

        String formattedDate = "";
        if (date != null) {
            formattedDate = writeFormat.format(date);
        }
        return formattedDate;
    }

    public void limpiar() {
        selectedCxoferta = new CandidatoxofertaForm();

        vb.updateComponent("CxofertaForm:btns");
    }

    public MembresiaForm getMembresiaActual() {
        MembresiaxEmpresaForm mf = mxef.obtenerMembresia(login.getUserLog().getIdentificador());
        MembresiaForm membre = memf.entityToDto(memf.find(new BigDecimal(mf.getIdmembresia())), new MembresiaForm());
        return membre;
    }

    public String getTitulo() {

        try {
            titulo = vb.getMsgBundle("lblCxOfertasActuales") + "(" + listaOferta.size() + " " + vb.getMsgBundle("lblOfertas") + ")";
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.controller.MostrarCandidatoxofertalaboralController.obtenerTitulo()");
            e.printStackTrace();
        }
        return titulo;
    }
    
    public String obtenerEstado(OfertaForm oferta){
    String res = "";
    String bolita = "";
    String estado = "";
    
        if (oferta.getEstadoofertalaboral().toLowerCase().equals("a")) {
            estado = vb.getMsgBundle("lblOfertaActivo");
            bolita = "boton-verde.gif";
        }else{
           estado = vb.getMsgBundle("lblOfertaInactiva");
            bolita = "boton-rojo.gif";
        }
        
       res = estado;
    
    return res;
    }
    
    public String obtenerEstadoColor(OfertaForm oferta){
    String res = "";
    if (oferta.getEstadoofertalaboral().toLowerCase().equals("a")) {
    res = "boton-verde.gif";
    }else{
    res = "boton-rojo.gif";
    }
    return res;
    }
}
