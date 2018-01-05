/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.util;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.ExternalContext;
/**
 *
 * @author dell
 */
@Stateless
public class CookieHelper {
    
    public void setCookie(String name, String value, int expiry){
    
        FacesContext fcontext = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest) fcontext.getExternalContext().getRequest();
        Cookie cookie = null;
        
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }
        
        if (cookie != null) {
            cookie.setValue(value);
        } else {
        cookie = new Cookie(name,value);
        cookie.setPath(request.getContextPath());
        }
        
        cookie.setMaxAge(expiry);
        
        HttpServletResponse response = (HttpServletResponse) fcontext.getExternalContext().getResponse();
        response.addCookie(cookie);
        System.out.println("se creo la cookie de prueba: " + cookie.getValue());
    }
    
    public Cookie getCookie(String name){
    
        try {
            FacesContext fcontext = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest)fcontext.getExternalContext().getRequest();
        Cookie cookie = null;
        
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    return cookie;
                }
            }
        }
    
        
        } catch (Exception e) {
            System.out.println("com.admin.hayteguacho.util.CookieHelper.getCookie()");
            e.printStackTrace();
            
        }
        return null;
    }
}
