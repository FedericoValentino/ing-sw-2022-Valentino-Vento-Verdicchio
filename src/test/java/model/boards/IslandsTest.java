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
    Student s1=new Student(Col.GREEN);
    Student s2=new Student(Col.PINK);
    Student s3=new Student(Col.BLUE);
    Student s4=new Student(Col.YELLOW);


    @Test
    public void testInizializationIsland()
    {
        assertEquals(is.getIslands().get(0).getIslandId(),0);
    }

    @Test
    public void testPlaceToken()
    {
        is.placeToken(s1,0);
        assertEquals(is.getIslands().get(0).getCurrentStudents().get(0),s1);
        is.placeToken(s2,0);
        assertEquals(is.getIslands().get(0).getCurrentStudents().get(1),s2);
        is.placeToken(s2,0);
        is.placeToken(s2,0);
        is.placeToken(s3,0);
        is.placeToken(s4,0);
        is.placeToken(s3,0);
    }

    @Test
    public void testIdManagment() {
        Team t1=new Team(ColTow.BLACK);
        Team t2=new Team(ColTow.GREY);
        CurrentGameState c=new CurrentGameState(2,false);
        Player p1=new Player("ci", ColTow.BLACK,8,"ca",false);
        Player p2=new Player("asd", ColTow.GREY,8,"caadsds",false);

        is.getIslands().get(0).currentStudents.add(s1);
        is.getIslands().get(1).currentStudents.add(s2);
        is.getIslands().get(3).currentStudents.add(s2);
        is.getIslands().get(2).currentStudents.add(s2);
        is.getIslands().get(0).currentStudents.add(s3);
        is.getIslands().get(0).currentStudents.add(s4);
        is.getIslands().get(10).currentStudents.add(s1);

        t1.addPlayer(p1);
        t2.addPlayer(p2);
        t1.updateProfessors();
        assertEquals(t1.getControlledProfessors().size(),0);

        p1.getSchool().updateProfessorsTable(0,true);//green prof
        p2.getSchool().updateProfessorsTable(2,true);//yellow prof

        t1.updateProfessors();
        t2.updateProfessors();
        c.getCurrentTeams().add(t1);
        c.getCurrentTeams().add(t2);
        for(int i=0;i<12;i++)
        {
            is.getIslands().get(i).calculateOwnership();
            is.getIslands().get(i).updateTeamInfluence(c.getCurrentTeams());
        }
        is.idManagement();
        is.getIslands().get(0).updateMotherNature();
        assertEquals(is.getTotalGroups(),12);
        is.idManagement();
        //assertEquals(is.getTotalGroups(),11); da mettere valida
        //appena fede updata il numero totali di gruppi in id managment
    }
    @Test
    public void testTotalGroups()
    {
        //da inserire questo test nel test di management delle isole
        assertEquals(is.getTotalGroups(),12);
    }

    @Test
    public void testGetMax()
    {
        testIdManagment();//lo chiamo perché ha tutta l'inizializzazione del game
        ColTow c=is.getMaxCol();

    }
}
