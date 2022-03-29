package model.boards.token;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {
    Student st= new Student(Col.GREEN);

    @Test
    public void StudentFunction()
    {
        assertTrue(st.getColor().equals(Col.GREEN));
    }

}
