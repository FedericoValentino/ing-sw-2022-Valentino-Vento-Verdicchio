package model.boards.token;

import org.junit.Test;
import static org.junit.Assert.*;

public class MotherNatureTest {
    MotherNature mother= new MotherNature();

    @Test
    public void testMove()
    {
        mother.move(123,13);

        mother.move(5,6);

        mother.move(9,11);
    }

    @Test
    public void testGetPosition() {
        assertTrue(mother.getPosition()<=11 && mother.getPosition()>=0);
    }
}



