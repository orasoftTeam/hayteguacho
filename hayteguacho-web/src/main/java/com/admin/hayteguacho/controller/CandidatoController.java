/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CandidatoForm;
import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.form.PaisForm;
import com.admin.hayteguacho.form.PuestoTrabajoForm;
import com.admin.hayteguacho.form.UserForm;
import com.admin.hayteguacho.util.RandomString;
import com.admin.hayteguacho.util.ValidationBean;
import com.captcha.botdetect.web.jsf.JsfCaptcha;
import com.hayteguacho.facade.CandidatoFacade;
import com.hayteguacho.facade.CargoEmpresaFacade;
import com.hayteguacho.facade.PaisFacade;
import com.hayteguacho.facade.PuestoTrabajoFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author LAP
 */
@ViewScoped
@ManagedBean(name = "candidatoController")
public class CandidatoController {

    @EJB
    CandidatoFacade candidatoFacade;

    @EJB
    ValidationBean validationBean;

    @EJB
    PuestoTrabajoFacade puestoFacade;

    @EJB
    PaisFacade paisFacade;

    @Inject
    LoginController loginBean;
    String appPath = System.getProperty("user.dir");
    private final String destination = appPath + File.separator + "pdf\\";
    private final String destinationFoto = appPath + File.separator + "fotosc\\";
    private @Getter
    @Setter
    String nameFileFinal;
    private @Getter
    @Setter
    UploadedFile archivo;
    private @Getter
    @Setter
    UploadedFile archivoFoto;
    private @Getter
    @Setter
    InputStream archivoFotoTemp;
    private @Getter
    @Setter
    String imageConfirm; //imagen de confirmacion que se muestra cuando se sube el pdf o docx de curriculum
    private @Getter
    @Setter
    List<CandidatoForm> listaCandidatos = new ArrayList<>();
    private @Getter
    @Setter
    CandidatoForm candidato = new CandidatoForm();
    private @Getter
    @Setter
    String msgFile;//mensaje de confirmacion que se muestra cuando se sube el pdf o docx de curriculum
    private @Getter
    @Setter
    String msgFoto;//mensaje de confirmacion que se muestra cuando se sube la foto
    private @Getter
    @Setter
    List<PuestoTrabajoForm> listaPuestos = new ArrayList<>();
    private @Getter
    @Setter
    String idcategoria;
    private @Getter
    @Setter
    String reclave;
    private @Getter
    @Setter
    JsfCaptcha captcha;
    private @Getter
    @Setter
    String captchaCode;

    @PostConstruct
    public void init() {

        //String pais= Locale.getDefault().getDisplayCountry();
        String pais = loginBean.getPais();
        List<PaisForm> tmp = paisFacade.obtenerPaisesPorNombre(pais.toUpperCase());
        if (!tmp.isEmpty()) {
            candidato.setIdpaistbl(tmp.get(0).getIdpais());
        }
        candidato.setIdcandidato("0");
        UserForm usuario = loginBean.getUserLog();
        if (usuario != null && usuario.getTipo().equals("C")) {
            candidato = candidatoFacade.obtenerCand(usuario.getIdentificador());
            System.out.println("HEY AQUI!!!!foto: " + candidato.getFoto());
        }
    }

    public boolean validarCaptcha() {
        boolean isHuman = captcha.validate(captchaCode);
        return isHuman;
    }

