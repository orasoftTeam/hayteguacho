/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hayteguacho.util.form;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

/**
 *
 * @author LAP
 */
public abstract class AbstractForms<E,C> {
    
    private Class<C> formClass;
  
    public C entityToDto(E entity, C dto){
        return (C) new ModelMapper().map(entity, dto.getClass());
    }
    
    public List<C> entityToDtoList(List<E> entity, C dto){
        List<C> lisTmp= new ArrayList<>();
       // dtoTemp.
        for(E tmp: entity){
           lisTmp.add(entityToDto(tmp, dto));
        }
        return lisTmp;
    }
}
