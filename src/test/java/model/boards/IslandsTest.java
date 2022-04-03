package model.boards;
import model.boards.token.Col;
import model.boards.token.Student;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class IslandsTest {
    Islands is=new Islands();
    Student s1=new Student(Col.GREEN);
    Student s2=new Student(Col.PINK);

    @Test
    public void testInizializationIsland()
    {
        assertEquals(is.getIslands().get(0).getIslandId(),0);
    }

    @Test
    public void testPlaceToken()
    {
        is.placeToken(s1,0);
        assertEquals(is.getIslands().get(0).getCurrentStudents().get(0),s1);
        is.placeToken(s2,0);
        assertEquals(is.getIslands().get(0).getCurrentStudents().get(1),s2);
    }

    @Test
    public void testIdManagment() { // da sviluppare

    }
        @Test
    public void testTotalGroups()
    {
        //da inserire questo test nel test di management delle isole
        assertEquals(is.getTotalGroups(),12);
    }
}