    public void actualizarCandidato() {
        String flag = "";
        if (validationBean.validarCampoVacio(candidato.getCorreocandidato(), "warn", "titleCandidato", "lblEmailReqEmpresa")
                && validationBean.validarEmail(candidato.getCorreocandidato(), "warn", "titleCandidato", "lblEmailValido")
                && validationBean.validarCampoVacio(candidato.getContrasenacandidato(), "warn", "titleCandidato", "lblClaveReqEmpresa")
                && validationBean.validarLongitudCampo(candidato.getContrasenacandidato(), 5, 20, "warn", "titleCandidato", "lblLongitudClaveEmpresa")
                && validationBean.validarCampoVacio(candidato.getNombrecandidato(), "warn", "titleCandidato", "lblNomCandidatoReq")
                && validationBean.validarSoloLetras(candidato.getNombrecandidato().replace(" ", ""), "warn", "titleCandidato", "lblSoloLetrasNombre")
                && validationBean.validarLongitudCampo(candidato.getNombrecandidato(), 4, 30, "warn", "titleCandidato", "lblLongitudNomCandidato")
                && validationBean.validarCampoVacio(candidato.getApellidocandidato(), "warn", "titleCandidato", "lblApeCandidatoReq")
                && validationBean.validarSoloLetras(candidato.getApellidocandidato().replace(" ", ""), "warn", "titleCandidato", "lblSoloLetrasApellido")
                && validationBean.validarLongitudCampo(candidato.getApellidocandidato(), 4, 30, "warn", "titleCandidato", "lblLongitudApellidoCandidato")
                && validationBean.validarCampoVacio(candidato.getTelefono1candidato(), "warn", "titleCandidato", "lblTelReq")
                && validationBean.validarSeleccion(candidato.getGenerocandidato(), "warn", "titleCandidato", "lblGenSelectReq")
                && validationBean.validarSeleccion(candidato.getIdpuestotrabajotbl(), "warn", "titleCandidato", "lblPuestoSelectReq")
                && validationBean.validarSeleccion(archivo == null ? "" : archivo.getFileName(), "warn", "titleCandidato", "lblFileUploadReq")
                && validationBean.validarCampoVacio(captchaCode, "warn", "titleCandidato", "lblCaptchaReq")) {

            if (candidato.getContrasenacandidato().equals(reclave)) {
                if (candidato.getIdcandidato() == null || candidato.getIdcandidato().equals("0")) {
                    //candidato.setContrasenacandidato(validationBean.encriptar(candidato.getContrasenacandidato(), candidato.getCorreocandidato()));
                    if (validarCaptcha()) {
                        flag = candidatoFacade.actualizarCandidato(candidato, "A");
                        if (flag.equals("0")) {
                            loginBean.setUsuario(candidato.getCorreocandidato());
                            loginBean.setPassword(candidato.getContrasenacandidato());
                            loginBean.logear();

                            //validationBean.lanzarMensaje("info", "titleCandidato", "lblGuardarSuccess");
                            //limpiar();
                        } else if (flag.equals("-1")) {
                            validationBean.lanzarMensaje("warn", "titleCandidato", "lblExistReg");
                        } else {
                            validationBean.lanzarMensaje("error", "titleCandidato", "lblGuardarError");
                        }
                    } else {
                        validationBean.lanzarMensaje("error", "titleCandidato", "lblCaptchaError");
                    }

                }
                if (!candidato.getIdcandidato().equals("0") && !candidato.getIdcandidato().equals("")) {
                    validationBean.lanzarMensaje("warn", "titleCandidato", "lblExistReg");
                }
            } else {

                validationBean.lanzarMensaje("error", "titleCandidato", "lblRepContra");
            }
        }
    }

