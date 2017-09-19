/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.MenuForm;
import com.admin.hayteguacho.form.UserForm;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LAP
 */

@Stateless
public class MenuFacade {
    @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    } 
    
    public MenuFacade(){
        
    }
    
    public List<MenuForm> obtenerModulos(String rol) {
        String sql = "select modulo.idmodulo identificador, modulo.nombremodulo nombremenu, '' opcion  \n" +
        "from tbl_modulos_x_rol rol, tbl_mnto_modulos modulo\n" +
        "where rol.idmodulo=modulo.idmodulo and rol.idrol="+rol+" and modulo.ESTADOMODULO='A'";
        Query q= getEntityManager().createNativeQuery(sql, "MenuMapping");
        List<MenuForm> temp= q.getResultList();
        return temp.isEmpty()? new ArrayList<MenuForm>():temp;
    }  
    
    public List<MenuForm> obtenerMenus(String modulo) {
        String sql = "select idmenuxmodulo identificador, nombremenu, opcion from tbl_menus_x_modulo\n" +
            "where idmodulo="+ modulo+" and estadomenu='A'";
        Query q= getEntityManager().createNativeQuery(sql, "MenuMapping");
        List<MenuForm> temp= q.getResultList();
        return temp.isEmpty()? new ArrayList<MenuForm>():temp;
    }
}
