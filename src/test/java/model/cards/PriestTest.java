package model.cards;

import model.CurrentGameState;
import model.boards.Pouch;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PriestTest {
    Priest p=new Priest();
    Pouch po=new Pouch();

    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void testIdCard()
    {
        assertEquals(p.getIdCard(),1);
    }

    @Test
    public void testUpdateStudents()
    {
        p.updateStudents(po);//perché aggiungo solo 1 student?
    }
    @Test
    public void testGetStudent()
    {
        //il get student di priest rimuove proprio lo student
    }

}
