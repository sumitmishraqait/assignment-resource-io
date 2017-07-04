package com.qainfotech.tap.training.resourceio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONArray;

import com.qainfotech.tap.training.resourceio.TeamsJsonReader;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class Team {
    
    private final String name;
    private final Integer id;
    private final List<Individual> members;
   // private TeamsJsonReader teamJsonReader;
    
  
    
    public Team(Map<String,Object> teamMap){
    	this.id=Integer.parseInt(teamMap.get("id").toString());
    	this.name=teamMap.get("name").toString();
    	this.members=new ArrayList<>();
    	List<Individual>arrayOfIndividuals=(new TeamsJsonReader()).getListOfIndividuals();
    	JSONArray memberArray=(JSONArray) teamMap.get("members");
    	Iterator<Individual> itr=arrayOfIndividuals.iterator();
    	while(itr.hasNext()){
    		Individual individual=itr.next();
    		for(int i=0;i<memberArray.size();i++){
    			if(individual.getId()==Integer.parseInt(memberArray.get(i).toString())){
    				this.members.add(individual);
    			}
    		}
    	}
    	
    	
    }
    
    /**
     * get team name
     * 
     * @return 
     */
    public String getName(){
        return name;
    }
    
    /**
     * get team id
     * 
     * @return 
     */
    public Integer getId(){
        return id;
    }
    
    /** 
     * get list of individuals that are members of this team
     * 
     * @return 
     */
    public List<Individual> getMembers(){
        return members;
    }
    
    /**
     * get a list of individuals that are members of this team and are also active
     * 
     * @return 
     */
    public List<Individual> getActiveMembers(){
        //throw new UnsupportedOperationException("Not implemented.");
    	List<Individual> activeMembersOfTeam=new ArrayList<>();
    	Iterator<Individual> itr=this.members.iterator();
    	while(itr.hasNext()){
    		Individual individual=itr.next();
    		if(individual.isActive()){
    			activeMembersOfTeam.add(individual);
    		}
    	}
    	return activeMembersOfTeam;
    }
        
    /**
     * get a list of individuals that are members of this team but are inactive
     * 
     * @return 
     */
    public List<Individual> getInactiveMembers(){
       // throw new UnsupportedOperationException("Not implemented.");
    	List<Individual> inactiveMembersListOfTeam=new ArrayList<>();
    	Iterator<Individual>itr=this.members.iterator();
    	while(itr.hasNext()){
    		Individual individual=itr.next();
    		if(!(individual.isActive())){
    			inactiveMembersListOfTeam.add(individual);
    		}
    	}
    	return inactiveMembersListOfTeam;
    }
}
