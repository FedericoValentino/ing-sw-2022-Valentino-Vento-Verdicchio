package model;

import model.boards.token.ColTow;
import model.cards.AssistantCard;
import model.cards.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testGenericGetter()
    {
        assertTrue(p1.getCurrentAssistantCard() instanceof AssistantCard);
        assertTrue(p1.getLastPlayedCard() instanceof AssistantCard);
        assertTrue(p1.getAssistantDeck() instanceof AssistantDeck);


    }

}
