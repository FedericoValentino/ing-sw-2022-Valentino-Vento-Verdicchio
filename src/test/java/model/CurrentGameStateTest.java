package model;

import model.boards.Islands;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.boards.token.ColTow;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class CurrentGameStateTest {
    CurrentGameState cg1=new CurrentGameState(2,false);
    CurrentGameState cg2=new CurrentGameState(2,true);
    CurrentGameState cg3=new CurrentGameState(3,false);
    Student s2=new Student(Col.RED);
    Student s5=new Student(Col.BLUE);

    Player p1=new Player("ci", ColTow.WHITE,8,"ca",false);
    Player p2=new Player("asd", ColTow.GREY,8,"caadsds",false);
    HashMap<String, Integer> tO=new HashMap<>();
    HashMap<String, Integer> tOO=new HashMap<>();


    @Test
    public void testUpdateBankBalance()
    {
        Player p11=new Player("ci", ColTow.WHITE,8,"ca",true);
        Player p22=new Player("asd", ColTow.BLACK,8,"caadsds",true);

        int i=p1.getCoinAmount();
        cg1.getCurrentTeams().get(0).addPlayer(p11);
        cg1.getCurrentTeams().get(1).addPlayer(p22);
        assertEquals(cg1.getBankBalance(),0);
        cg1.updateBankBalance(p1, 0);
        assertEquals(cg1.getBankBalance(),0);

        //else branch
        cg1.updateBankBalance(p11,-222);
        assertEquals(cg1.getBankBalance(),0);

    }

    @Test
    public void testUpdateTurnState()
    {
        cg2.getCurrentTeams().get(0).addPlayer(p2);//grey
        cg2.getCurrentTeams().get(1).addPlayer(p1);//white
        p1.chooseAssistantCard(0);
        p2.chooseAssistantCard(1);
        cg2.updateTurnState();
        tO.put(p1.getNome(), p1.getValue());
        tOO.put(p2.getNome(),p2.getValue());

        //TO DO risolvere questo
        //assertEquals(cg2.getCurrentTurnState().getTurnOrder().get(0),tO);
    }

    //this is a service class used in 3° test of testCheckWinner
public void testPlaceToken1()
   {
        cg2.getCurrentIslands().getIslands().get(0).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(1).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(2).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(3).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(4).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(5).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(6).currentStudents.add(s5);
        cg2.getCurrentIslands().getIslands().get(7).currentStudents.add(s5);
        cg2.getCurrentIslands().getIslands().get(8).currentStudents.add(s5);

        cg2.getCurrentIslands().getIslands().get(9).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(10).currentStudents.add(s2);
        cg2.getCurrentIslands().getIslands().get(11).currentStudents.add(s2);

        p1.getSchool().updateProfessorsTable(1,true);//red prof
        p2.getSchool().updateProfessorsTable(4,true);//blue prof
        cg2.getCurrentTeams().get(0).updateProfessors();//team p2 grey
        cg2.getCurrentTeams().get(1).updateProfessors();//team p1 black

        for(int i=0;i<cg2.getCurrentIslands().getTotalGroups();i++)
        {
            cg2.getCurrentIslands().getIslands().get(i).updateTeamInfluence(cg2.getCurrentTeams());
            cg2.getCurrentIslands().getIslands().get(i).updateMotherNature();
            cg2.getCurrentIslands().getIslands().get(i).calculateOwnership();
            cg2.getCurrentIslands().getIslands().get(i).updateTeamInfluence(cg2.getCurrentTeams());
        }
        cg2.getCurrentIslands().idManagement();
        assertEquals(2, cg2.getCurrentIslands().getTotalGroups());
    }

    @Test
    public void testCheckWinner()
    {
        //checking the first if
        testUpdateTurnState();
        p1.getSchool().updateTowerCount(-8);
        cg2.checkWinner();
        p1.isTowerOwner();
        assertEquals(ColTow.WHITE,cg2.getCurrentTurnState().getWinningTeam());
        //checking the second if
        while(!cg2.getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().isEmpty())
        {
            cg2.getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
            cg2.getCurrentTeams().get(0).getPlayers().get(0).Discard();
        }
        assertTrue(cg2.getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().checkEmpty());
        cg2.checkWinner();
        assertTrue(cg2.getCurrentTurnState().lastTurn);

        //checking the 3° if total groups<=3
        testPlaceToken1();
        p1.getSchool().updateTowerCount(8);
        cg2.checkWinner();

        //checking the 4° if pouch empty
        // while(!cg3.getCurrentPouch().checkEmpty())
        cg3.getCurrentPouch().updateSetup(false);
        while(!cg3.getCurrentPouch().checkEmpty())
            cg3.getCurrentPouch().extractStudent();
        assertTrue(cg3.getCurrentPouch().checkEmpty());
        cg3.checkWinner();
        assertTrue(cg3.getCurrentTurnState().lastTurn);
    }

    @Test
    public void testGiveProfessors()
    {
        Team t1=new Team(ColTow.WHITE);
        Team t2=new Team(ColTow.GREY);
        cg1.getCurrentTeams().add(t1);
        cg1.getCurrentTeams().add(t2);
        cg1.getCurrentTeams().get(0).addPlayer(p1);
        cg1.getCurrentTeams().get(1).addPlayer(p2);
        cg1.getCurrentTeams().get(0).getPlayers().get(0);

        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.YELLOW);
        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.GREEN);
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.giveProfessors();
        /*
         System.out.println(cg1.getCurrentTeams().get(0).getControlledProfessors());
         System.out.println(cg1.getCurrentTeams().get(1).getControlledProfessors());

         assertTrue(cg1.getCurrentTeams().get(0).getControlledProfessors().contains(Col.GREEN));
         assertTrue(cg1.getCurrentTeams().get(0).getControlledProfessors().contains(Col.YELLOW));
         assertTrue(cg1.getCurrentTeams().get(1).getControlledProfessors().contains(Col.RED));
         */
    }

    @Test
    public void testGetter()
    {

        assertEquals(cg1.getCurrentPouch().getContent().size(),130);
        assertTrue(cg1.getCurrentMotherNature().getPosition()>=0);
        assertTrue(!cg1.getCurrentCharacterDeck().checkEmpty());

        assertTrue(cg2.getCurrentClouds().length==2);
        //da rivedere, perché questa add va messa  in una funzione dedicata nel model (problema discusso)
        //assertEquals(,cg1.getCurrentActiveCharacterCard());
        // cg1.getCurrentActiveCharacterCard().add(cg1.getCurrentCharacterDeck().drawCard(0));
    }
}


