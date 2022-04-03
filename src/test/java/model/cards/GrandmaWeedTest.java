package model.cards;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GrandmaWeedTest {
    GrandmaWeed g=new GrandmaWeed();


    @Test
    public void testGetUses()
    {
        assertEquals(g.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {
        Assert.assertEquals(g.getBaseCost(),2);
        Assert.assertEquals(g.currentCost,g.getBaseCost());
        g.updateCost();
        assertEquals(g.getCurrentCost(),(g.getUses()+g.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        Assert.assertEquals(g.getIdCard(),5);
    }

    @Test
    public void checkGetUses()
    {
        assertEquals(g.getNoEntry(),0);
    }
}
