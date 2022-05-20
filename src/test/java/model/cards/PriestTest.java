package model.cards;

import controller.MainController;
import model.CurrentGameState;
import model.boards.Pouch;
import model.boards.token.Col;
import model.boards.token.Student;
import model.boards.token.Wizard;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PriestTest {
    Priest p=new Priest();
    Pouch po=new Pouch();

    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,p.getBaseCost());
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void getIdCard()
    {
        assertEquals(p.getIdCard(),0);
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
    /** Priest effect test */
    public void effect()
    {
        MainController controllerTest = new MainController(2, true);

            setupTest(controllerTest);
            Priest testCard = new Priest();
            EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
            EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

            int island = (int) ((Math.random()*11));

        /*fills the Priest card with 4 students chosen randomly from the pouch,
        then saves the colour of the first one in the arraylist for testing purposes*/
            for (int i = 0; i < 4; i++)
                testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
            Col color = testCard.getStudents().get(0).getColor();

            controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
            testCard.effect(controllerTest.getGame(), 0, island, null, null);





            //Checks if a new student has been added to the card after the effect
            assertEquals(4, testCard.getStudents().size());

        /*First of all, it verifies that the last student added to the island is of the same colour of that
        taken from the card.
        Then, it checks if the correct number of students are on the island at the end of the effect  */
            assertEquals(color, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.get(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size()-1).getColor());
            if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
                assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
            else
                assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());

    }

}
