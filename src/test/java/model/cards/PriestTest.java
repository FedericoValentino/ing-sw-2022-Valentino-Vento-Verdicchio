package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PriestTest {
    Priest p=new Priest();

    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getBaseCost()+1));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),1);
    }
}
