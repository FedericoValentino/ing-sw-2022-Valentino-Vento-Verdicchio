package model.boards;

import org.junit.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;
import static org.junit.Assert.*;
import model.boards.token.Student;
import model.boards.token.Col;

public class CloudTest {
    Cloud c= new Cloud();
    ArrayList<Student> stud=new ArrayList<Student>();
    Student st1=new Student(Col.GREEN);
    Student st2=new Student(Col.PINK);
    Student st3=new Student(Col.YELLOW);

    @Test
    public void testEmptyANDPlaceToken()
    {
        Student s= new Student(Col.GREEN);
        assertTrue(c.isEmpty());
        c.placeToken(s);
        assertFalse(c.isEmpty());
    }
    @Test
    public void TestPos()
    {
        c.placeToken(st1);
        c.placeToken(st2);
        c.placeToken(st3);


        for(int i=0;i<stud.size();i++)
        {
            c.getStudent(1);
            assertTrue(c.getStudent(1).equals(st2));
            System.out.println("color st2 : " +st2.getColor());
        }

    }
    
}
