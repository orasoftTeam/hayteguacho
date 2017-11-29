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
import com.admin.hayteguacho.form.UserForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.captcha.botdetect.web.jsf.JsfCaptcha;
import com.hayteguacho.facade.CargoEmpresaFacade;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
import com.hayteguacho.facade.DepartamentoFacade;
import com.hayteguacho.facade.EmpresaFacade;
import com.hayteguacho.facade.MunicipioFacade;
import com.hayteguacho.facade.PaisFacade;
import com.hayteguacho.facade.TipologiaFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
@ManagedBean(name = "empresaController")
public class EmpresaController {
    private @Getter @Setter EmpresaForm empresa= new EmpresaForm();
    
    String appPath = System.getProperty("user.dir");
    private String destination=appPath + File.separator + "logos\\";
    
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
    
    @Inject
    LoginController loginBean;
    
    private @Getter @Setter UploadedFile archivo;
    private @Getter @Setter BufferedImage archivo2;
     private @Getter @Setter InputStream archivo3;
    private @Getter @Setter String msgFile;
    private @Getter @Setter List<DepartamentoForm> listaDepto;
    private @Getter @Setter List<MunicipioForm> listaMuni;
    private @Getter @Setter String idDepto;
    private @Getter @Setter String idMuni;
    private @Getter @Setter String nameFileFinal;
    private @Getter @Setter List<CategoriaEmpresaForm> listaCategoria;
    private @Getter @Setter List<CargoEmpresaForm> listaCargo;
    private @Getter @Setter String idCategoria;
    private @Getter @Setter String idCargo;
    private @Getter @Setter List<TipologiaForm> listaTipologia;
    private @Getter @Setter  String idTipologia;
    private @Getter @Setter  String repass;
    private @Getter @Setter JsfCaptcha captcha;
    private @Getter @Setter String captchaCode;
    
    
    @PostConstruct
    public void init(){
        String pais= loginBean.getPais();
        /*
        empresa.setNompais(pais.toUpperCase());
        List<PaisForm> tmp= paisFacade.obtenerPaisesPorNombre(pais.toUpperCase());
        if(!tmp.isEmpty()){
            empresa.setIdpais(tmp.get(0).getIdpais());
            listaDepto= deptoFacade.obtenerDepartamentos(tmp.get(0).getIdpais());
        }
        */
        listaCategoria= catFacade.obtenerCategorias();
        listaCargo= cargoFacade.obtenerCargos();
        listaTipologia= tipologiaFacade.obtenerTipologias();
        empresa.setIdempresa("0");
        UserForm usuario=loginBean.getUserLog();
        if(usuario!=null && usuario.getTipo().equals("E")){
            empresa= empresaFacade.obtenerEmpresaById(usuario.getIdentificador()).get(0);
            idCargo= empresa.getIdcargoempresa();
            idCategoria= empresa.getIdcategoria();
            idTipologia= empresa.getIdtipologia();
            idMuni= empresa.getIdciudad();
            idDepto= deptoFacade.obtenerDepartamentoByIdCiudad(idMuni).get(0).getIddepartamento();
            listaMuni= muniFacade.obtenerMunicipios(idDepto);
            
        }
            empresa.setNompais(pais.toUpperCase());
            List<PaisForm> tmp= paisFacade.obtenerPaisesPorNombre(pais.toUpperCase());
            if(!tmp.isEmpty()){
                empresa.setIdpais(tmp.get(0).getIdpais());
                listaDepto= deptoFacade.obtenerDepartamentos(tmp.get(0).getIdpais());
            }        
       
    }
    
    public boolean validarCaptcha(){
    boolean isHuman = captcha.validate(captchaCode);
    return isHuman;
    }
    
