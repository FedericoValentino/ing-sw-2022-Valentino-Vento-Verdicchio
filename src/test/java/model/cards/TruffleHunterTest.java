package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TruffleHunterTest {
    TruffleHunter t=new TruffleHunter();

    @Test
    public void testUpdateCost()
    {

        assertEquals(t.getBaseCost(),3);
        assertEquals(t.currentCost,t.getBaseCost());
        t.updateCost();
        assertEquals(t.getCurrentCost(),(t.getBaseCost()+1));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(t.getIdCard(),9);
    }
}