    public void actualizarCandidatoRegistro() {
        String flag = "";
        if (validationBean.validarCampoVacio(candidato.getCorreocandidato(), "warn", "titleCandidato", "lblEmailReqEmpresa")
                && validationBean.validarEmail(candidato.getCorreocandidato(), "warn", "titleCandidato", "lblEmailValido")
                && validationBean.validarCampoVacio(candidato.getContrasenacandidato(), "warn", "titleCandidato", "lblClaveReqEmpresa")
                && validationBean.validarLongitudCampo(candidato.getContrasenacandidato(), 5, 20, "warn", "titleCandidato", "lblLongitudClaveEmpresa")
                && validationBean.validarCampoVacio(candidato.getNombrecandidato(), "warn", "titleCandidato", "lblNomCandidatoReq")
                && validationBean.validarSoloLetras(candidato.getNombrecandidato().replace(" ", ""), "warn", "titleCandidato", "lblSoloLetras")
                && validationBean.validarLongitudCampo(candidato.getNombrecandidato(), 4, 30, "warn", "titleCandidato", "lblLongitudNomCandidato")
                && validationBean.validarCampoVacio(candidato.getApellidocandidato(), "warn", "titleCandidato", "lblApeCandidatoReq")
                && validationBean.validarSoloLetras(candidato.getApellidocandidato().replace(" ", ""), "warn", "titleCandidato", "lblSoloLetras")
                && validationBean.validarLongitudCampo(candidato.getApellidocandidato(), 4, 30, "warn", "titleCandidato", "lblLongitudApellidoCandidato")
                && validationBean.validarCampoVacio(candidato.getTelefono1candidato(), "warn", "titleCandidato", "lblTelReq")
                && validationBean.validarSeleccion(candidato.getGenerocandidato(), "warn", "titleCandidato", "lblGenSelectReq")
                && validationBean.validarSeleccion(candidato.getIdpuestotrabajotbl(), "warn", "titleCandidato", "lblPuestoSelectReq") /*
                &&  validationBean.validarSeleccion(archivo==null?"":archivo.getFileName(),"warn", "titleCandidato", "lblFileUploadReq")
                 */) {

            if (candidato.getIdcandidato() == null || !candidato.getIdcandidato().equals("0")) {
                //candidato.setContrasenacandidato(validationBean.encriptar(candidato.getContrasenacandidato(), candidato.getCorreocandidato()));
                flag = candidatoFacade.actualizarCandidato(candidato, "U");
                if (flag.equals("0")) {
                    validationBean.lanzarMensaje("info", "titleCandidato", "lblGuardarSuccess");
                    limpiar();
                } else if (flag.equals("-1")) {
                    validationBean.lanzarMensaje("warn", "titleCandidato", "lblExistReg");
                } else {
                    validationBean.lanzarMensaje("error", "titleCandidato", "lblGuardarError");
                }
            }
        }
    }

    public void cambiarCategoria() {
        System.out.println("entra");
        if (!idcategoria.equals("")) {
            listaPuestos = puestoFacade.obtenerPuestosByIdCategoria(idcategoria);
            System.out.println("com.admin.hayteguacho.controller.CandidatoController.cambiarCategoria()");
        } else {
            listaPuestos = new ArrayList<>();
        }
        validationBean.updateComponent("candidatoForm:cmbPuesto");
    }

    public void handleFileUpload(FileUploadEvent event) {

        try {
            if (archivo == null) {
                archivo = event.getFile();
                validationBean.copyFile(event.getFile().getFileName(), destination, event.getFile().getInputstream());
                msgFile = validationBean.getMsgBundle("lblFileSuccess");
                validationBean.updateComponent("candidatoForm:msgFile");
                String name = archivo.getContentType();
                String[] pieces = name.split(".");
                RandomString rs = new RandomString(8, ThreadLocalRandom.current());
                String random = rs.nextString();
                candidato.setArchivocurriculum("/pdf/" + event.getFile().getFileName());
                imageConfirm = "resources/images/iconoCV.jpg";
                //validationBean.updateComponent("candidatoForm:cvMostrar");
                validationBean.updateComponent("candidatoForm:linkM");
            } else if (validationBean.deleteFile(destination + archivo.getFileName())) {
                archivo = event.getFile();
                validationBean.copyFile(event.getFile().getFileName(), destination, event.getFile().getInputstream());
                msgFile = validationBean.getMsgBundle("lblFileSuccess");
                validationBean.updateComponent("candidatoForm:msgFile");
                candidato.setArchivocurriculum("/pdf/" + event.getFile().getFileName());
                imageConfirm = "resources/images/iconoCV.jpg";
                //validationBean.updateComponent("candidatoForm:cvMostrar");

                validationBean.updateComponent("candidatoForm:linkM");
            }
        } catch (IOException e) {
            msgFile = validationBean.getMsgBundle("lblFileUploadError");
            validationBean.updateComponent("candidatoForm:msgFile");
            if (archivo != null) {
                if (validationBean.deleteFile("/pdf/" + archivo.getFileName())) {
                    archivo = null;
                }
            }
            candidato.setArchivocurriculum("");
            e.printStackTrace();
        }

    }

