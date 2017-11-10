/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.MembresiaForm;
import com.admin.hayteguacho.form.MembresiaxEmpresaForm;
import com.admin.hayteguacho.form.MenuForm;
import com.admin.hayteguacho.form.OfertaForm;
import com.admin.hayteguacho.form.UserForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.MembresiaFacade;
import com.hayteguacho.facade.MembresiaxempresaFacade;
import com.hayteguacho.facade.MenuFacade;
import com.hayteguacho.facade.UserFacade;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author LAP
 */

/*
@ManagedBean(name = "login")
@SessionScoped
 */
//@Named (value = "login")
@ManagedBean
@Named(value = "login")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private MenuFacade menuFacade;

    @EJB
    private UserFacade userFacade;

    @EJB

    private ValidationBean validationBean;

    @EJB
    private MembresiaFacade membresiaFacade;

    @EJB
    private MembresiaxempresaFacade membresiaxEmpresaFacade;

    @Inject
    private MostrarOfertaController ofertas;

    private @Getter
    @Setter
    String idCompany = "";
    private @Getter
    @Setter
    String idRol = "";
    private @Getter
    @Setter
    String usuario;
    private @Getter
    @Setter
    String password;

    private @Getter
    @Setter
    com.admin.hayteguacho.form.UserForm userLog;

    private @Getter
    @Setter
    String nombreUsuario;

    private @Getter
    @Setter
    boolean loggedIn;

    private @Getter
    @Setter
    boolean isMembresia;

    private @Getter
    @Setter
    List<MenuForm> listaModulos = new ArrayList<>();
    private @Getter
    @Setter
    List<MenuForm> listaOpciones = new ArrayList<>();

    private @Getter
    @Setter
    boolean isMenu = false;

    private @Getter
    @Setter
    int indexNav = -1;

    private @Getter
    @Setter
    String pais = "";

    private @Getter
    @Setter
    MembresiaForm membresiaActual;
    private @Getter
    @Setter
    MembresiaxEmpresaForm membresiaxEmpresaActual;

    @PostConstruct
    public void init() {
        loggedIn = false;
        System.err.println("El valor de logged in es: " + loggedIn);
    }

    @PreDestroy
    public void close() {
        //String pag = "/indexPaises.xhtml";
        if (userLog.getTipo().equals("E")) {
            String log = userFacade.actualizarLogUser(userLog, "OUT", "A");
            if (log.equals("1")) {
                userLog = new UserForm();
                setUsuario("");
                setPassword("");
                loggedIn = false;
                listaModulos = new ArrayList<MenuForm>();
                listaOpciones = new ArrayList<MenuForm>();
                isMenu = false;
            } else if (log.equals("-2")) {
                //validationBean.lanzarMensaje("error", "titleLogin", "lblErrorTransact");
                System.err.println("Error de transaccion");
            }
        }
        //redireccionar(pag);      
    }

    public boolean buscarMenus(String op) {

        for (MenuForm obj : listaModulos) {
            listaOpciones = menuFacade.obtenerMenus(obj.getIdentificador());
            for (MenuForm obj2 : listaOpciones) {
                if (obj2.getOpcion().equalsIgnoreCase(op)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void activarLinks(int indice, String opcion) {
        if (indice == -2) {
            if (indexNav < 0) {
                indexNav = -1;
            }
        } else {
            indexNav = indice;
        }
        /*
        if(!(indice == -2 && indexNav < 0)){
           indexNav= indice; 
        }
        /*
        System.err.println("el indice es: "+ indice +" y su pagina es:"+opcion);
        if(indice < 3){
            redireccionar(opcion);
        }
        else{
            redireccionar("/pages/"+opcion);
        }
         */
    }

    public String setearColor(int indice) {
        String color = "";
        if (indice == indexNav) {
            color = "background-color:green;";
        } else {
            color = "background-color:transparent;";
        }
        return color;
    }

    public void limpiar() {
        if (!loggedIn) {
            setUsuario("");
            setPassword("");
        }
    }

    public List<MenuForm> obtenerListaOpciones(int index) {
        if (!isMenu) {
            if (listaModulos.size() != 0) {
                String codModulo = listaModulos.get(index).getIdentificador();
                listaOpciones = menuFacade.obtenerMenus(codModulo);
            }
            if (listaModulos.size() == index) {
                isMenu = true;
            }
        }
        //listaOpciones = menuFacade.buscarOpciones(codModulo, idCompany);
        return listaOpciones;
    }

    public void logear() {
        System.err.println("Entro a logear");
        String pag = "";
        UserForm usuario = userFacade.getUser(getUsuario(), getPassword());
        if (usuario.getCorreo() == null) {
            validationBean.lanzarMensaje("warn", "titleLogin", "lblErrorLogin");
            System.err.println("Usuarios incorrecto");
            //limpiar();
            // pag="faces/login.xhtml";
            // JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("titleUserNotFound"));
        } else if (usuario.getCorreo() != null) {
            if (usuario.getTipo().equals("E")) {
                String log = userFacade.actualizarLogUser(usuario, "IN", "A");
                if (log.equals("0")) {
                    userLog = usuario;
                    setUsuario("");
                    setPassword("");
                    loggedIn = true;
                    isMembresia = true;
                    listaModulos = menuFacade.obtenerModulos(usuario.getIdrol());
                } else if (log.equals("-1")) {
                    validationBean.lanzarMensaje("warn", "titleLogin", "lblLimitUser");
                    System.err.println("Excedió el numero de usuarios");
                } else if (log.equals("-2")) {
                    validationBean.lanzarMensaje("error", "titleLogin", "lblErrorTransact");
                    System.err.println("Error de transaccion");
                } else if (log.equals("-3")) {
                    isMembresia = false;
                    userLog = usuario;
                    setUsuario("");
                    setPassword("");
                    loggedIn = true;
                    listaModulos = menuFacade.obtenerModulos(usuario.getIdrol());
                    redireccionarEnlaces("membresia/vistaMembresia.xhtml");
                    //redireccionar("/index.xhtml");
                }
            } else if (usuario.getTipo().equals("C")) {
                userLog = usuario;
                setUsuario("");
                setPassword("");
                loggedIn = true;
                listaModulos = menuFacade.obtenerModulos(usuario.getIdrol());
            }

            System.err.println("la imagen es: " + userLog.getImagen());
            /*
            pag = "/pages/empleado/agregarEmpleado.xhtml";
            setIdRol(usuario.getIdrol());
            setIdCompany(usuario.getIdcompany());
            setNombreUsuario(usuario.getNombre());
            listaModulos = menuFacade.buscarModulos(idRol, idCompany);
            //cargarMenus();
            setLoggedIn(true);
            redireccionar(pag);
             */
        }
    }

    /*
    public void logearEmpresa() {
        //UserForm usuario = userFacade.getUser(getUsuario(), getPassword());
        //userLog = usuario;
        /*
        setUsuario("");
        setPassword("");
        loggedIn = true;
        isMembresia = false;
        
        loggedIn = true;
        listaModulos = menuFacade.obtenerModulos(usuario.getIdrol());
    }
     */

    public void redirectLogin() {
        redireccionar("/login.xhtml");
    }

    public void doLogout() {
        // Set the paremeter indicating that user is logged in to false
        //loggedIn = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        String pag = "/indexPaises.xhtml";
        if (userLog.getTipo().equals("E")) {
            String log = userFacade.actualizarLogUser(userLog, "OUT", "A");
            if (log.equals("1")) {
                userLog = new UserForm();
                setUsuario("");
                setPassword("");
                loggedIn = false;
                validationBean.lanzarMensaje("info", "titleLogin", "titleRegresarLogin");
                listaModulos = new ArrayList<MenuForm>();
                listaOpciones = new ArrayList<MenuForm>();
                isMenu = false;
            } else if (log.equals("-2")) {
                validationBean.lanzarMensaje("error", "titleLogin", "lblErrorTransact");
                System.err.println("Error de transaccion");
            }
        }
        redireccionar(pag);
    }

    public void seleccionarPais(String pais) {
        this.pais = pais;
        ofertas.setPais(pais);
        ofertas.init();
        redireccionar("/mostrarOfertas.xhtml?opcion=0");
    }

    public void redireccionar(String pag) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + pag);
        } catch (IOException ex) {
            System.err.println("entro acá al error");
            //JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("titleUserNotFound"));
            //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redireccionarEnlaces(String pag) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/pages/" + pag);
        } catch (IOException ex) {
            System.err.println("entro acá al error");
            //JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("titleUserNotFound"));
            //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean obtenerMembresiaActual() {
        boolean flag = false;

        if (loggedIn) {

            if (userLog.getTipo().toLowerCase().equals("e")) {
                try {
                    List<MembresiaForm> lista = membresiaFacade.obtenerMembresiaEmpresa(Integer.parseInt(userLog.getIdentificador()));
                    if (!lista.isEmpty()) {
                        membresiaActual = lista.get(0);
                        membresiaxEmpresaActual = membresiaxEmpresaFacade.obtenerMembresia(userLog.getIdentificador());
                        flag = true;
                    }
                } catch (Exception e) {
                    System.out.println("com.admin.hayteguacho.controller.LoginController.obtenerMembresiaActual()");
                    e.printStackTrace();
                }
            }

        }

        return flag;
    }

    public boolean mostrarBaseCandidatos() {
        boolean flag = false;
        if (loggedIn) {
            try {
                List<MembresiaForm> lista = membresiaFacade.obtenerMembresiaEmpresa(Integer.parseInt(userLog.getIdentificador()));
                if (!lista.isEmpty()) {
                    membresiaActual = lista.get(0);
                    if (membresiaActual.getTitulomembresia().toLowerCase().equals("gold")) {
                        flag = true;
                    }

                }
            } catch (Exception e) {
                System.out.println("com.admin.hayteguacho.controller.LoginController.obtenerMembresiaActual()");
                e.printStackTrace();
            }
        }

        return flag;

    }

    public String obtenerFecha(String fecha) {
        String dateStr = fecha;
        DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        DateFormat writeFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    public String colorMem(String title) {
        String color = "black";
        switch (title.toLowerCase()) {
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

}
