package com.qainfotech.tap.training.resourceio;
import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsYamlReader {

	String file = "db.yaml";
	ClassLoader loader = this.getClass().getClassLoader();

	/**
	 * get a list of individual objects from db yaml file
	 * 
	 * @return
	 */

	public List<Individual> getListOfIndividuals() {
		List<Individual> listofindividual = new ArrayList<>();
		try {
			InputStream is = loader.getResourceAsStream(file);
			Yaml yaml = new Yaml();

			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) yaml.load(is);

			ArrayList ab = (ArrayList) result.get("individuals");
			Map<String, Object> map;
			for (int i = 0; i < ab.size(); i++) {
				map = ((Map<String, Object>) ab.get(i));
				Individual abc = new Individual(map);
				listofindividual.add(abc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listofindividual;
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
		List<Individual> listofindividual = new ArrayList<>();
		try {
			listofindividual = (new TeamsYamlReader()).getListOfIndividuals();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Individual individual = null;
		int index, match = 0;
		for (index = 0; index < listofindividual.size(); index++) {
			individual = listofindividual.get(index);
			if (individual.getId().compareTo(id) == 0) {
				match = 1;
				break;
			}
		}
		if (match == 0) {
			throw new ObjectNotFoundException("Individual", "id", id.toString());
		} else
			return individual;
	}

	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException {
		List<Individual> listofindividual = new ArrayList<>();
		int index, match = 0;
		Individual individual = null;
		try {
			listofindividual = (new TeamsYamlReader()).getListOfIndividuals();

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (index = 0; index < listofindividual.size(); index++) {
			individual = listofindividual.get(index);
			if (individual.getName().compareTo(name) == 0) {
				match = 1;
				break;
			}
		}

		if (match == 0) {
			throw new ObjectNotFoundException("Individual", "Name", name);
		} else
			return individual;
	}

	/**
	 * get a list of individual object who are not active
	 * 
	 * @return List of inactive individuals object
	 */
	public List<Individual> getListOfInactiveIndividuals() {
		List<Individual> lisofindividual = new ArrayList<>();
		try {
			lisofindividual = (new TeamsYamlReader()).getListOfIndividuals();
		} catch (Exception e) {
			e.printStackTrace();

		}
		List<Individual> listofinactivemember = new ArrayList<>();
		for (int index = 0; index < lisofindividual.size(); index++) {
			Individual individual = lisofindividual.get(index);
			if (!individual.isActive()) {
				listofinactivemember.add(individual);
			}
		}
		return listofinactivemember;
	}

	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 */
	public List<Individual> getListOfActiveIndividuals() {
		List<Individual> listofindividual = new ArrayList<>();
		try {
			listofindividual = (new TeamsYamlReader()).getListOfIndividuals();
		} catch (Exception e) {
			e.printStackTrace();

		}
		List<Individual> listofactivemember = new ArrayList<>();
		for (int index = 0; index < listofindividual.size(); index++) {
			Individual individual = listofindividual.get(index);
			if (individual.isActive()) {
				listofactivemember.add(individual);
			}
		}
		return listofactivemember;
	}

	/**
	 * get a list of team objects from db yaml
	 * 
	 * @return
	 */
	public List<Team> getListOfTeams() {
		List<Team> listofTeams = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<String, Object>();

		TeamsYamlReader reader = new TeamsYamlReader();
		try {

			InputStream inputStream = loader.getResourceAsStream(file);
			Yaml yaml = new Yaml();
			Map<String, Object> result = (Map<String, Object>) yaml.load(inputStream);
			ArrayList teamArray = (ArrayList) result.get("teams");
			Map<String, Object> map;

			for (int index = 0; index < teamArray.size(); index++) {
				List<Individual> individualList = new ArrayList<>();
				map = (Map<String, Object>) teamArray.get(index);
				map1.put("name", map.get("name"));
				map1.put("id", map.get("id"));
				List listofmembers = (List) map.get("members");
				for (int loc = 0; loc < listofmembers.size(); loc++) {
					individualList.add(reader.getIndividualById((Integer) listofmembers.get(loc)));

				}
				map1.put("members", individualList);
				listofTeams.add(new Team(map1));
			}

		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return listofTeams;

	}
}
