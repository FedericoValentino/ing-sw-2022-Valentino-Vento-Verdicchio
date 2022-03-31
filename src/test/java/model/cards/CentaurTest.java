package model.cards;

import org.junit.Test;
import static org.junit.Assert.*;

public class CentaurTest {
    Centaur c=new Centaur();

    @Test
    public void testUpdateCost()
    {

        assertEquals(c.getBaseCost(),3);
        assertEquals(c.currentCost,3);
        c.updateCost();
        assertEquals(c.getCurrentCost(),4);
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(c.getIdCard(),6);
    }
}
