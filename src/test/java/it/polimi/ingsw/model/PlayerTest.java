package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;
import it.polimi.ingsw.model.cards.assistants.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p1=new Player("Giaco", ColTow.WHITE,8, Wizard.DRUID,false);
    Player p3=new Player("paol", ColTow.WHITE,0, Wizard.DRUID,false);
    Player p2=new Player("ci", ColTow.BLACK,8,Wizard.DRUID,true);
    AssistantDeck ad=new AssistantDeck(Wizard.DRUID);
    AssistantCard ac=new AssistantCard(1,1);


    @Test
    public void testUpdateMaxMotherNatureMovement()
    {
        p1.updateMaxMotherMovement(1);
        assertEquals(p1.getMaxMotherMovement(),1);
    }

    /**
     * This method tests if the chooseAssistantCard it's correct: choose the card 1 that has value=1 and movement = 1
     */
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

    /**
     * This method try to pick up a card using the choosing method and then verify that current and last card are updated
     */
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
        assertEquals(p1.getName(),"Giaco");
        assertTrue(p1.isTowerOwner());
    }

    /**
     * This method place 2 students in the dining room and verify that the player doesn't gain a coin.
     * Then it inserts other 2 students and verify that gain only 1 coin.
     * Then it inserts other 2 students and verify that gain only 2 coin.
     */
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


    /**
     * This method verify the proper behaviour of updateCoin, also testing the case in which it ties to add a negative
     * value of coins
     */
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
