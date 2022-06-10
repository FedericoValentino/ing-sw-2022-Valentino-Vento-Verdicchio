package it.polimi.ingsw.model.cards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.CharacterDeck;
import org.junit.Test;

public class CharacterDeckTest {
    CurrentGameState game = new CurrentGameState(2,true);
    CharacterDeck cd = new CharacterDeck(game);
    CharacterCard cc;CharacterCard cc1;

    @Test
    public void testCheckEmpty() {
        assertFalse(cd.checkEmpty());
    }

    @Test
    public void testDrawCard() {
        cc1=cd.getCard(0);
        cc=cd.drawCard(cc1);
        assertEquals(cc1,cc);
        assertEquals(cc.getBaseCost()+1,cc.getCurrentCost());
    }

    @Test
    public void testGetCard() {
        //assertEquals(cc,cd.getCard(0));
    }


}
