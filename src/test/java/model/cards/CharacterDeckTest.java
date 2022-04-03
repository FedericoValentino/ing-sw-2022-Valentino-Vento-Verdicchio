package model.cards;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharacterDeckTest {
    CharacterDeck cd = new CharacterDeck();
    Priest p=new Priest();

    @Test
    public void testCheckEmpty() {
        assertFalse(cd.checkEmpty());
    }

    @Test
    public void testDrawCard() {
        assertEquals(p,cd.drawCard(p));
        assertEquals(p.baseCost+1,p.currentCost);
    }

    @Test
    public void testGetDeck() {
        //assertEquals(cd,cd.getDeck());
    }

    @Test
    public void testGetCard() {
        assertEquals(p,cd.getCard(p));
    }
}
