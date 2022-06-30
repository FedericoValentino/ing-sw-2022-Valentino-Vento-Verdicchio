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

public class IslandsTest {
    CurrentGameState dummy = new CurrentGameState(2, false);
    Islands islands1;
    Islands islands2;
    Islands islands3;

    Student studGREEN;
    Student studRED;
    Student studPINK;
    Student studBLUE;
    Student studYELLOW;

    Team t1;
    Team t2;
    Team t3;
    CurrentGameState c;
    Player p1;
    Player p2;
    Player p3;


    public void initialization() {
        islands1 =new Islands(dummy);
        islands2 =new Islands(dummy);
        islands3 =new Islands(dummy);

        studGREEN =new Student(Col.GREEN);
        studRED =new Student(Col.RED);
        studPINK =new Student(Col.PINK);
        studBLUE =new Student(Col.BLUE);
        studYELLOW =new Student(Col.YELLOW);

        t1=new Team(ColTow.GREY);
        t2=new Team(ColTow.WHITE);
        t3=new Team(ColTow.BLACK);
        c=new CurrentGameState(2,false);
        p1=new Player("Kojima", ColTow.GREY,8,Wizard.SENSEI, false);
        p2=new Player("Hayao_Nakayama", ColTow.WHITE,8,Wizard.LORD,false);
        p3=new Player("miyazaki", ColTow.BLACK,8, Wizard.WITCH,false);

        t1.addPlayer(p1);
        t2.addPlayer(p2);
        t1.updateProfessors();
        t2.updateProfessors();
        assertEquals(t1.getControlledProfessors().size(),0);
    }

    //place token in 4 groups
    public void initPlaceToken1()
    {
        islands1.getIslands().get(0).getCurrentStudents().add(studGREEN);//green

        islands1.getIslands().get(1).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(2).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(3).getCurrentStudents().add(studPINK);//pink

        islands1.getIslands().get(4).getCurrentStudents().add(studYELLOW);//yellow
        islands1.getIslands().get(5).getCurrentStudents().add(studYELLOW);//yellow
        islands1.getIslands().get(6).getCurrentStudents().add(studYELLOW);//yellow

        islands1.getIslands().get(7).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(8).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(9).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(10).getCurrentStudents().add(studPINK);//pink
        islands1.getIslands().get(11).getCurrentStudents().add(studPINK);//pink
    }

    /**
     * This is a method in which won't be any type of merging because the islands has different owners
     */
    public void initPlaceToken2()
    {
        islands2.getIslands().get(0).getCurrentStudents().add(studGREEN);
        islands2.getIslands().get(1).getCurrentStudents().add(studRED);
        islands2.getIslands().get(2).getCurrentStudents().add(studYELLOW);
        islands2.getIslands().get(3).getCurrentStudents().add(studPINK);
        islands2.getIslands().get(4).getCurrentStudents().add(studBLUE);
        islands2.getIslands().get(5).getCurrentStudents().add(studGREEN);
        islands2.getIslands().get(6).getCurrentStudents().add(studRED);
        islands2.getIslands().get(7).getCurrentStudents().add(studYELLOW);
        islands2.getIslands().get(8).getCurrentStudents().add(studPINK);
        islands2.getIslands().get(9).getCurrentStudents().add(studBLUE);
        islands2.getIslands().get(10).getCurrentStudents().add(studGREEN);
        islands2.getIslands().get(11).getCurrentStudents().add(studRED);
    }

    public void initPlaceToken3()
    {
        islands3.getIslands().get(0).getCurrentStudents().add(studGREEN);//green  black

        islands3.getIslands().get(1).getCurrentStudents().add(studPINK);//pink   grey
        islands3.getIslands().get(3).getCurrentStudents().add(studPINK);//pink
        islands3.getIslands().get(2).getCurrentStudents().add(studPINK);//pink

        islands3.getIslands().get(4).getCurrentStudents().add(studYELLOW);//yellow  white

        islands3.getIslands().get(5).getCurrentStudents().add(studBLUE);

        islands3.getIslands().get(6).getCurrentStudents().add(studYELLOW);

        islands3.getIslands().get(7).getCurrentStudents().add(studPINK);//pink   grey
        islands3.getIslands().get(8).getCurrentStudents().add(studPINK);//pink
        islands3.getIslands().get(9).getCurrentStudents().add(studPINK);//pink
        islands3.getIslands().get(10).getCurrentStudents().add(studPINK);//pink
        islands3.getIslands().get(11).getCurrentStudents().add(studPINK);//pink
    }

    /**
     * This method test a simple case in which it has initially 10 groups, then it adds the pink professor
     * to the player 1 and the group become 2
     */
    @Test
    public void testIdManagmentCase1() {

        initialization();
        initPlaceToken1();// I put the students in the islands


        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof
        t1.updateProfessors();
        t2.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        for(int i=0;i<12;i++) {
            islands1.getIslands().get(i).calculateOwnership();
            islands1.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }

        //calling the management function, but it can't operate because I've done calculate Ownerhip before
        //the update team influence
        islands1.idManagement();
        assertEquals(islands1.getTotalGroups(),12);

        //merging the island in positions 4,5,6 ( so remain 12-3+1= 10 groups)
        for(int i = 0; i< islands1.getTotalGroups(); i++)
        {
            islands1.getIslands().get(i).updateMotherNature();
            islands1.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
            islands1.getIslands().get(i).calculateOwnership();
        }
        islands1.idManagement();
        assertEquals(10, islands1.getTotalGroups());

        //now it adds the pink professor to the p1
        p1.getSchool().updateProfessorsTable(3,true);//pink prof
        t1.updateProfessors();
        for(int i = 0; i< islands1.getTotalGroups(); i++)
        {
            islands1.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
            islands1.getIslands().get(i).calculateOwnership();
        }
        islands1.idManagement();
        assertEquals(2, islands1.getTotalGroups());
    }

