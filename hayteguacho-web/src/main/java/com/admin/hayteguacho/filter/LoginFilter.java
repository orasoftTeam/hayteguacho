/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.filter;



import com.admin.hayteguacho.controller.LoginController;
import java.io.IOException;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
 * Filter checks if LoginBean has loginIn property set to true.
 * If it is not set then request is being redirected to the login.xhml page.
 * 
 * @author itcuties
 *
 */
public class LoginFilter implements Filter {
    
    @Inject
    LoginController loginBean;
 
    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String[] url= ((HttpServletRequest)request).getRequestURI().toString().split("/pages/");
        System.err.println(url);
        System.err.println(url[1]);
        String contextPath = ((HttpServletRequest)request).getContextPath();
        if (loginBean == null || !loginBean.isLoggedIn()) {
            //String contextPath = ((HttpServletRequest)request).getContextPath();
            if(!contextPath.contains("/faces")){
                ((HttpServletResponse)response).sendRedirect(contextPath + "/login.xhtml");
                loginBean.limpiar();
            }
        }
        else{
            if(!loginBean.buscarMenus(url[1])){
                ((HttpServletResponse)response).sendRedirect(contextPath + "/login.xhtml");
                loginBean.limpiar();
            }
        }
         
        chain.doFilter(request, response);
             
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }   

}