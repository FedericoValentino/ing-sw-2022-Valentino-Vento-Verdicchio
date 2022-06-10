package it.polimi.ingsw.model.boards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;
import it.polimi.ingsw.model.boards.Pouch;

public class PouchTest{
    Pouch test = new Pouch();

    @Test
    public void testExtractStudent()
    {
        assertEquals(130, test.getContent().size());
        test.extractStudent();
        //da inserire il testing con il gamesetup a false
        test.updateSetup(false);
        Student s=test.getContent().get(0);
        Student s1=test.extractStudent();//perché l'index a 0??????
        assertEquals(s,s1);
    }
    @Test
    public void testCheckEmpty()
    {
        assertFalse(test.checkEmpty());
    }
    @Test
    public void testGetSetup()
    {
        assertTrue(test.getSetup());
    }
}