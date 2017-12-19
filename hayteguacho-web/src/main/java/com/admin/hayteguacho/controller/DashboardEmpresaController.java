/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.controller;

import com.admin.hayteguacho.form.CategoriaEmpresaForm;
import com.admin.hayteguacho.form.EmpresaForm;
import com.admin.hayteguacho.form.MembresiaxEmpresaForm;
import com.admin.hayteguacho.form.OfertaAplicaForm;
import com.admin.hayteguacho.util.ValidationBean;
import com.hayteguacho.facade.CategoriaEmpresaFacade;
import com.hayteguacho.facade.EmpresaFacade;
import com.hayteguacho.facade.MembresiaxempresaFacade;
import com.hayteguacho.facade.TotalFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "dashboardEmpresa")
public class DashboardEmpresaController {

    @EJB
    ValidationBean vb;

    @EJB
    MembresiaxempresaFacade mexf;

    @EJB
    EmpresaFacade ef;

    @Inject
    LoginController login;

    @EJB
    CategoriaEmpresaFacade cef;

    @EJB
    TotalFacade tf;

    private @Getter
    @Setter
    EmpresaForm loggedEmp = new EmpresaForm();

    private @Getter
    @Setter
    PieChartModel pie;
    private @Getter
    @Setter
    Integer totalOfertas;
    private @Getter
    @Setter
    Integer restantes;
    private @Getter
    @Setter
    Integer maximoOfertas;
    private @Getter
    @Setter
    List<OfertaAplicaForm> listaOferta = new ArrayList<>();
    private @Getter
    @Setter
    MembresiaxEmpresaForm membresia = new MembresiaxEmpresaForm();

    @PostConstruct
    public void init() {
        try {
            loggedEmp = ef.obtenerEmp(login.getUserLog().getIdentificador());
            createPieModel();
            totalOfertas = listaOferta.size();
            membresia = mexf.obtenerMembresia(loggedEmp.getIdempresa());
            maximoOfertas = Integer.parseInt(membresia.getCantidadcontratada());
            restantes = maximoOfertas - totalOfertas;
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.controller.DashboardEmpresaController.init()");
            e.printStackTrace();
        }
    }

    public String getCat() {
        CategoriaEmpresaForm catForm = cef.entityToDto(cef.find(new BigDecimal(loggedEmp.getIdcategoria())), new CategoriaEmpresaForm());
        return catForm.getNombrecategoria();
    }

    public void createPieModel() {
        pie = new PieChartModel();

        try {
            listaOferta = tf.dashEmpresa(login.getPais(),
                    loggedEmp.getIdempresa());

            for (OfertaAplicaForm oaf : listaOferta) {
                pie.set(oaf.getTitulo(),
                        Integer.parseInt(oaf.getInscritos()));
            }
            pie.setTitle("Candidatos por Oferta");
            pie.setLegendPosition("w");
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.controller.DashboardEmpresaController.createPieModel()");
            e.printStackTrace();
        }
    }
}
