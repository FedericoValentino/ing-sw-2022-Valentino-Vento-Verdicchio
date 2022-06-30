package it.polimi.ingsw.model.boards;

import it.polimi.ingsw.model.CurrentGameState;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

public class  CloudTest {
    Cloud c= new Cloud(1);
    Cloud c2= new Cloud( 2);
    ArrayList<Student> stud=new ArrayList<>();
    Student st1=new Student(Col.GREEN);
    Student st2=new Student(Col.PINK);
    Student st3=new Student(Col.YELLOW);
    ArrayList<Student> stud1=new ArrayList<>();

    /**
     * This method test the place token functionality.
     */
    @Test
    public void testEmptyANDPlaceToken()
    {
        Student s= new Student(Col.GREEN);
        assertTrue(c.getStudents().isEmpty());
        c.placeToken(s);
        assertFalse(c.getStudents().isEmpty());
    }

    /**
     * This method test the getter of the student in a cloud
     */
    @Test
    public void testGetPos()
    {
        c.placeToken(st1);
        c.placeToken(st2);
        c.placeToken(st3);

        Student student = c.getStudents().get(1);
        assertEquals(student.getColor(), st2.getColor());
    }

    @Test
    public void testRemoveToken()
    {
        c2.placeToken(st1);
        c2.removeToken(st1);
        assertTrue(c2.getStudents().isEmpty());
    }

    @Test
    public void testEmptyCloud()
    {
        Cloud c1 = new Cloud(1);
        Student s1 = new Student(Col.YELLOW);
        Student s2 = new Student(Col.GREEN);
        ArrayList<Student> studentArrayList;
        c1.placeToken(s1);
        c1.placeToken(s2);

        studentArrayList = c1.EmptyCloud();

        assert(studentArrayList.contains(s1));
        assert(studentArrayList.contains(s2));
        assert(c1.getStudents().isEmpty());
    }
}
