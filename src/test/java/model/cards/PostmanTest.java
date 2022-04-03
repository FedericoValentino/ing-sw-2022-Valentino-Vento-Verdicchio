package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostmanTest {
    Postman p=new Postman();


    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,1);
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),4);
    }
}
