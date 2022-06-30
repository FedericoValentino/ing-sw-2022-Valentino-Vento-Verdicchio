package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class CurrentGameStateTest {
    CurrentGameState cg1=new CurrentGameState(2,false);
    CurrentGameState cg2=new CurrentGameState(2,true);
    Student studRED =new Student(Col.RED);
    Student studBLUE =new Student(Col.BLUE);

    Player p1=new Player("CiroScapece", ColTow.WHITE,8, Wizard.DRUID,false);
    Player p2=new Player("SparaPeppinoSpara", ColTow.GREY,8,Wizard.WITCH,false);
    HashMap<String, Integer> turn1 =new HashMap<>();


    @Test
    public void testUpdateBankBalance()
    {
        Player p11=new Player("ci", ColTow.WHITE,8,Wizard.SENSEI,true);
        Player p22=new Player("asd", ColTow.BLACK,8,Wizard.LORD,true);

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
        p1.chooseAssistantCard(0);
        assertEquals(p1.getValue(),1);
        p2.chooseAssistantCard(1);
        assertEquals(p2.getValue(),2);

        cg2.getCurrentTeams().get(0).addPlayer(p2);//grey
        cg2.getCurrentTeams().get(1).addPlayer(p1);//white


        cg2.updateTurnState();
        turn1.put(p1.getName(), p1.getValue());
        turn1.put(p2.getName(),p2.getValue());

        assertEquals(cg2.getCurrentTurnState().getTurnOrder().get(2),turn1.get(2));
    }

    /**
     * This method give some students to the players and call the method giveProfessor
     */
    @Test
    public void testGiveProfessors()
    {
        Team t1=new Team(ColTow.WHITE);
        Team t2=new Team(ColTow.GREY);
        cg1.getCurrentTeams().add(t1);
        cg1.getCurrentTeams().add(t2);
        cg1.getCurrentTeams().get(0).addPlayer(p1);
        cg1.getCurrentTeams().get(1).addPlayer(p2);

        //give yellow, red, green student to player 1
        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.YELLOW);
        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().placeInDiningRoom(Col.GREEN);

        //give 3 red students to player 2
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);
        cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().placeInDiningRoom(Col.RED);

        //assert to verify that any professor is given before calling the method
        assertTrue(cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getProfessorTable()[0]);
        assertTrue(cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getProfessorTable()[2]);
        assertTrue(cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().getProfessorTable()[1]);

        cg1.giveProfessors(false);

        //verify the given professors
        assertTrue(cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getProfessorTable()[0]);
        assertTrue(cg1.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getProfessorTable()[2]);
        assertTrue(cg1.getCurrentTeams().get(1).getPlayers().get(0).getSchool().getProfessorTable()[1]);
    }

    @Test
    public void testGetter()
    {
        assertEquals(cg1.getCurrentPouch().getContent().size(),130);
        assertTrue(cg1.getCurrentMotherNature().getPosition()>=0);
        assertFalse(cg2.getCurrentCharacterDeck().getDeck().isEmpty());

        assertEquals(2, cg2.getCurrentClouds().length);
    }
}


