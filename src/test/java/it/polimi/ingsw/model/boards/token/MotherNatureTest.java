package it.polimi.ingsw.model.boards.token;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.MotherNature;
import org.junit.Test;
import static org.junit.Assert.*;

public class MotherNatureTest {
    MotherNature mother= new MotherNature();

    /**
     * This method tests the movement of mother nature with different values. (NB: problem: mother nature initial
     * position is random, so to do the test I used a updatePositionAfterMerge that is a function that set the
     * position of MN directly)
     *
     */
    @Test
    public void testMove()
    {
        int value;
        int maxIslands;

        mother.updatePositionAfterMerge(0);

        value=5; maxIslands=6;
        mother.move(value,maxIslands);
        assertEquals(5,mother.getPosition());

        mother.updatePositionAfterMerge(10);
        value=9; maxIslands=11;
        mother.move(value,maxIslands);
        assertEquals(7,mother.getPosition());

        mother.updatePositionAfterMerge(0);
        value=9; maxIslands=9;
        mother.move(value,maxIslands);
        assertEquals(9,mother.getPosition());
    }

    @Test
    public void testGetPosition() {
        assertTrue(mother.getPosition()<=11 && mother.getPosition()>=0);
    }
}



