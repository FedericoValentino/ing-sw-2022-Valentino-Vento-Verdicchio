package model.boards;

import org.junit.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;
import static org.junit.Assert.*;
import model.boards.token.Student;
import model.boards.token.Col;

public class CloudTest {
    Cloud c= new Cloud();
    Cloud c2= new Cloud();
    ArrayList<Student> stud=new ArrayList<Student>();
    Student st1=new Student(Col.GREEN);
    Student st2=new Student(Col.PINK);
    Student st3=new Student(Col.YELLOW);
    ArrayList<Student> stud1=new ArrayList<Student>();

    @Test
    public void testConstructor()
    {
        stud1.add(st1);
        stud1.add(st2);
        Cloud c1=new Cloud(stud1);
        assertEquals(c1.getStudent(1),st2);
    }
    @Test
    public void testEmptyANDPlaceToken()
    {
        Student s= new Student(Col.GREEN);
        assertTrue(c.isEmpty());
        c.placeToken(s);
        assertFalse(c.isEmpty());
    }
    @Test
    public void testGetPos()
    {
        c.placeToken(st1);
        c.placeToken(st2);
        c.placeToken(st3);

        c.getStudent(1);
        assertTrue(c.getStudent(1).equals(st2));
        //now i want to verify the try catch
        assertNull(c.getStudent(70));
    }

    @Test
    public void testRemoveToken()
    {
        c2.placeToken(st1);
        c2.removeToken(st1);
        assertTrue(c2.isEmpty());
        c2.removeToken(st1);
    }

    @Test
    public void testEmptyCloud()
    {
        Cloud c1 = new Cloud();
        Student s1 = new Student(Col.YELLOW);
        Student s2 = new Student(Col.GREEN);
        ArrayList<Student> ss = new ArrayList<>();
        c1.placeToken(s1);
        c1.placeToken(s2);
        ss = c1.EmptyCloud();
        assert(ss.contains(s1));
        assert(ss.contains(s2));
        assert(c1.isEmpty());


    }
}
