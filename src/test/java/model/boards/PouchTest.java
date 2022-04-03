package model.boards;

import static org.junit.Assert.*;
import org.junit.Test;
import model.boards.Pouch;

public class PouchTest{
    Pouch test = new Pouch();

    @Test
    public void testExtractStudent()
    {
        assertEquals(130, test.getContent().size());
        test.extractStudent();
        //da inserire il testing con il gamesetup a false


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