    public void changeDepartamento(){
        System.err.println("pasó x aca");
        if(idDepto.equals("")){
            listaMuni= new ArrayList<>();
        }
        else{
            listaMuni= muniFacade.obtenerMunicipios(idDepto);
        }
        validationBean.updateComponent("empresaForm:cmbMuni");
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
                && validationBean.validarLongitudCampo(empresa.getPassword().replace(" ", ""), 5, 20,"warn", "titleEmpresa", "lblLongitudClaveEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getNombrecontacto().replace(" ", ""), "warn", "titleEmpresa", "lblNomContactoReqEmpresa")
                && validationBean.validarSoloLetras(empresa.getNombrecontacto().replace(" ", ""), "warn", "titleEmpresa", "lblSoloLetrasNombre")
                && validationBean.validarLongitudCampo(empresa.getNombrecontacto().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudNomContactoEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getApellidocontacto().replace(" ", ""), "warn", "titleEmpresa", "lblApeContactoReqEmpresa")
                && validationBean.validarSoloLetras(empresa.getApellidocontacto().replace(" ", ""), "warn", "titleEmpresa", "lblSoloLetrasApellido")
                && validationBean.validarLongitudCampo(empresa.getApellidocontacto().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudApellidoContactoEmpresa")
                && validationBean.validarSeleccion(empresa.getIdcargoempresa(),"warn", "titleEmpresa", "lblSelectRegCargo")
                && validationBean.validarLongitudCampo(empresa.getTelefono1(), 7, 15, "warn", "titleEmpresa", "lblTelReq")
                &&
                validationBean.validarCampoVacio(empresa.getNombreempresa().replace(" ", ""), "warn", "titleEmpresa", "lblNameReqEmpresa")
                && validationBean.validarLongitudCampo(empresa.getNombreempresa().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudNombreEmpresa")
                &&
                validationBean.validarCampoVacio(empresa.getRazonsocial().replace(" ", ""), "warn", "titleEmpresa", "lblRazonReqEmpresa")
                 && validationBean.validarSoloLetras(empresa.getRazonsocial().replace(" ", ""), "warn", "titleEmpresa", "lblSoloLetrasRazon")
                && validationBean.validarLongitudCampo(empresa.getRazonsocial().replace(" ", ""), 4, 30,"warn", "titleEmpresa", "lblLongitudRazonEmpresa")
                &&
                validationBean.validarLongitudCampo(empresa.getIdtributaria().replace(" ", ""), 17, 17,"warn", "titleEmpresa", "lblLongitudTributarioEmpresa")
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
                validationBean.validarLongitudCampo(empresa.getDescripcionempresa(), 15, 255,"warn", "titleEmpresa", "lblLongitudDescripcionEmpresa")
                && validationBean.validarCampoVacio(captchaCode, "warn", "titleCandidato", "lblCaptchaReq")){
            
            if (empresa.getPassword().equals(repass)) {
                 if(empresa.getIdempresa()==null || empresa.getIdempresa().equals("0")){
               //empresa.setPassword(validationBean.encriptar(empresa.getPassword(), empresa.getEmail()));
               empresa.setLogo(empresa.getLogo()==null?"":empresa.getLogo());
               if (validarCaptcha()) {
                    flag= empresaFacade.actualizarEmpresa(empresa, "A"); 
                    if(flag.equals("0")){
                        loginBean.setUsuario(empresa.getEmail());
                        loginBean.setPassword(empresa.getPassword());
                        loginBean.logear();
                        //loginBean.setLoggedIn(true);
                        //loginBean.redireccionarEnlaces("membresia/vistaMembresia.xhtml?faces-redirect=true");
                            loginBean.redireccionarEnlaces("oferta/mantenimientoOferta.xhtml?faces-redirect=true");    
                      //  loginBean.logear();
                        //validationBean.lanzarMensaje("info", "titleEmpresa", "lblGuardarSuccess");
                        //limpiar();
                    }
                    else if(flag.equals("-1")){
                        validationBean.lanzarMensaje("warn", "titleEmpresa", "lblExistReg");
                    }
                    else if(flag.equals("-2")){
                        validationBean.lanzarMensaje("error", "titleEmpresa", "lblGuardarError");
                    }
                    else if(flag.equals("-3")){
                        validationBean.lanzarMensaje("error", "titleEmpresa", "lblCandidatoExist");
                    }
                    }else{
                     validationBean.lanzarMensaje("error", "titleCandidato", "lblCaptchaError");
                     }
            }
            UserForm usuario=loginBean.getUserLog();
            if(usuario!=null && usuario.getTipo().equals("E")){
                    flag= empresaFacade.actualizarEmpresa(empresa, "U"); 
                    switch (flag) {
                        case "0":
                            validationBean.lanzarMensaje("info", "titleEmpresa", "lblGuardarSuccess");
                            limpiar();
                            break;
                        case "-1":
                            validationBean.lanzarMensaje("warn", "titleEmpresa", "lblExistReg");
                            break;
                        default:
                            validationBean.lanzarMensaje("error", "titleEmpresa", "lblGuardarError");
                            break;               
                    }
            }
            }else{
            
            validationBean.lanzarMensaje("error", "titleEmpresa", "lblRepContra");
            }
            
            
        }
    }
    public void limpiar() {
       empresa= new EmpresaForm();
       empresa.setIdempresa("0");
       msgFile="";
       repass = "";
    }
    
    
    public void handleFileUpload(FileUploadEvent event) {
    //validationBean.lanzarMensaje("warn","titleEmpresa","lblFileNotSuccess");
    String nameFile;
    String name;
        try {
            if(archivo==null){
                archivo=event.getFile();
                BufferedImage img = ImageIO.read(archivo.getInputstream());
                if(validationBean.validarTamanioImagen(img)){
                   name = archivo.getFileName();
                   nameFile = validationBean.generarRandom(name);
                   archivo3 = validationBean.cutImage(img, name);
                validationBean.copyFile(nameFile,destination, archivo3);
                msgFile= validationBean.getMsgBundle("lblFileSuccess");
                /*String name = validationBean.generarRnadom();
                String[] ext = event.getFile().getFileName().split(".");
                String newname = name + "." + ext[1];*/
                empresa.setLogo("/logos/"+nameFile);
                validationBean.updateComponent("empresaForm:msgFile");
                validationBean.updateComponent("empresaForm:logoMostrar");
                nameFileFinal = nameFile;
              }else{
                archivo = null;
                }
            }
            else{
                archivo=event.getFile();
                BufferedImage img = ImageIO.read(archivo.getInputstream());
                if(validationBean.validarTamanioImagen(img)){
                  
                  if(validationBean.deleteFile(destination+nameFileFinal)){
                      name= archivo.getFileName();
                      nameFile= validationBean.generarRandom(name);
                      archivo3 = validationBean.cutImage(img, name);
                  validationBean.copyFile(nameFile,destination, archivo3);
                  msgFile= validationBean.getMsgBundle("lblFileSuccess");
                  validationBean.updateComponent("empresaForm:msgFile");
                  empresa.setLogo("/logos/"+nameFile); 
                  validationBean.updateComponent("empresaForm:logoMostrar");
                  nameFileFinal = nameFile;
                  }
                }
            }
        } catch (IOException e) {
            msgFile= validationBean.getMsgBundle("lblFileUploadError");
            validationBean.updateComponent("empresaForm:msgFile");
            if(archivo!=null){
                if(validationBean.deleteFile("/logos/"+archivo.getFileName())){
                    archivo=null;
                }
            }
            empresa.setLogo(""); 
            
            validationBean.updateComponent("empresaForm:logoMostrar");
            e.printStackTrace();
        }
    }
    
    
    
    
  
}
