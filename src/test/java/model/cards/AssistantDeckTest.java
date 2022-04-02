package model.cards;


import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class AssistantDeckTest{

    AssistantDeck deck = new AssistantDeck("Giovanni", "Nico");

    @Test
    public void testCheckEmpty()
    {
        assertFalse(deck.checkEmpty());
    }

    @Test
    public void testExtractCard()
    {
       AssistantCard test = new AssistantCard(2, 10);
       assertEquals(test.getMovement(), deck.extractCard(2).getMovement());
    }

    @Test
    public void testGetPlayerName()
    {
        assertEquals("Nico", deck.getPlayerName());
    }

    @Test
    public void testGetWizard()
    {
        assertEquals("Giovanni", deck.getWizard());
    }

    @Test
    public void testGetDeck()
    {
        assertArrayEquals(deck, deck.getDeck());
    }

    private void assertArrayEquals(AssistantDeck deck, ArrayList<AssistantCard> deck1) {
    }
}

