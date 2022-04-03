package model.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KnightTest {
    Knight k=new Knight();

    @Test
    public void testGetUses()
    {
        assertEquals(k.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(k.getBaseCost(),2);
        assertEquals(k.currentCost,k.getBaseCost());
        k.updateCost();
        assertEquals(k.getCurrentCost(),(k.getUses()+k.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(k.getIdCard(),8);
    }
}
