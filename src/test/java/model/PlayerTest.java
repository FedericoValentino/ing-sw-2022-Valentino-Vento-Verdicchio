package model;

import model.boards.School;
import model.boards.token.ColTow;
import model.cards.AssistantCard;
import model.cards.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p1=new Player("Giaco", ColTow.WHITE,8,"ca",false);
    Player p2=new Player("ci", ColTow.BLACK,8,"cal",true);
    AssistantDeck ad=new AssistantDeck("ca","Giaco");
    AssistantCard ac=new AssistantCard(1,1);

    @Test
    public void test()
    {
        AssistantDeck as=new AssistantDeck("ca","GIova");

    }

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
        assertEquals(ad.getCard(0).getValue(),1);
        assertEquals(ad.getCard(0).getMovement(),1);
    }

    @Test
    public void testGetPlayerSchool()
    {
        assertEquals(p1.getSchool(),p1.school);
    }

    /*@Test
    public void testDiscard()
    {
        AssistantCard a1=new AssistantCard(1,1);
        AssistantCard a2=new AssistantCard(1,1);

        p1.chooseAssistantCard(1);
        a1=p1.getCurrentAssistantCard();
        AssistantCard aTemp=p1.getCurrentAssistantCard();
        a2=p1.getLastPlayedCard();
        p1.Discard();
        assertEquals(a2.getMovement(),aTemp.getMovement());
        assertEquals(a2.getMovement(),aTemp.getValue());
        /*da aggiungere quando fede gestirà il problema del null sulla
        lastCardUsed in Discard

        assertNull(a1);
    }
    */
    public void testGenericGetter()
    {
        /*assertTrue(p1.getCurrentAssistantCard() instanceof AssistantCard);*/
        //assertTrue(p1.getLastPlayedCard() instanceof AssistantCard);
        assertTrue(p1.getAssistantDeck() instanceof AssistantDeck);
        assertTrue(p1.getSchool() instanceof School);
        assertTrue(p1.getNome() instanceof String);
    }

}
