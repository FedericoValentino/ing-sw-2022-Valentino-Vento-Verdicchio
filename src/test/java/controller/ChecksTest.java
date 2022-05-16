package controller;

import model.boards.token.GamePhase;
import model.boards.token.Student;
import model.boards.token.Wizard;
import org.junit.Test;
import static org.junit.Assert.*;


public class ChecksTest {

    MainController controllerTest = new MainController(2, true);

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, Wizard.LORD );
        controllerTest.AddPlayer(1, "fede", 8, Wizard.DRUID);
        controllerTest.Setup();
    }

    @Test
    public void testIsGamePhase()
    {
        setupTest();
        assertEquals(GamePhase.SETUP, controllerTest.getGame().getCurrentTurnState().getGamePhase());
        controllerTest.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);
        assertEquals(GamePhase.ACTION, controllerTest.getGame().getCurrentTurnState().getGamePhase());
    }

    @Test
    public void testIsCurrentPlayer()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(3);
        //controllerTest.getGame().getCurrentTurnState().UpdateTurn();
        controllerTest.determineNextPlayer();
        assertEquals("jack", controllerTest.getCurrentPlayer());
    }

    @Test
    public void testIsDestinationAvailable()
    {
        setupTest();
        Student student = MainController.findPlayerByName(controllerTest.getGame(), "jack").getSchool().extractStudent(0);
        controllerTest.getGame().getCurrentIslands().placeToken(student, 0);
        assertFalse(controllerTest.getChecks().isDestinationAvailable(controllerTest.getGame(), "jack", 6, false, 13 ));
        assertFalse(controllerTest.getChecks().isDestinationAvailable(controllerTest.getGame(), "jack", 4, true, 13 ));
        assertTrue(controllerTest.getChecks().isDestinationAvailable(controllerTest.getGame(), "jack", 3, false, 13 ));
        assertTrue(controllerTest.getChecks().isDestinationAvailable(controllerTest.getGame(), "jack", 2, true, 5 ));
    }

    @Test
    public void testIsAcceptableMovementAmount()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(6);
        assertFalse(controllerTest.getChecks().isAcceptableMovementAmount(controllerTest.getGame(), "jack", 5));
        assertTrue(controllerTest.getChecks().isAcceptableMovementAmount(controllerTest.getGame(), "jack", 2));
    }

    @Test
    public void testIsCloudAvailable()
    {
        setupTest();
        for(int i=0; i<3; i++)
            controllerTest.getGame().getCurrentClouds()[0].placeToken(controllerTest.getGame().getCurrentPouch().extractStudent());
        assertFalse(controllerTest.getChecks().isCloudAvailable(controllerTest.getGame(), 2));
        assertTrue(controllerTest.getChecks().isCloudAvailable(controllerTest.getGame(), 0));
        controllerTest.getGame().getCurrentClouds()[0].EmptyCloud();
        assertFalse(controllerTest.getChecks().isCloudAvailable(controllerTest.getGame(), 0));
    }

    @Test
    public void testIsPouchAvailable()
    {
        setupTest();
        assertTrue(controllerTest.getChecks().isPouchAvailable(controllerTest.getGame()));
        System.out.println(controllerTest.getGame().getCurrentPouch().getContent().size());
        for(int i=0; i<106; i++)
            controllerTest.getGame().getCurrentPouch().extractStudent();
        System.out.println(controllerTest.getGame().getCurrentPouch().getContent().size());
        assertFalse(controllerTest.getChecks().isPouchAvailable(controllerTest.getGame()));
    }

    @Test
    public void testIsAssistantValid()
    {
        setupTest();
        assertTrue(controllerTest.getChecks().isAssistantValid(controllerTest.getGame(), "jack", 5));
        assertFalse(controllerTest.getChecks().isAssistantValid(controllerTest.getGame(), "jack", 10));
        for(int i=0; i<10; i++)
            controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        assertFalse(controllerTest.getChecks().isAssistantValid(controllerTest.getGame(), "jack", 0));
    }

    @Test
    public void testIsAssistantAlreadyPlayed() {
    }

    @Test
    public void testCanCardStillBePlayed() {
    }

    @Test
    public void testIsLastPlayer() {
    }

    @Test
    public void testCheckForInfluenceCharacter() {
    }
}