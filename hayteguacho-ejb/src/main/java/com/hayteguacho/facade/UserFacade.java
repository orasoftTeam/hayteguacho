/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.facade;

import com.admin.hayteguacho.form.UserForm;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
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
public class UserFacade {

     @PersistenceContext(unitName = "hayteguachoPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
    }

    public UserForm getUser(String user, String pass) {
        String sql = "select usuario.tipo, usuario.identificador, usuario.nombre, usuario.correo, "
         + "(SELECT QB_ENCRIPCION.FB_DESCENCRIPTAR(usuario.contrasena) FROM DUAL) contrasena, rol.NOMBREROL, rol.IDROL, roluser.IDROLUSUARIO from tbl_rolusuario roluser,\n" +
        "(\n" +
        "select 'E' tipo,  company.IDEMPRESA identificador, company.NOMBREEMPRESA nombre, company.correocontactoempresa correo, company.contrasenacontactoempresa contrasena from tbl_empresa company \n" +
        "where company.correocontactoempresa='"+user+"' \n" +
        "and \n" +
        "(SELECT QB_ENCRIPCION.FB_DESCENCRIPTAR(company.contrasenacontactoempresa) FROM DUAL)='"+pass+"'\n" +
        "union \n" +
        "select 'C' tipo, candidato.idcandidato identificador, (candidato.nombrecandidato || candidato.apellidocandidato) as  nombre, candidato.correocandidato correo, candidato.contrasenacandidato contrasena from tbl_candidato candidato \n" +
        "where candidato.correocandidato='"+user+"' and candidato.estadocandidato='A' \n" +
        "and \n" +
        "(SELECT QB_ENCRIPCION.FB_DESCENCRIPTAR(candidato.contrasenacandidato) FROM DUAL)='"+pass+"'\n" +
        ") usuario, tbl_rol rol\n" +
        "where roluser.EMAILUSUARIO=usuario.correo and roluser.ESTADOROLUSUARIO='A'\n" +
        "and roluser.IDROL=rol.IDROL and rol.ESTADOROL='A'";
        Query q= getEntityManager().createNativeQuery(sql, "UsuarioMapping");
        List<UserForm> temp= q.getResultList();
        return temp.isEmpty()? new UserForm():temp.get(0);
    }
    
    public String actualizarLogUser(UserForm uf, String tipoLog, String op) {
        String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            //cn.setAutoCommit(false);
            CallableStatement cs = cn.prepareCall("{call HAYTEGUACHO.PROC_ACTUALIZA_LOG_USER (?,?,?,?,?)}");
            cs.setInt(1, 0);
            cs.setInt(2, new Integer(uf.getIdentificador()));
            cs.setString(3, tipoLog);
            cs.setString(4, op);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            flag = cs.getString(5);
            System.out.println(flag);
            cs.close();
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(OK)");
        } catch (Exception e) {
            //System.out.println("com.infomedic.facade.UsuarioFacade.agregarUsuario(ERROR)");
            e.printStackTrace();
        }
        return flag;
    }
}
