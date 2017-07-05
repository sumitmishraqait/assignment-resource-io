package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsJsonReader {

	List<Individual> individualList = new ArrayList<Individual>();
	List<Individual> inactiveIndObjList = new ArrayList<Individual>();
	List<Individual> activeIndObjList = new ArrayList<Individual>();
	JSONParser parser = new JSONParser();

	public List<Individual> getListOfIndividuals() throws FileNotFoundException, IOException {

		individualList.clear();
		JSONObject mainJsonObj = null;
		try {
			mainJsonObj = (JSONObject) parser.parse(new FileReader("src/main/resources/db.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray individualjsArray = (JSONArray) mainJsonObj.get("individuals");

		Individual tempObject;
		JSONObject innerJson;

		for (int index = 0; index < individualjsArray.size(); index++) {

			innerJson = (JSONObject) individualjsArray.get(index);

			Map<String, Object> individualMap = new HashMap();
			individualMap.put("name", innerJson.get("name").toString().trim());
			individualMap.put("id", innerJson.get("id").toString().trim());
			individualMap.put("active", innerJson.get("active").toString().trim());

			tempObject = new Individual(individualMap);

			try {
				individualList.add(tempObject);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return individualList;

	}

	/**
	 * get individual object by id
	 * 
	 * @param id
	 *            individual id
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 */
	public Individual getIndividualById(Integer id) throws ObjectNotFoundException {

		for (int index = 0; index < this.individualList.size(); index++) {

			if ((int) this.individualList.get(index).getId() == (int) id) {

				return this.individualList.get(index);

			}

		}

		throw new ObjectNotFoundException("IndividualByname", id.toString(), null);

	}

	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException {

		for (int index = 0; index < this.individualList.size(); index++) {

			if (this.individualList.get(index).getName().equals(name)) {

				return this.individualList.get(index);

			}

		}
		throw new ObjectNotFoundException("IndividualByname", name, name);
	}

	/**
	 * get a list of individual objects who are not active
	 * 
	 * @return List of inactive individuals object
	 * @throws IOException
	 * @throws FileNotFoundException
	 */

	public List<Individual> getListOfInactiveIndividuals() throws FileNotFoundException, IOException {

		if (individualList.isEmpty()) {
			individualList = this.getListOfIndividuals();
		}

		inactiveIndObjList.clear();

		for (int index = 0; index < this.individualList.size(); index++) {

			if (this.individualList.get(index).isActive() == false) {

				inactiveIndObjList.add(this.individualList.get(index));
			}
		}

		return inactiveIndObjList;

	}

	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<Individual> getListOfActiveIndividuals() throws FileNotFoundException, IOException {

		if (inactiveIndObjList.isEmpty()) {
			individualList = this.getListOfIndividuals();
		}

		activeIndObjList.clear();

		for (int index = 0; index < this.individualList.size(); index++) {

			if (this.individualList.get(index).isActive() == true) {

				activeIndObjList.add(this.individualList.get(index));
			}
		}

		return activeIndObjList;

	}

	List<Team> teamObjList = new ArrayList<Team>();

	/**
	 * get a list of team objects from db json
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ObjectNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Team> getListOfTeams()
			throws FileNotFoundException, IOException, NumberFormatException, ObjectNotFoundException {
		JSONObject mainJsObj = null;

		teamObjList.clear();

		try {
			mainJsObj = (JSONObject) parser.parse(new FileReader("src/main/resources/db.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (individualList.isEmpty()) {
			individualList = this.getListOfIndividuals();
		}

		JSONArray teamJsArray = (JSONArray) mainJsObj.get("teams");

		JSONObject myobj;

		for (int index = 0; index < teamJsArray.size(); index++) {

			myobj = (JSONObject) teamJsArray.get(index);

			Map<String, Object> TeamMap = new HashMap();
			TeamMap.put("name", myobj.get("name").toString().trim());
			TeamMap.put("id", myobj.get("id").toString().trim());

			JSONArray team_members = (JSONArray) myobj.get("members");
			List<Individual> memberIndividuals = new ArrayList<>();
			for (int i = 0; i < team_members.size(); i++) {

				memberIndividuals.add(this.getIndividualById(Integer.parseInt(team_members.get(i).toString())));

			}

			TeamMap.put("memberobject", memberIndividuals);

			Team tempObj = new Team(TeamMap);
			teamObjList.add(tempObj);

		}

		return teamObjList;

	}

}
