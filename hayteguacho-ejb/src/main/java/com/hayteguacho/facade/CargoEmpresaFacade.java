/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.CargoEmpresaForm;
import com.hayteguacho.entity.TblCargoempresa;
import com.hayteguacho.util.facade.AbstractFacade;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LAP
 */
@Stateless
public class CargoEmpresaFacade extends AbstractFacade<TblCargoempresa, CargoEmpresaForm> {

    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public CargoEmpresaFacade() {
        super(TblCargoempresa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<CargoEmpresaForm> obtenerCargos() {
        Query q = getEntityManager().createNativeQuery("select * from tbl_cargoempresa", TblCargoempresa.class);
        List<TblCargoempresa> listaEntity;
        List<CargoEmpresaForm> listaEntityForm;

        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntityForm = new ArrayList<CargoEmpresaForm>();
            } else {
                listaEntityForm = this.entityToDtoList(listaEntity, new CargoEmpresaForm());
            }
        } catch (Exception ex) {
            listaEntityForm = new ArrayList<CargoEmpresaForm>();
        }

        return listaEntityForm;
    }

    public String actualizarCargo(CargoEmpresaForm cef, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_CARGO_EMP (?,?,?,?)}");
            cs.setString(1, cef.getNombrecargoempresa().toUpperCase());
            cs.setString(2, op);
            cs.setInt(3, new Integer(cef.getIdcargoempresa()));
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(4);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
