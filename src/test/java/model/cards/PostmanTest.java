package model.cards;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostmanTest {
    Postman p=new Postman();

    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,1);
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getBaseCost()+1));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),4);
    }
}
