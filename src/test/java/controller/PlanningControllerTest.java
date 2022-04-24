package controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlanningControllerTest {

    MainController controllerTest = new MainController(2, true);


    public void setupTest() {
        controllerTest.AddPlayer(0, "jack", 8, "Franco");   //grey
        controllerTest.AddPlayer(1, "fede", 8, "Giulio");   //white
        controllerTest.Setup();
    }

    @Test
    public void testDrawStudentForClouds()
    {
        setupTest();

        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);

        assertEquals(2, controllerTest.getGame().getCurrentClouds().length);
        assertEquals(3, controllerTest.getGame().getCurrentClouds()[0].getStudents().size());
        assertEquals(0, controllerTest.getGame().getCurrentClouds()[1].getStudents().size());
        assertEquals(103, controllerTest.getGame().getCurrentPouch().getContent().size());
    }


    @Test
    public void testDrawAssistantCard()
    {
        setupTest();

        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), "jack", 2);

        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard().getMovement());
        assertEquals(9, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().size());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
    }
}