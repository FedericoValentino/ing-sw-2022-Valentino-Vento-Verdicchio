package model.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeraldTest {
    Herald h=new Herald();

    @Test
    public void testGetUses()
    {
        assertEquals(h.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(h.getBaseCost(),3);
        assertEquals(h.currentCost,h.getBaseCost());
        h.updateCost();
        assertEquals(h.getCurrentCost(),(h.getUses()+h.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(h.getIdCard(),3);
    }
}
