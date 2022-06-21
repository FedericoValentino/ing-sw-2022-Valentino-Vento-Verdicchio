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
    CurrentGameState dummy  = new CurrentGameState(2, false);
    Island i=new Island(0, dummy);
    CurrentGameState c=new CurrentGameState(5,false);
    Student s=new Student(Col.GREEN);
    Student s2=new Student(Col.RED);
    Student s3=new Student(Col.YELLOW);
    Student s4=new Student(Col.PINK);
    Student s5=new Student(Col.BLUE);
    Team t1=new Team(ColTow.BLACK, dummy);
    Team t2=new Team(ColTow.GREY, dummy);

    Player p1=new Player("ci", ColTow.BLACK,8, Wizard.LORD,false, dummy);
    Player p2=new Player("asd", ColTow.GREY,8,Wizard.SENSEI,false, dummy);
    Player p3=new Player("asd", ColTow.BLACK,8,Wizard.WITCH,false, dummy);
    Player p4=new Player("asd", ColTow.GREY,8,Wizard.DRUID,false, dummy);
    Player p5=new Player("asd", ColTow.GREY,8,Wizard.DRUID,false, dummy);

    @Test
    public void testInizializationIsland()
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
        i.addStudent(s);
        assertEquals(i.getCurrentStudents().size(),1);
        assertEquals(i.getCurrentStudents().get(0),s);
    }


    @Test
    public void testCalculateOwnership()
    {
        i.setMotherNature(true);
        i.getTeamInfluence()[0]=3;
        i.getTeamInfluence()[1]=4;
        i.getTeamInfluence()[2]=9;
        i.calculateOwnership();//problema su index array
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
    @Test
    public void testUpdateTeamInfluence()
    {
        //2 green , 1 blue, 1 yellow
        i.getCurrentStudents().add(s);
        i.getCurrentStudents().add(s2);
        i.getCurrentStudents().add(s3);
        i.getCurrentStudents().add(s4);
        i.getCurrentStudents().add(s);
        i.updateMotherNature();
        t1.addPlayer(p1);t1.addPlayer(p3);
        t2.addPlayer(p2);t2.addPlayer(p4);t2.addPlayer(p5);
        t1.updateProfessors();
        assertEquals(t1.getControlledProfessors().size(),0);

        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof
        p3.getSchool().updateProfessorsTable(1,true);//red prof
        p4.getSchool().updateProfessorsTable(3,true);//pink prof
        p5.getSchool().updateProfessorsTable(4,true);//blue prof

        t1.updateProfessors();
        t2.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        assertEquals(t1.getControlledProfessors().get(0), Col.GREEN);
        assertEquals(t2.getControlledProfessors().get(0), Col.YELLOW);
        assertEquals(t1.getControlledProfessors().get(1), Col.RED);
        assertEquals(t2.getControlledProfessors().get(1), Col.PINK);
        assertEquals(t2.getControlledProfessors().get(2), Col.BLUE);
        i.updateMotherNature();
        i.calculateOwnership();
        i.updateTeamInfluence(c.getCurrentTeams());
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