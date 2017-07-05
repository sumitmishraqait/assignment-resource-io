package com.qainfotech.tap.training.resourceio.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class Team {

	private final String name;
	private final Integer id;
	private final List<Individual> members;

	public Team(Map<String, Object> teamMap) {

		Map<String, Object> myMap = teamMap;

		name = myMap.get("name").toString();
		id = Integer.parseInt(myMap.get("id").toString());
		members = (List<Individual>) teamMap.get("memberobject");

		// throw new UnsupportedOperationException("Not implemented.");
	}

	/**
	 * get team name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * get team id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	List<Individual> membersInTeam = new ArrayList<Individual>();
	JSONParser parser = new JSONParser();

	/**
	 * get list of individuals that are members of this team
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<Individual> getMembers() throws FileNotFoundException, IOException {

		return members;

	}

	List<Individual> myActiveTeam = new ArrayList<Individual>();
	List<Individual> mynotActiveTeam = new ArrayList<Individual>();

	/**
	 * get a list of individuals that are members of this team and are also
	 * active
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<Individual> getActiveMembers() throws FileNotFoundException, IOException {

		for (int i = 0; i < members.size(); i++) {

			if (members.get(i).isActive()) {
				{
					myActiveTeam.add(members.get(i));

				}
			}

		}

		return myActiveTeam;

	}

	/**
	 * get a list of individuals that are members of this team but are inactive
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<Individual> getInactiveMembers() throws FileNotFoundException, IOException {

		for (int i = 0; i < members.size(); i++) {

			if (!members.get(i).isActive()) {
				{
					mynotActiveTeam.add(members.get(i));

				}
			}

		}

		return mynotActiveTeam;

	}

}
