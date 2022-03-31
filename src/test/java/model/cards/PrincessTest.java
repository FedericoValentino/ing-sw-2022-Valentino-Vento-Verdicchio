package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PrincessTest {
    Princess p=new Princess();

    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),2);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getBaseCost()+1));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),11);
    }
}
