package model.cards;

import controller.MainController;
import model.boards.Pouch;
import model.boards.token.Col;
import model.boards.token.Student;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
import static org.junit.Assert.*;

public class PrincessTest {
    Princess p=new Princess();
    Pouch po=new Pouch();

    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),2);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getIdCard(),10);
    }

    @Test
    public void testUpdateStudents()
    {
        p.updateStudents(po);
    }
    @Test
    public void getStudents()
    {
        for(int i=0;i<4;i++)
            p.updateStudents(po);
        assertTrue(p.getStudent(2) instanceof Student);
    }

    @Test
    /** Princess effect test */
    public void testTestEffect()
    {
        MainController controllerTest = new MainController(2, true);

        setupTest(controllerTest);
        Princess testCard = new Princess();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = (int) ((Math.random()*11));


        /*fills the Princess card with 4 students chosen randomly from the pouch,
        then saves the colour of the first one in the arraylist and the ordinal
        of the color of the aforementioned student for testing purposes*/
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        Col color = testCard.getStudents().get(0).getColor();
        int index = color.ordinal();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, 0, "fede", null);




        //Checks if a new student has been added to the card after the effect
        assertEquals(4, testCard.getStudents().size());

        /*First, it checks that the dining room of the school of the player that didn't call the effect remained untouched.
        Secondly, it assures that the dining room of the active player has been manipulated correctly (the int at the index
        corresponding with the ordinal of the selected colour has been updated: the rest remains untouched).  */
        for(int i=0; i<5; i++)
            assertEquals(0,controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getDiningRoom()[i]);
        for(int i =0; i<5; i++)
            if(i!=index)
                assertEquals(0,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
            else
                assertEquals(1,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
    }
}
