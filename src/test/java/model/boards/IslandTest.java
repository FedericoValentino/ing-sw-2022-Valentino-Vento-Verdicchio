package model.boards;

import model.boards.token.Col;
import model.boards.token.Student;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class IslandTest {
    Island i=new Island(0);
    Student s=new Student(Col.GREEN);

    @Test
    public void testInizializationIsland()
    {
        assertEquals(i.getIslandId(),0);
        assertFalse(i.getMotherNature());
        assertFalse(i.getGroup());
        assertEquals(i.getTowerNumber(),0);
        assertNull(i.getOwnership());
        assertFalse(i.getNoEntry());
        assertEquals(i.teamInfluence,i.getTeamInfluence());
    }
    @Test
    public void testAddStudent()
    {
        assertEquals(i.getCurrentStudents().size(),0);
        i.addStudent(s);
        assertEquals(i.getCurrentStudents().size(),1);
        assertEquals(i.currentStudents.get(0),s);
    }


    @Test
    public void testCalculateOwnership()
    {

    }
}