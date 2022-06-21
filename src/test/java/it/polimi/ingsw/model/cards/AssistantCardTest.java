package it.polimi.ingsw.model.cards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.cards.assistants.AssistantCard;
import org.junit.Test;


public class AssistantCardTest {

    AssistantCard test = new AssistantCard(5, 9);

    @Test
    public void TestGetMovement()
    {
        assertEquals(5, test.getMovement());
    }

    @Test
    public void TestGetValue()
    {
        assertEquals(9, test.getValue());
    }

}