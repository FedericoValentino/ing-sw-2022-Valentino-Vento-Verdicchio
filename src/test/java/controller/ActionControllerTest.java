package controller;
import model.boards.token.Student;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ActionControllerTest
{
    MainController controllerTest = new MainController(2, true);

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, "Franco" );
        controllerTest.AddPlayer(1, "fede", 8, "Giulio");
        controllerTest.Setup();
    }


    @Test
    public void testPlaceStudentToIsland()
    {
        Student s;
        setupTest();
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        s = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        controllerTest.getActionController().placeStudentToIsland(0, 0, controllerTest.getGame(), controllerTest.getCurrentPlayer());
        assert(controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents().contains(s));
    }

    @Test
    public void testPlaceStudentToDiningRoom()
    {
        Student s;
        setupTest();
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        s = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        int x = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[s.getColor().ordinal()];
        controllerTest.getActionController().placeStudentToDiningRoom(0, controllerTest.getGame(), controllerTest.getCurrentPlayer());
        assertEquals(x + 1, MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[s.getColor().ordinal()]);
    }



}
