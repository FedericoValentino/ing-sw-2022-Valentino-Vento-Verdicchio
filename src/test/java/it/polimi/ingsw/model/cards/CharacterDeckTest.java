package it.polimi.ingsw.model.cards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.CurrentGameState;
import org.junit.Test;

public class CharacterDeckTest {
    CharacterDeck cd = new CharacterDeck();
    CharacterCard cc;CharacterCard cc1;

    @Test
    public void testCheckEmpty() {
        assertFalse(cd.getDeck().isEmpty());
    }

    @Test
    public void testDrawCard() {
        cc1=cd.getDeck().get(0);
        cc=cd.drawCard(cc1);
        assertEquals(cc1,cc);
        assertEquals(cc.getBaseCost()+1,cc.getCurrentCost());
    }
}
