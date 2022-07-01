package it.polimi.ingsw.model.boards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;

public class PouchTest{
    Pouch pouch = new Pouch();

    /**
     * This method tests the extract student from pouch in setup mode or not (in the 2 cases it draw the student from a
     * different index of the pouch)
     */
    @Test
    public void testExtractStudent()
    {
        assertEquals(130, pouch.getContent().size());
        pouch.extractStudent();
        pouch.updateSetup(false);
        Student s= pouch.getContent().get(0);
        Student s1= pouch.extractStudent();
        assertEquals(s,s1);
    }
    @Test
    public void testCheckEmpty()
    {
        assertFalse(pouch.getContent().isEmpty());
    }
    @Test
    public void testGetSetup()
    {
        assertTrue(pouch.getSetup());
    }
}