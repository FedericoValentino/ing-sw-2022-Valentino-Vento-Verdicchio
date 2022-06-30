package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanningControllerTest {

    MainController controllerTest = new MainController(2, false);

    /**
     * Setups the environment and fills the first cloud. Ensures that:
     * - only two clouds have been created
     * - the first cloud has been filled with three players, and the second one remained untouched
     * - the pouch size has accordingly decreased
     */
    @Test
    public void testDrawStudentForClouds()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);

        assertEquals(2, controllerTest.getGame().getCurrentClouds().length);
        assertEquals(3, controllerTest.getGame().getCurrentClouds()[0].getStudents().size());
        assertEquals(0, controllerTest.getGame().getCurrentClouds()[1].getStudents().size());
        assertEquals(103, controllerTest.getGame().getCurrentPouch().getContent().size());
    }


    /**
     * Setups the environment and draws the third card of the grey player's assistant deck. Ensures that:
     * - the extracted card is the right card, by comparing value and movement to their expected values
     * - the card has been extracted from the right deck by analyzing its size
     * - the value and movement of the card have been used to update the same fields of the correct player
     */
    @Test
    public void testDrawAssistantCard()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), "jack", 2);

        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getMovement());
        assertEquals(9, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().size());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
    }
}