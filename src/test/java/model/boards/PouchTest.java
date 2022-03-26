package model.boards;

import static org.junit.Assert.*;

import model.boards.token.Student;
import org.junit.Test;

public class PouchTest{

    Pouch test = new Pouch();

    @Test
    public void testExtractStudent()
    {
        assertEquals(130, test.getContent().size());
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

    @Test
    public void testGetContent()
    {
    }
}