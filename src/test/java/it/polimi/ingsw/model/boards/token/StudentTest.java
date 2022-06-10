package it.polimi.ingsw.model.boards.token;

import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
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
