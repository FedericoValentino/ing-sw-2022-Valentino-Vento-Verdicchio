package it.polimi.ingsw.controller;
import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ActionControllerTest
{
    MainController controllerTest = new MainController(2, true);

    /**
     * Setups a test environment for two players, then saves the first student from the entrance of the current player's school.
     * Then it places the student at the first position in the entrance onto the island 0. Checks if the island contains a student
     * of the same type as the saved one
     */
    @Test
    public void testPlaceStudentToIsland()
    {
        Student student;
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTurn(controllerTest);
        student = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        controllerTest.getActionController().placeStudentToIsland(0, 0, controllerTest.getGame());
        assert(controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents().contains(student));
    }

    /**
     * Setups a test environment for two players, then saves the first student form the entrance of the current player's school.
     * Then saves the amount of students in the player's dining room corresponding to the color of that student.
     * After this, it adds the first student of the entrance in the dining room; checks whether the number of students in the
     * expected segment of the dining room has increased by one
     */
    @Test
    public void testPlaceStudentToDiningRoom()
    {
        Student student;
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTurn(controllerTest);
        student = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        int studentsInDiningBeforeCheck = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[student.getColor().ordinal()];
        controllerTest.getActionController().placeStudentToDiningRoom(0, controllerTest.getGame());
        assertEquals(studentsInDiningBeforeCheck + 1, MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[student.getColor().ordinal()]);
    }

    /**
     * After creating the right environment and generating a random movement value for mother nature, based on a predetermined
     * extracted assistant card, it moves mother nature of that value. Checks if the island where mother nature previously was
     * has set its MotherNature parameter to false, and if the new destination have set it to true
     */
    @Test
    public void testMoveMN()
    {
        Random random = new Random();
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTurn(controllerTest);
        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), controllerTest.getCurrentPlayer(), 7);
        int movement = random.nextInt(MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getMaxMotherMovement() - 1) + 1;
        if(movement == 0)
        {
            movement++;
        }
        int previousPosition = controllerTest.getGame().getCurrentMotherNature().getPosition();
        controllerTest.getActionController().MoveMN(movement, controllerTest.getGame());
        assert(!controllerTest.getGame().getCurrentIslands().getIslands().get(previousPosition).getMotherNature());
        assert(controllerTest.getGame().getCurrentIslands().getIslands().get(controllerTest.getGame().getCurrentMotherNature().getPosition()).getMotherNature());
    }

    /**
     * After a long setup, checks whether the influence calculation on the desired island has gone as intended
     */
    @Test
    public void testSolveEverything()
    {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTurn(controllerTest);
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents().removeAll(controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents());
        for(int i = 0; i < 4; i++)
        {
            MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().placeToken(new Student(Col.PINK));

        }

        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToDiningRoom(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    controllerTest.getGame());
        }

        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToIsland(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    0,
                    controllerTest.getGame());
        }
        assertTrue(MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getProfessorTable()[Col.PINK.ordinal()]);
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).setMotherNature(true);
        controllerTest.getGame().solveEverything( 0);
        assertEquals(MainController.getPlayerColor(controllerTest.getGame(), controllerTest.getCurrentPlayer()),  controllerTest.getGame().getCurrentIslands().getIslands().get(0).getOwnership());
        controllerTest.determineNextPlayer();


        for(int i = 0; i < 6; i++)
        {
            MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().placeToken(new Student(Col.RED));
        }


        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToDiningRoom(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    controllerTest.getGame()
            );
        }


        for(int i = 0; i < 4; i++)
        {
            controllerTest.getActionController().placeStudentToIsland(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    0,
                    controllerTest.getGame());
        }
        assertTrue(MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getProfessorTable()[Col.RED.ordinal()]);
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).setMotherNature(true);
        controllerTest.getGame().solveEverything(0);
        assertEquals(MainController.getPlayerColor(controllerTest.getGame(), controllerTest.getCurrentPlayer()),  controllerTest.getGame().getCurrentIslands().getIslands().get(0).getOwnership());

    }

    /**
     * checks if the selected cloud is empty after being summoned to draw students form it, in order to add them in the entrance;
     * by saving the entrance size before the process, it is able to check if the students have been correctly placed into it.
     */
    @Test
    public void TestDrawFromClouds()
    {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTurn(controllerTest);
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);
        int entranceSize = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size();
        controllerTest.getActionController().drawFromClouds(0, controllerTest.getGame());
        assert(controllerTest.getGame().getCurrentClouds()[0].getStudents().isEmpty());
        assert(entranceSize + 3 == MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size());
    }

}
