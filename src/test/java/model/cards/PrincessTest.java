package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PrincessTest {
    Princess p=new Princess();

    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),2);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),11);
    }
}
