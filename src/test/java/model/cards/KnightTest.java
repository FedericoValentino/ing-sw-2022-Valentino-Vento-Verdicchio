package model.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KnightTest {
    Knight k=new Knight();

    @Test
    public void testUpdateCost()
    {

        assertEquals(k.getBaseCost(),2);
        assertEquals(k.currentCost,k.getBaseCost());
        k.updateCost();
        assertEquals(k.getCurrentCost(),(k.getBaseCost()+1));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(k.getIdCard(),8);
    }
}
