package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import java.io.IOException;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Ramandeep
 */
public class TeamsJsonReaderTest {
    
    TeamsJsonReader teamReader;
    
    @BeforeTest
    public void loadDB(){
        teamReader = new TeamsJsonReader();
    }
    
    @Test
    public void getListOfIndividuals_should_return_array_list_of_all_individual_objects()
            throws IOException{
        
        assertThat(teamReader.getListOfIndividuals().size()).isEqualTo(5);
        assertThat(teamReader.getListOfIndividuals().get(0).getName())
                .isEqualTo("John Doe");
        assertThat(teamReader.getListOfIndividuals().get(4).getId())
                .isEqualTo(1205);
        assertThat(teamReader.getListOfIndividuals().get(1).isActive())
                .isFalse();
        assertThat(teamReader.getListOfIndividuals().get(3).isActive())
                .isTrue();
    }
    
    @Test
    public void getListOfActiveIndividuals_ahould_return_array_list_of_only_active_individual_objects(){
        
        assertThat(teamReader.getListOfActiveIndividuals().size()).isEqualTo(4);
        for(Individual individual:teamReader.getListOfActiveIndividuals()){
            assertThat(individual.isActive()).isTrue();
        }
    }
    
    @Test
    public void getListOfInactiveIndividuals_should_return_array_list_of_only_inactive_individual_objects(){
        
        assertThat(teamReader.getListOfInactiveIndividuals().size()).isEqualTo(1);
        for(Individual individual:teamReader.getListOfInactiveIndividuals()){
            assertThat(individual.isActive()).isFalse();
        }
    }
    
    /* tests for getIndividualById(Integer id) */
    @Test
    public void getIndividualById_should_return_Individual_object_matching_id()
            throws ObjectNotFoundException{
        
        assertThat(teamReader.getIndividualById(1202).getName())
                .isEqualTo("Mark Twain");
    }
    
    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void getIndividualById_should_throw_ObjectNotFoundException_for_incorrect_id()
            throws ObjectNotFoundException{
        
        teamReader.getIndividualById(100);
    }
    
    /* tests for getIndividualByName(String name) */
    @Test
    public void getIndividualByName_should_return_Individual_object_matching_name()
            throws ObjectNotFoundException{
        
        assertThat(teamReader.getIndividualByName("Speedy Gonzales").getId())
                .isEqualTo(1203);
    }
    
    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void getIndividualByName_should_throw_ObjectNotFoundException_for_incorrect_name()
            throws ObjectNotFoundException{
        
        teamReader.getIndividualByName("Individual By This Name Does Not Exist");
    }
    
    @Test
    public void getListOfTeams_should_return_a_list_if_Team_object_from_db_json(){
        
        assertThat(teamReader.getListOfTeams().size()).isEqualTo(2);
        assertThat(teamReader.getListOfTeams().get(0).getId()).isEqualTo(1001);
        assertThat(teamReader.getListOfTeams().get(0).getName())
                .isEqualTo("Kino");
        assertThat(teamReader.getListOfTeams().get(0).getMembers().size())
                .isEqualTo(3);
        assertThat(teamReader.getListOfTeams().get(1).getId()).isEqualTo(1002);
        assertThat(teamReader.getListOfTeams().get(1).getName())
                .isEqualTo("B V Hammersmark");
        assertThat(teamReader.getListOfTeams().get(1).getMembers().size())
                .isEqualTo(3);
    }
    
    @Test
    public void Team_getActiveMembers_should_return_a_list_of_team_members_that_are_active(){
        assertThat(teamReader.getListOfTeams().get(0).getActiveMembers().size())
                .isEqualTo(2);
        assertThat(teamReader.getListOfTeams().get(1).getActiveMembers().size())
                .isEqualTo(3);
    }
    
    @Test
    public void Team_getInactiveMembers_should_return_a_list_of_individual_team_members_that_are_inactive(){
        assertThat(teamReader.getListOfTeams().get(0).getInactiveMembers()
                .size()).isEqualTo(1);
        assertThat(teamReader.getListOfTeams().get(0).getInactiveMembers()
                .get(0).getName()).isEqualTo("Mark Twain");
        
        assertThat(teamReader.getListOfTeams().get(1).getInactiveMembers()
                .size()).isEqualTo(0);
    }
    
}
