package model.cards;

import model.boards.Pouch;
import model.boards.token.Student;
import org.junit.Test;
import static org.junit.Assert.*;

public class PrincessTest {
    Princess p=new Princess();
    Pouch po=new Pouch();

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

    @Test
    public void testUpdateStudents()
    {
        p.updateStudents(po);
    }
    @Test
    public void getStudents()
    {
        for(int i=0;i<4;i++)
            p.updateStudents(po);
        assertTrue(p.getStudent(2) instanceof Student);
    }
}
