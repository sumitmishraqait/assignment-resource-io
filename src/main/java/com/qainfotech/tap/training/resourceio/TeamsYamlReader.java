package com.qainfotech.tap.training.resourceio;

import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.ValueHandlerMultiFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.internal.YamlParser;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsYamlReader {

	/**
	 * get a list of individual objects from db yaml file
	 * 
	 * @return
	 */

	List<Individual> individualList = new ArrayList<Individual>();
	List<Individual> inactiveIndObjList = new ArrayList<Individual>();
	List<Individual> activeIndObjList = new ArrayList<Individual>();

	public List<Individual> getListOfIndividuals() throws IOException {
		// throw new UnsupportedOperationException("Not implemented.");

		individualList.clear();

		File newConfiguration = new File("D:\\eclipse\\Demo\\src\\main\\resources\\db.yaml");
		InputStream is = null;
		try {
			is = new FileInputStream(newConfiguration);
			System.out.println(is.read());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();

		@SuppressWarnings("unchecked")
		Map<String, ArrayList> yamlParsers = (Map<String, ArrayList>) yaml.load(is);

		

		ArrayList individuals = (ArrayList) yamlParsers.get("ndividuals");

		for (int i = 0; i < individuals.size(); i++) {
			Map each_individual = (Map<String, ArrayList>) individuals.get(i);

			System.out.println(each_individual);

			Map<String, Object> individualMap = new HashMap();
			individualMap.put("name", each_individual.get("name").toString().trim());
			individualMap.put("id", each_individual.get("id").toString().trim());
			individualMap.put("active", each_individual.get("active").toString().trim());

			Individual tempObject = new Individual(individualMap);

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
	 * @throws IOException 
	 */
	public Individual getIndividualById(Integer id) throws ObjectNotFoundException, IOException {

		
		if (individualList.isEmpty()) {
			this.getListOfIndividuals();
		}
		for (int index = 0; index < this.individualList.size(); index++) {

			if ((int) this.individualList.get(index).getId() == (int) id) {

				return this.individualList.get(index);

			}

		}

		throw new ObjectNotFoundException("Individual", "id", id.toString());

	}

	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 * @throws IOException 
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException, IOException {

			
		if (this.individualList.isEmpty()) {
			this.getListOfIndividuals();
		}
		for (int index = 0; index < this.individualList.size(); index++) {

			if (this.individualList.get(index).getName().equals(name)) {

				return this.individualList.get(index);

			}

		}

		throw new ObjectNotFoundException("Individual", "Name", name);

	}

	/**
	 * get a list of individual objects who are not active
	 * 
	 * @return List of inactive individuals object
	 * @throws IOException
	 */
	public List<Individual> getListOfInactiveIndividuals() throws IOException {

		if (this.individualList.isEmpty()) {
			this.getListOfIndividuals();
		}

		inactiveIndObjList.clear();

		for (int index = 0; index < this.individualList.size(); index++) {

			if (this.individualList.get(index).isActive() == false) {

				this.inactiveIndObjList.add(individualList.get(index));
			}
		}

		return inactiveIndObjList;
	}

	List<Team> teamObjList = new ArrayList<Team>();

	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 * @throws IOException
	 */
	public List<Individual> getListOfActiveIndividuals() throws IOException {

		if (inactiveIndObjList.isEmpty()) {
			individualList = this.getListOfIndividuals();
		}

		activeIndObjList.clear();

		for (int index = 0; index < individualList.size(); index++) {

			if (individualList.get(index).isActive() == true) {

				activeIndObjList.add(individualList.get(index));
			}
		}

		return activeIndObjList;

	}

	/**
	 * get a list of team objects from db yaml
	 * 
	 * @return
	 * @throws IOException
	 * @throws ObjectNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Team> getListOfTeams() throws IOException, NumberFormatException, ObjectNotFoundException {

		teamObjList.clear();

		File newConfiguration = new File("D:\\eclipse\\Demo\\src\\main\\resources\\db.yaml");
		InputStream is = null;
		try {
			is = new FileInputStream(newConfiguration);
			System.out.println(is.read());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (individualList.isEmpty()) {
			individualList = this.getListOfIndividuals();
		}

		Yaml yaml = new Yaml();

		@SuppressWarnings("unchecked")
		Map<String, ArrayList> yamlParsers = (Map<String, ArrayList>) yaml.load(is);

		ArrayList teams = yamlParsers.get("teams");

		for (int index = 0; index < teams.size(); index++) {

			Map<String, Object> TeamMap = new HashMap();
			Map<String, Object> team_content = (Map<String, Object>) teams.get(index);

			System.out.println(team_content.get("members"));
			TeamMap.put("name", team_content.get("name").toString().trim());
			TeamMap.put("id", team_content.get("id").toString().trim());

			List team_ids = (List) team_content.get("members");

			List<Individual> mytemp = new ArrayList<>();
			for (int c = 0; c < team_ids.size(); c++) {

				mytemp.add(this.getIndividualById(Integer.parseInt(team_ids.get(c).toString())));
			}

			System.err.println(mytemp.size());
			TeamMap.put("memberobject", mytemp);

			Team tempObj = new Team(TeamMap);
			teamObjList.add(tempObj);
		}

		System.err.println(teamObjList);
		return teamObjList;

	}

	public List<Individual> getMembers(List<Individual> members, int id) throws FileNotFoundException, IOException {

		File newConfiguration = new File("D:\\eclipse\\Demo\\src\\main\\resources\\db.yaml");
		InputStream is = null;
		try {
			is = new FileInputStream(newConfiguration);
			System.out.println(is.read());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();

		@SuppressWarnings("unchecked")
		Map<String, ArrayList> yamlParsers = (Map<String, ArrayList>) yaml.load(is);

		ArrayList teams = yamlParsers.get("teams");

		List<Individual> membersInTeam = new ArrayList<>();
		for (int index = 0; index < membersInTeam.size(); index++) {
			Map<String, ArrayList> team_members = (Map<String, ArrayList>) teams.get(index);

		}
		return membersInTeam;

	}

}
