package it.polimi.ingsw.model.boards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import org.junit.Test;

import static org.junit.Assert.*;

public class IslandTest {
    Island i=new Island(0);
    CurrentGameState c=new CurrentGameState(5,false);
    Student studGreen =new Student(Col.GREEN);
    Student studRed =new Student(Col.RED);
    Student studYellow =new Student(Col.YELLOW);
    Student studPink =new Student(Col.PINK);
    Student studBlue =new Student(Col.BLUE);
    Team t1=new Team(ColTow.BLACK);
    Team t2=new Team(ColTow.GREY);

    Player p1=new Player("ci", ColTow.BLACK,8, Wizard.LORD,false);
    Player p2=new Player("asd", ColTow.GREY,8,Wizard.SENSEI,false);
    Player p3=new Player("asd", ColTow.BLACK,8,Wizard.WITCH,false);
    Player p4=new Player("asd", ColTow.GREY,8,Wizard.DRUID,false);

    /**
     * Test the initialization of mother nature= false, group=false, ecc...
     */
    @Test
    public void testInitializationIsland()
    {
        assertEquals(i.getIslandId(),0);
        assertFalse(i.getMotherNature());
        assertFalse(i.getGroup());
        assertEquals(i.getTowerNumber(),0);
        assertFalse(i.getNoEntry());
        assertEquals(i.getTeamInfluence(),i.getTeamInfluence());
    }

    @Test
    public void testAddStudent()
    {
        assertEquals(i.getCurrentStudents().size(),0);
        i.addStudent(studGreen);
        assertEquals(i.getCurrentStudents().size(),1);
        assertEquals(i.getCurrentStudents().get(0), studGreen);
    }


    /**
     * This method tests the ownership of an island adding the influence of 3 teams and verifying that the 2 team has
     * the ownership
     */
    @Test
    public void testCalculateOwnership()
    {
        i.setMotherNature(true);
        i.updateTeamInfluence(3,0);
        i.updateTeamInfluence(4,1);
        i.updateTeamInfluence(9,2);

        i.calculateOwnership();
        assertEquals(i.getOwnership(), ColTow.BLACK);
    }

    @Test
    public void testUpdateNoEntry()
    {
        assertFalse(i.getNoEntry());
        i.updateNoEntry();
        assertTrue(i.getNoEntry());
        i.updateNoEntry();
        assertFalse(i.getNoEntry());
    }

    /**
     * This method put some students on the island and some professor in the dining of some players
     * Then try to calculate the team influence and then test also if the ownership is correct
     */
    @Test
    public void testUpdateTeamInfluence()
    {
        //2 green , 1 red, 1 yellow, 1 pink
        i.getCurrentStudents().add(studGreen);
        i.getCurrentStudents().add(studGreen);
        i.getCurrentStudents().add(studRed);
        i.getCurrentStudents().add(studYellow);
        i.getCurrentStudents().add(studPink);

        i.updateMotherNature();
        t1.addPlayer(p1);t1.addPlayer(p3);
        t2.addPlayer(p2);t2.addPlayer(p4);
        t1.updateProfessors();
        assertEquals(t1.getControlledProfessors().size(),0);



        p1.getSchool().updateProfessorsTable(0,true);//green prof to p1 (t1)
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof to p2 (t2)
        p3.getSchool().updateProfessorsTable(1,true);//red prof to p3 (t1)
        p4.getSchool().updateProfessorsTable(3,true);//pink prof to p4 (t2)


        t1.updateProfessors();
        t2.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);

        assertEquals(t1.getControlledProfessors().get(0), Col.GREEN);
        assertEquals(t2.getControlledProfessors().get(0), Col.YELLOW);
        assertEquals(t1.getControlledProfessors().get(1), Col.RED);
        assertEquals(t2.getControlledProfessors().get(1), Col.PINK);

        i.updateTeamInfluence(c.getCurrentTeams());

        assertEquals(2,i.getTeamInfluence()[0]); //team grey influence =2
        assertEquals(0,i.getTeamInfluence()[1]); //team white 0 (beacuse it doesn't exit)
        assertEquals(3,i.getTeamInfluence()[2]); //team black influence =3

        i.calculateOwnership();
        assertEquals(i.getOwnership(),ColTow.BLACK);
    }

    @Test
    public void testUpdateTeamInfluence2()
    {
        i.getTeamInfluence()[0]=0;
        i.getTeamInfluence()[1]=34;
        i.getTeamInfluence()[2]=5;
        assertEquals(i.getTeamInfluence()[0],0);
        i.updateTeamInfluence(4,0);
        assertEquals(i.getTeamInfluence()[0],4);

    }
    @Test
    public void testUpdateMotherNature()
    {
        assertFalse(i.getMotherNature());
        i.updateMotherNature();
        assertTrue(i.getMotherNature());
        i.updateMotherNature();
        assertFalse(i.getMotherNature());
    }

}