    /*

    public void actualizarPais() {
        
        String flag=""; 
        if(validationBean.validarCampoVacio(cemp.getNombrecargoempresa().replace(" ", ""), "warn", "titleCargoEmpresa", "lblNombreReqCargoEmpresa")
                && validationBean.validarSoloLetras(cemp.getNombrecargoempresa().replace(" ", ""), "warn", "titleCargoEmpresa", "lblSoloLetras")
                && validationBean.validarLongitudCampo(cemp.getNombrecargoempresa().replace(" ", ""), 4, 25,"warn", "titleCargoEmpresa", "lblLongitudNombreCargo")){
            if(cemp.getIdcargoempresa()==null || cemp.getIdcargoempresa().equals("0")){
               flag= cargoFacade.actualizarCargo(cemp, "A"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCargoEmpresa", "lblGuardarSuccess");
            }
            else{
                flag= cargoFacade.actualizarCargo(cemp, "U"); 
               if(flag.equals("0"))
                   validationBean.lanzarMensaje("info", "titleCargoEmpresa", "lblEditarSuccess");
            }
            limpiar();
            listaCargos = cargoFacade.obtenerCargos();
        }
    }
     */
    public void limpiar() {
        candidato = new CandidatoForm();
        candidato.setIdcandidato("");
        listaPuestos = new ArrayList<>();
        idcategoria = "";
        msgFile = "";
        msgFoto = "";
    }

    public void handleFotoUpload(FileUploadEvent event) {
        //validationBean.lanzarMensaje("warn","titleEmpresa","lblFileNotSuccess");
        String nameFile;
        String name;

        try {
            if (archivoFoto == null) {
                archivoFoto = event.getFile();

                name = archivoFoto.getFileName();
                nameFile = validationBean.generarRandom(name);

                validationBean.copyFile(nameFile, destinationFoto, archivoFoto.getInputstream());
                msgFoto = validationBean.getMsgBundle("lblFotoExito");

                candidato.setFoto("/fotosc/" + nameFile);
                validationBean.updateComponent("candidatoForm:msgFoto");
                validationBean.updateComponent("candidatoForm:candidatoFoto");
               // validationBean.updateComponent("candidatoForm:defaultFoto");
                nameFileFinal = nameFile;

            } else {
                archivoFoto = event.getFile();

                if (validationBean.deleteFile(destinationFoto + nameFileFinal)) {
                    name = archivoFoto.getFileName();
                    nameFile = validationBean.generarRandom(name);

                    validationBean.copyFile(nameFile, destinationFoto, archivoFoto.getInputstream());
                    msgFoto = validationBean.getMsgBundle("lblFotoExito");
                    //validationBean.updateComponent("candidatoForm:msgFoto");
                    candidato.setFoto("/fotosc/" + nameFile);
                    //validationBean.updateComponent("candidatoForm:fotoMostrar");
                    validationBean.updateComponent("candidatoForm:msgFoto");
                    validationBean.updateComponent("candidatoForm:candidatoFoto");
                    //validationBean.updateComponent("candidatoForm:defaultFoto");
                    nameFileFinal = nameFile;
                }

            }
        } catch (IOException e) {
            msgFoto = validationBean.getMsgBundle("lblFileUploadError");
            //validationBean.updateComponent("candidatoForm:msgFoto");
            if (archivoFoto != null) {
                if (validationBean.deleteFile("/fotosc/" + archivoFoto.getFileName())) {
                    archivoFoto = null;
                }
            }
            candidato.setFoto("");

            //validationBean.updateComponent("candidatoForm:fotoMostrar");
            validationBean.updateComponent("candidatoForm:msgFoto");
            validationBean.updateComponent("candidatoForm:candidatoFoto");
            //validationBean.updateComponent("candidatoForm:defaultFoto");
            e.printStackTrace();
        }
    }

}
