package model.cards;

import org.junit.Test;
import static org.junit.Assert.*;

public class CentaurTest {
    Centaur c=new Centaur();


    @Test
    public void testGetUses()
    {
        assertEquals(c.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(c.getBaseCost(),3);
        assertEquals(c.currentCost,3);
        c.updateCost();
        assertEquals(c.getCurrentCost(),(c.getUses()+c.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(c.getIdCard(),6);
    }
}
