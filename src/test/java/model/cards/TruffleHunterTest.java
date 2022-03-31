package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TruffleHunterTest {
    TruffleHunter t=new TruffleHunter();


    @Test
    public void testGetUses()
    {
        assertEquals(t.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(t.getBaseCost(),3);
        assertEquals(t.currentCost,t.getBaseCost());
        t.updateCost();
        assertEquals(t.getCurrentCost(),(t.getUses()+t.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(t.getIdCard(),9);
    }
}
