package model.boards.token;

import org.junit.Test;

public class MotherNatureTest {
    MotherNature mother= new MotherNature();

    @Test
    public void testMove()
    {
        System.out.println("Pos iniziale mN: "+mother.getPosition());
        mother.move(123,13);
        System.out.println("Pos finale mN: "+mother.getPosition());

        System.out.println("Pos iniziale mN: "+mother.getPosition());
        mother.move(5,6);

        System.out.println("Pos finale mN: "+mother.getPosition());
    }


}



