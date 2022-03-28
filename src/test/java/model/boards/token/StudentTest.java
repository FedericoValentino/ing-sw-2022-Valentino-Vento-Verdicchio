package model.boards.token;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void StudentFunction()
    {
        Student st= new Student(Col.GREEN);
        assertTrue(st.getColor().equals(Col.GREEN));
    }

}
