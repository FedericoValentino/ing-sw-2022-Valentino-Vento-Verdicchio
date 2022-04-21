package model.boards;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class IslandTest {
    Island i=new Island(0);
    CurrentGameState c=new CurrentGameState(5,false);
    Student s=new Student(Col.GREEN);
    Student s2=new Student(Col.RED);
    Student s3=new Student(Col.YELLOW);
    Student s4=new Student(Col.PINK);
    Student s5=new Student(Col.BLUE);
    Team t1=new Team(ColTow.BLACK);
    Team t2=new Team(ColTow.GREY);

    Player p1=new Player("ci", ColTow.BLACK,8,"ca",false);
    Player p2=new Player("asd", ColTow.GREY,8,"caadsds",false);
    Player p3=new Player("asd", ColTow.BLACK,8,"caadsds",false);
    Player p4=new Player("asd", ColTow.GREY,8,"caadsds",false);
    Player p5=new Player("asd", ColTow.GREY,8,"caadsds",false);

    @Test
    public void testInizializationIsland()
    {
        assertEquals(i.getIslandId(),0);
        assertFalse(i.getMotherNature());
        assertFalse(i.getGroup());
        assertEquals(i.getTowerNumber(),0);
        assertFalse(i.getNoEntry());
        assertEquals(i.teamInfluence,i.getTeamInfluence());
    }
    @Test
    public void testAddStudent()
    {
        assertEquals(i.getCurrentStudents().size(),0);
        i.addStudent(s);
        assertEquals(i.getCurrentStudents().size(),1);
        assertEquals(i.currentStudents.get(0),s);
    }


    @Test
    public void testCalculateOwnership()
    {
        i.motherNature=true;
        i.teamInfluence[0]=3;
        i.teamInfluence[1]=4;
        i.teamInfluence[2]=9;
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
        i.currentStudents.add(s);
        i.currentStudents.add(s2);
        i.currentStudents.add(s3);
        i.currentStudents.add(s4);
        i.currentStudents.add(s);
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
        assertTrue(t1.getControlledProfessors().get(0).equals(Col.GREEN));
        assertTrue(t2.getControlledProfessors().get(0).equals(Col.YELLOW));
        assertTrue(t1.getControlledProfessors().get(1).equals(Col.RED));
        assertTrue(t2.getControlledProfessors().get(1).equals(Col.PINK));
        assertTrue(t2.getControlledProfessors().get(2).equals(Col.BLUE));
        i.updateMotherNature();
        i.calculateOwnership();
        i.updateTeamInfluence(c.getCurrentTeams());
    }

    @Test
    public void testUpdateTeamInfluence2()
    {
        i.teamInfluence[0]=0;
        i.teamInfluence[1]=34;
        i.teamInfluence[2]=5;
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