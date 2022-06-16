package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.Wizard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    CurrentGameState dummy  = new CurrentGameState(2, false);
    Player p1=new Player("Giaco", ColTow.WHITE,8, Wizard.DRUID,false, dummy);
    Player p3=new Player("paol", ColTow.WHITE,0, Wizard.DRUID,false, dummy);
    Player p2=new Player("ci", ColTow.BLACK,8,Wizard.DRUID,true, dummy);
    AssistantDeck ad=new AssistantDeck(Wizard.DRUID,"Giaco");
    AssistantCard ac=new AssistantCard(1,1);


    @Test
    public void testUpdateMaxMotherNatureMovement()
    {
        p1.updateMaxMotherMovement(1);
        assertEquals(p1.getMaxMotherMovement(),1);
    }

    @Test
    public void testChooseAssistantCard()
    {
        p1.chooseAssistantCard(0);
        assertEquals(p1.getCurrentAssistantCard().getMovement(),1);
        assertEquals(p1.getCurrentAssistantCard().getValue(),1);
        assertEquals(p1.getMaxMotherMovement(),1);
        assertEquals(p1.getValue(),1);
    }

    @Test
    public void testGetPlayerSchool()
    {
        assertEquals(p1.getSchool(),p1.school);
    }

    @Test
    public void testDiscard()
    {
        testChooseAssistantCard(); //to inzialize the current card, because I'm lazy
        AssistantCard a=p1.getCurrentAssistantCard();
        p1.discard();
        assertNull(p1.getCurrentAssistantCard());
        assertEquals(a,p1.getLastPlayedCard());
    }
    @Test
    public void testGenericGetter()
    {
        assertTrue(p1.getAssistantDeck() instanceof AssistantDeck);
        assertEquals(p1.getName(),"Giaco");
        assertTrue(p1.isTowerOwner());
    }

    @Test
    public void testGainCoin()
    {

        p1.getSchool().placeInDiningRoom(Col.YELLOW);
        p1.getSchool().placeInDiningRoom(Col.YELLOW);

        assertEquals(0,p1.gainCoin());
        assertEquals(2,p1.getSchool().getRoomCheckpoints()[2]);

        p1.getSchool().placeInDiningRoom(Col.YELLOW);
        p1.getSchool().placeInDiningRoom(Col.YELLOW);
        assertEquals(1,p1.gainCoin());
        assertEquals(5,p1.getSchool().getRoomCheckpoints()[2]);
        p1.getSchool().placeInDiningRoom(Col.YELLOW);
        p1.getSchool().placeInDiningRoom(Col.YELLOW);
        assertEquals(1,p1.gainCoin());
        assertEquals(8,p1.getSchool().getRoomCheckpoints()[2]);

    }


    @Test
    public void testUpdateCoins()
    {
        //first control
        assertEquals(p1.getCoinAmount(),0);
        p1.updateCoins(2);
        assertEquals(p1.getCoinAmount(),2);

        //second control
        p1.updateCoins(-50);
        assertEquals(p1.getCoinAmount(),0);
    }
}
