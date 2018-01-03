/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
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
    }
    
    public Cookie getCookie(String name){
    
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
    
        return null;
    }
}