    /**
     * In this method: firstly, it tests the placeToken functionality (removing the added student at the end of the
     *                      initial part of the test)
     *                 secondly, it tests multiple calls of the idManagement (and it must not change anything)
     *                 thirdly, it tests a call without ever calculate the ownership, and it won't run
     */
    @Test
    public void testIdManagmentCase2() {
        initialization();
        initPlaceToken2();// I put the students in the islands

        islands2.placeToken(studGREEN,0);
        assertEquals(islands2.getIslands().get(0).getCurrentStudents().get(0).getColor(), studGREEN.getColor());

        islands2.placeToken(studPINK,1);
        assertEquals(islands2.getIslands().get(1).getCurrentStudents().get(0).getColor(), studRED.getColor());
        assertEquals(islands2.getIslands().get(1).getCurrentStudents().get(1).getColor(), studPINK.getColor());

        islands2.placeToken(studGREEN,2);
        assertEquals(islands2.getIslands().get(2).getCurrentStudents().get(0).getColor(), studYELLOW.getColor());
        assertEquals(islands2.getIslands().get(2).getCurrentStudents().get(1).getColor(), studGREEN.getColor());

        //now I remove all the previous placing
        islands2.getIslands().get(0).getCurrentStudents().remove(0);
        islands2.getIslands().get(1).getCurrentStudents().remove(0);
        islands2.getIslands().get(2).getCurrentStudents().remove(0);
        assertEquals(islands2.getIslands().get(0).getCurrentStudents().size(),1);
        assertEquals(islands2.getIslands().get(1).getCurrentStudents().size(),1);
        assertEquals(islands2.getIslands().get(2).getCurrentStudents().size(),1);



        //Initializing the professors and all the other things
        t1.addPlayer(p1);//white
        t2.addPlayer(p2);//grey
        t3.addPlayer(p3);//black

        //reset the professors table of testIdManagmentCase1 becuase they have player in common
        for(int i=0;i<5;i++)
        {
            p1.getSchool().updateProfessorsTable(i,false);//green prof
            p2.getSchool().updateProfessorsTable(i,false);//green prof
        }

        p1.getSchool().updateProfessorsTable(0,true);//green prof  white
        p2.getSchool().updateProfessorsTable(1,true);//red prof    grey
        p1.getSchool().updateProfessorsTable(2,true);//yellow      white
        p2.getSchool().updateProfessorsTable(3,true);//pink prof   grey
        p3.getSchool().updateProfessorsTable(4,true);//blue prof   black

        t1.updateProfessors();
        t2.updateProfessors();
        t3.updateProfessors();

        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        c.getCurrentTeams().add(t3);
        assertEquals(islands2.getTotalGroups(),12);

        for(int i = 0; i< islands2.getTotalGroups(); i++)
        {
            islands2.getIslands().get(i).calculateOwnership();
            islands2.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }
        islands2.idManagement();
        assertEquals(12, islands2.getTotalGroups());

        //Retrying merging without any changes to see if there are some problems
        islands2.idManagement();
        assertEquals(12, islands2.getTotalGroups());
        //because I don't have mother nature= true, so it doesn't change anything


        //don't do anything because I don't have already updated the ownership
        islands2.idManagement();

        //now it adds some student to start merging but it doesn't have mother nature so it won't merge
        islands2.placeToken(studGREEN,1);
        islands2.placeToken(studGREEN,1);
        islands2.placeToken(studPINK,6);
        islands2.placeToken(studPINK,6);

        for(int i = 0; i< islands2.getTotalGroups(); i++) {
            islands1.getIslands().get(i).calculateOwnership();
            islands2.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }
        islands2.idManagement();
        assertEquals(12, islands2.getTotalGroups());
    }

    /**
     *In this method we do a simple merging without any
     */
    @Test
    public void testIdManagmentCase3() {
        initialization();
        initPlaceToken3();

        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof
        p3.getSchool().updateProfessorsTable(3,true);//pink prof
        t3.addPlayer(p3);
        t1.updateProfessors();
        t2.updateProfessors();
        t3.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        c.getCurrentTeams().add(t3);

        for(int i=0;i<12;i++) {
            islands3.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
            islands3.getIslands().get(i).updateMotherNature();
            islands3.getIslands().get(i).calculateOwnership();
        }
        //before merging are all initialized and with the right ownership
        assertEquals(12, islands3.getTotalGroups());

        islands3.idManagement();
        assertEquals(6, islands3.getTotalGroups());
}

    @Test
    public void testTotalGroups()
    {
        initialization();
        initPlaceToken1();
        //basic test at the beginning
        assertEquals(islands1.getTotalGroups(),12);
    }


    /**
     * This method use the testIdManagement to give an example of calculate ownership
     */
    @Test
    public void testGetMax()
    {
        initialization();
        testIdManagmentCase1();
        assertEquals(ColTow.GREY, islands1.getMaxCol(c.getCurrentTeams()));
    }
}
