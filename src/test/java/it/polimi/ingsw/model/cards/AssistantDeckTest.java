package it.polimi.ingsw.model.cards;


import static org.junit.Assert.*;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;
import it.polimi.ingsw.model.cards.assistants.AssistantDeck;
import org.junit.Test;

public class AssistantDeckTest{

    AssistantDeck d = new AssistantDeck(Wizard.DRUID);
    AssistantCard a=new AssistantCard(1,1);
    AssistantCard test = new AssistantCard(2, 10);

    @Test
    public void testCheckEmpty()
    {
        assertFalse(d.getDeck().isEmpty());
    }

    @Test
    public void testExtractCard()
    {
       assertEquals(test.getMovement(), d.extractCard(2).getMovement());
    }

    @Test
    public void testGetWizard()
    {
        assertEquals(Wizard.DRUID, d.getWizard());
    }

    @Test
    public void testGetCard()
    {
        assertEquals(a.getMovement(),d.getDeck().get(0).getMovement());
        assertEquals(a.getValue(),d.getDeck().get(0).getValue());
    }

}

