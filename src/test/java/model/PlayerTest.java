package model;

import model.boards.token.ColTow;
import model.cards.AssistantDeck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player p1=new Player("ci", ColTow.WHITE,8,"ca",false);

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
}
