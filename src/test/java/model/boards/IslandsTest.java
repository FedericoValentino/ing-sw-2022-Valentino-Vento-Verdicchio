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

public class IslandsTest {
    Islands is=new Islands();
    Islands is2=new Islands();
    Student s1=new Student(Col.GREEN);
    Student s12=new Student(Col.RED);
    Student s2=new Student(Col.PINK);
    Student s3=new Student(Col.BLUE);
    Student s4=new Student(Col.YELLOW);


    @Test
    public void testInizializationIsland()
    {
        assertEquals(is.getIslands().get(0).getIslandId(),0);
    }

    //place token basic with 4 groups
    @Test
    public void testPlaceToken1()
    {
        is.getIslands().get(0).currentStudents.add(s1);//green

        is.getIslands().get(1).currentStudents.add(s2);//pink
        is.getIslands().get(3).currentStudents.add(s2);//pink
        is.getIslands().get(2).currentStudents.add(s2);//pink

        is.getIslands().get(4).currentStudents.add(s4);
        is.getIslands().get(5).currentStudents.add(s4);//yellow
        is.getIslands().get(6).currentStudents.add(s4);

        is.getIslands().get(7).currentStudents.add(s2);//pink
        is.getIslands().get(10).currentStudents.add(s2);//pink
        is.getIslands().get(11).currentStudents.add(s2);//pink

    }

    //test with 0 groups because the student are different
    public void testPlaceToken2()
    {
        is2.placeToken(s1,0);
        assertEquals(is2.getIslands().get(0).getCurrentStudents().get(0),s1);
        is2.placeToken(s2,0);
        assertEquals(is2.getIslands().get(0).getCurrentStudents().get(1),s2);
        is2.placeToken(s1,0);



        is2.getIslands().get(0).currentStudents.add(s1);//green p1
        is2.getIslands().get(1).currentStudents.add(s12);//red p2
        is2.getIslands().get(2).currentStudents.add(s4);//yellow p1
        is2.getIslands().get(3).currentStudents.add(s2);//pink p2
        is2.getIslands().get(4).currentStudents.add(s3);//blue p3
        is2.getIslands().get(5).currentStudents.add(s1);//green p1
        is2.getIslands().get(6).currentStudents.add(s12);//red p2
        is2.getIslands().get(7).currentStudents.add(s4);//yellow p1
        is2.getIslands().get(8).currentStudents.add(s2);//pink p2
        is2.getIslands().get(9).currentStudents.add(s3);//blue p3
        is2.getIslands().get(10).currentStudents.add(s1);//green p1
        is2.getIslands().get(11).currentStudents.add(s12);//red p2

    }

    public void testIdManagmentCase1(Team t1,Team t2,Player p1,Player p2, CurrentGameState c) {


        testPlaceToken1();// I put the students in the islands

        t1.addPlayer(p1);
        t2.addPlayer(p2);
        t1.updateProfessors();
        assertEquals(t1.getControlledProfessors().size(),0);

        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof
        p1.getSchool().updateProfessorsTable(3,true);//pink
        t1.updateProfessors();
        t2.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        for(int i=0;i<12;i++)
        {
            is.getIslands().get(i).calculateOwnership();
            is.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }


        assertEquals(is.getTotalGroups(),12);

        //calling the management function
        is.idManagement();
        assertEquals(is.getTotalGroups(),4);

        //adding a new student to reload idManagement (reducing to 3 groups)
        is.getIslands().get(0).currentStudents.add(s3);
        for(int i=0;i<is.getTotalGroups();i++)
        {
            is.getIslands().get(i).updateMotherNature();
            is.getIslands().get(i).calculateOwnership();
            is.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }
        is.idManagement();
        //assertEquals(is.getTotalGroups(),3);

    }

    public void testIdManagmentCase2(Team t1,Team t2,Player p1,Player p2, CurrentGameState c, Player p3, Team t3) {
        testPlaceToken2();// I put the students in the islands

        t1.addPlayer(p1);
        t2.addPlayer(p2);
        t3.addPlayer(p3);

        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(1,true);//red prof
        p1.getSchool().updateProfessorsTable(2,true);//yellow
        p2.getSchool().updateProfessorsTable(3,true);//pink prof
        p3.getSchool().updateProfessorsTable(4,true);//blue prof
        t1.updateProfessors();
        t2.updateProfessors();
        t3.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        c.getCurrentTeams().add(t3);
        assertEquals(is2.getTotalGroups(),12);
        for(int i=0;i<is2.getTotalGroups();i++)
        {
            is2.getIslands().get(i).calculateOwnership();
            System.out.println("Influence : "+i+" "+is2.getIslands().get(i).getOwnership());
            is2.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }



        is2.idManagement();
        System.out.println(is2.getTotalGroups());

        is2.idManagement();
        System.out.println(is2.getTotalGroups());

        assertEquals(is2.getTotalGroups(),4); //becauase


    }

    @Test
    public void testIdManagment() {
        Team t1=new Team(ColTow.BLACK);
        Team t2=new Team(ColTow.GREY);
        Team t3=new Team(ColTow.WHITE);
        CurrentGameState c=new CurrentGameState(2,false);
        Player p1=new Player("ci", ColTow.BLACK,8,"ca",false);
        Player p2=new Player("asd", ColTow.GREY,8,"caadsds",false);
        Player p3=new Player("adsd", ColTow.WHITE,8,"caaddassds",false);

        testIdManagmentCase1(t1,t2,p1,p2,c); // caso1 testing ez
        testIdManagmentCase2(t1,t2,p1,p2,c,p3,t3); //caso 2 un po' più elaborato

        }
    @Test
    public void testTotalGroups()
    {
        //basic test at the beginning
        assertEquals(is.getTotalGroups(),12);
    }

    @Test
    public void testGetMax()
    {
        testIdManagment();//because it has all the inizialization

        ColTow c=is2.getMaxCol();   // here it will call the island
                                    //linked to the second test
        assertEquals(ColTow.GREY,c);
    }
}
