/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.hayteguacho.util;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
public class FacesUtil {
    //Getters
    public static FacesContext getFacesContext(
    HttpServletRequest req, HttpServletResponse res){
     //get current facescontext
     FacesContext facesContext = FacesContext.getCurrentInstance();
     
     //check current facescontext
        if (facesContext == null) {
            
            //create new lifecycle
            LifecycleFactory lifecycleFactory = (LifecycleFactory)
                    FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
            
            //create new facescontext
            FacesContextFactory contextFactory = (FacesContextFactory)
                    FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            facesContext = contextFactory.getFacesContext(
                    req.getSession().getServletContext(), res, res, lifecycle);
            
            // creatw new view
            UIViewRoot view = facesContext.getApplication().getViewHandler().createView(
                    facesContext, "");
            facesContext.setViewRoot(view);
            
            //set current facescontext
            FacesContextWrapper.setCurrentInstance(facesContext);
        }
        return facesContext;
    
    }
    
    //Helpers
    
    //wrap the protected FacesContext.setCurrentInstance() in inner class
    private static abstract class FacesContextWrapper extends FacesContext{
    protected static void setCurrentInstance(FacesContext facesContext){
    FacesContext.setCurrentInstance(facesContext);
    }
    }
}
