package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.model.boards.token.Wizard;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanningControllerTest {

    MainController controllerTest = new MainController(2, false);


    @Test
    public void testDrawStudentForClouds()
    {
        TestUtilities.setupTestfor2(controllerTest);

        //Operates on the first cloud
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);

        /* Ensures that:
        - only two clouds have been created, since this is a 2 player game
        - the first cloud has been filled with three players, and second one remained untouched
        - the students have been effectively drawn from the pouch, comparing its size to its expected value
         */
        assertEquals(2, controllerTest.getGame().getCurrentClouds().length);
        assertEquals(3, controllerTest.getGame().getCurrentClouds()[0].getStudents().size());
        assertEquals(0, controllerTest.getGame().getCurrentClouds()[1].getStudents().size());
        assertEquals(103, controllerTest.getGame().getCurrentPouch().getContent().size());
    }


    @Test
    public void testDrawAssistantCard()
    {
        TestUtilities.setupTestfor2(controllerTest);

        //it draws the third card of the AssistantDeck on account of the GREY player
        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), "jack", 2);

        /* Ensures that:
        - The extracted card is the right card, by comparing its Value and Movement to their expected values
        - The card has been extracted from the Deck by analysing its size
        - The value and Movement of the card have been correctly used to update the same fields of the correct Player
         */
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getMovement());
        assertEquals(9, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().size());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
    }
}