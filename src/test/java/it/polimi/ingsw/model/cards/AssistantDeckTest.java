package it.polimi.ingsw.model.cards;


import static org.junit.Assert.*;

import it.polimi.ingsw.model.boards.token.Wizard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import org.junit.Test;

public class AssistantDeckTest{

    AssistantDeck d = new AssistantDeck(Wizard.DRUID, "Nico");
    AssistantCard a=new AssistantCard(1,1);
    AssistantCard test = new AssistantCard(2, 10);

    @Test
    public void testCheckEmpty()
    {
        assertFalse(d.checkEmpty());
    }

    @Test
    public void testExtractCard()
    {
       assertEquals(test.getMovement(), d.extractCard(2).getMovement());
    }

    @Test
    public void testGetPlayerName()
    {
        assertEquals("Nico", d.getPlayerName());
    }

    @Test
    public void testGetWizard()
    {
        assertEquals(Wizard.DRUID, d.getWizard());
    }

    @Test
    public void testGetCard()
    {
        assertEquals(a.getMovement(),d.getCard(0).getMovement());
        assertEquals(a.getValue(),d.getCard(0).getValue());
    }

}

