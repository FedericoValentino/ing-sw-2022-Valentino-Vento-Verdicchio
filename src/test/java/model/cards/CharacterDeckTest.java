package model.cards;

import static org.junit.Assert.*;

import model.CurrentGameState;
import org.junit.Test;

public class CharacterDeckTest {
    CharacterDeck cd = new CharacterDeck();
    CharacterCard cc;CharacterCard cc1;

    @Test
    public void testCheckEmpty() {
        assertFalse(cd.checkEmpty());
    }

    @Test
    public void testDrawCard() {
        cc1=cd.getCard(0);
        cc=cd.drawCard(0);
        assertEquals(cc1,cc);
        assertEquals(cc.getBaseCost()+1,cc.getCurrentCost());
    }

    @Test
    public void testGetCard() {
        //assertEquals(cc,cd.getCard(0));
    }


}
