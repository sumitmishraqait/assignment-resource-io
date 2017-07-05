package com.qainfotech.tap.training.resourceio.model;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class Individual {
    
    private final String name;
    private final Integer id;
    private final Boolean active;
    
    
    public Individual(Map<String, Object> individualMap){
    	
    Map<String, Object> myMap = individualMap;
    	
    name = myMap.get("name").toString();
    id = Integer.parseInt(myMap.get("id").toString());
    active = Boolean.parseBoolean(myMap.get("active").toString());
       
    // throw new UnsupportedOperationException("Not implemented.");
    }
  
    
    /**
     * get the name of individual
     * 
     * @return individual name
     */
    public String getName(){
        return name;
    }
    
    /**
     * get the employee of of individual
     * @return id
     */
    public Integer getId(){
        return id;
    }
    
    /**
     * get the active status of individual
     * 
     * @return 
     */
    public Boolean isActive(){
        return active;
    }
    
   
    
}
