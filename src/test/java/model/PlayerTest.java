package model;

import model.boards.School;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.cards.AssistantCard;
import model.cards.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p1=new Player("Giaco", ColTow.WHITE,8,"ca",false);
    Player p3=new Player("paol", ColTow.WHITE,0,"ca",false);
    Player p2=new Player("ci", ColTow.BLACK,8,"cal",true);
    AssistantDeck ad=new AssistantDeck("ca","Giaco");
    AssistantCard ac=new AssistantCard(1,1);


    @Test
    public void testUpdateMaxMotherNatureMovement()
    {
        p1.updateMaxMotherMovement(1);
        assertEquals(p1.getMaxMotherMovement(),1);
    }
    @Test
    public void testUpdateCoins()
    {
        assertEquals(p1.getCoinAmount(),0);
        p1.updateCoins(2);
        assertEquals(p1.getCoinAmount(),2);
    }

    @Test
    public void testGetMaxMotherMovement()
    {
        int i=p2.getMaxMotherMovement();
        p2.getMovementValue();
        assertEquals(i,0);
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
        p1.Discard();
        assertNull(p1.getCurrentAssistantCard());
        assertEquals(a,p1.getLastPlayedCard());
    }
    @Test
    public void testGenericGetter()
    {
        assertTrue(p1.getAssistantDeck() instanceof AssistantDeck);
        assertEquals(p1.getNome(),"Giaco");
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

}
