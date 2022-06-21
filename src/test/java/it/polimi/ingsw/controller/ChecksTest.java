package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.model.boards.token.*;
import it.polimi.ingsw.model.cards.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class ChecksTest {

    MainController controllerTest = new MainController(2, true);
    MainController controllerTestFor3 = new MainController(3, true);


    @Test
    public void testIsGamePhase()
    {
        TestUtilities.setupTestfor2(controllerTest);
        assertEquals(GamePhase.SETUP, controllerTest.getGame().getCurrentTurnState().getGamePhase());
        controllerTest.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);
        assertEquals(GamePhase.ACTION, controllerTest.getGame().getCurrentTurnState().getGamePhase());
    }

    @Test
    public void testIsCurrentPlayer()
    {
        TestUtilities.setupTestfor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(3);
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        assertTrue(Checks.isCurrentPlayer("jack", controllerTest.getCurrentPlayer()));
    }

    @Test
    public void testIsDestinationAvailable()
    {
        TestUtilities.setupTestfor2(controllerTest);
        Student student = MainController.findPlayerByName(controllerTest.getGame(), "jack").getSchool().extractStudent(0);
        controllerTest.getGame().getCurrentIslands().placeToken(student, 0);
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 6, false, 13 ));
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 4, true, 13 ));
        assertTrue(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 3, false, 13 ));
        assertTrue(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 2, true, 5 ));
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 10, false, 2));
    }

    @Test
    public void testIsAcceptableMovementAmount()
    {
        TestUtilities.setupTestfor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(6);
        assertFalse(Checks.isAcceptableMovementAmount(controllerTest.getGame(), "jack", 5));
        assertTrue(Checks.isAcceptableMovementAmount(controllerTest.getGame(), "jack", 2));
    }

    @Test
    public void testIsCloudAvailable()
    {
        TestUtilities.setupTestfor2(controllerTest);
        for(int i=0; i<3; i++)
            controllerTest.getGame().getCurrentClouds()[0].placeToken(controllerTest.getGame().getCurrentPouch().extractStudent());
        assertFalse(Checks.isCloudAvailable(controllerTest.getGame(), 2));
        assertTrue(Checks.isCloudAvailable(controllerTest.getGame(), 0));
        controllerTest.getGame().getCurrentClouds()[0].EmptyCloud();
        assertFalse(Checks.isCloudAvailable(controllerTest.getGame(), 0));
    }

    @Test
    public void testIsPouchAvailable()
    {
        TestUtilities.setupTestfor2(controllerTest);
        assertTrue(Checks.isPouchAvailable(controllerTest.getGame()));
        controllerTest.getGame().getCurrentPouch().getContent().clear();
        assertFalse(Checks.isPouchAvailable(controllerTest.getGame()));
    }

    @Test
    public void testIsAssistantValid()
    {
        TestUtilities.setupTestfor2(controllerTest);
        assertTrue(Checks.isAssistantValid(controllerTest.getGame(), "jack", 5));
        assertFalse(Checks.isAssistantValid(controllerTest.getGame(), "jack", 10));
        for(int i=0; i<10; i++)
            controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        assertFalse(Checks.isAssistantValid(controllerTest.getGame(), "jack", 0));
    }

    @Test
    public void testIsAssistantAlreadyPlayed()
    {
        TestUtilities.setupTestfor3(controllerTestFor3);
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "fede", 0));
        assertFalse(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "fede", 1));
    }

    @Test
    public void testCanCardStillBePlayed()
    {
        TestUtilities.setupTestfor3(controllerTestFor3);
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTestFor3.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(1);
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "puddu", 0));
        assertFalse(Checks.canCardStillBePlayed(controllerTestFor3.getGame(), "puddu", 0));
        controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).chooseAssistantCard(2);
        // fede: 1 2 3 4 5 6 7 8 9
        // jack: 0 2 3 4 5 6 7 8 9
        // pudd: 0 1 3 4 5 6 7 8 9
        for(int i = 0; i < 7; i++)
            controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).getAssistantDeck().getDeck().remove(0);
        assertEquals(9, controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).getAssistantDeck().getDeck().get(0).getValue());
        assertEquals(10, controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).getAssistantDeck().getDeck().get(1).getValue());
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).discard();
        controllerTestFor3.getGame().getCurrentTeams().get(1).getPlayers().get(0).discard();
        controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).discard();
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(7);
        controllerTestFor3.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(8);
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "puddu", 0));
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "puddu", 1));
        assertTrue(Checks.canCardStillBePlayed(controllerTestFor3.getGame(), "puddu", 0));
        assertTrue(Checks.canCardStillBePlayed(controllerTestFor3.getGame(), "puddu", 1));
    }

    @Test
    public void testIsLastPlayer()
    {
        TestUtilities.setupTestfor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(1);
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        assertFalse(Checks.isLastPlayer(controllerTest.getGame()));
        controllerTest.determineNextPlayer();
        assertTrue(Checks.isLastPlayer(controllerTest.getGame()));
    }

    @Test
    public void checkForInfluenceCharacters()
    {
        TestUtilities.setupTestfor2(controllerTest);

        Knight knight = new Knight();
        Centaur centaur = new Centaur();
        TruffleHunter tHunter = new TruffleHunter();
        Princess princess = new Princess();

        EffectTestsUtility.setDecks(knight, controllerTest.getGame());
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(10);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertTrue(Checks.checkForInfluenceCharacter(controllerTest.getGame()));

        controllerTest.getGame().getCurrentActiveCharacterCard().clear();
        controllerTest.getGame().getCurrentActiveCharacterCard().add(princess);

        assertFalse(Checks.checkForInfluenceCharacter(controllerTest.getGame()));
    }

    @Test
    public void testIsCloudFillable()
    {
        TestUtilities.setupTestfor2(controllerTest);

        assertTrue(Checks.isCloudFillable(controllerTest.getGame(), 0));
        assertTrue(Checks.isCloudFillable(controllerTest.getGame(), 1));
        assertFalse(Checks.isCloudFillable(controllerTest.getGame(), 2));

        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 1);

        assertFalse(Checks.isCloudFillable(controllerTest.getGame(), 0));
        assertFalse(Checks.isCloudFillable(controllerTest.getGame(), 1));

    }

    @Test
    public void testIsLastTurn()
    {
        TestUtilities.setupTestfor2(controllerTest);

        assertFalse(Checks.isLastTurn(controllerTest.getGame()));

        controllerTest.getGame().getCurrentTurnState().setLastTurn();

        assertTrue(Checks.isLastTurn(controllerTest.getGame()));

    }

    @Test
    public void testCheckLastTurnDueToAssistants()
    {
        TestUtilities.setupTestfor2(controllerTest);

        assertFalse(Checks.checkLastTurnDueToAssistants(controllerTest.getGame(), "jack"));

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().clear();

        assertTrue(Checks.checkLastTurnDueToAssistants(controllerTest.getGame(), "jack"));
    }

    @Test
    public void testIsThereAWinner()
    {
        TestUtilities.setupTestfor2(controllerTest);

        assertFalse(Checks.isThereAWinner(controllerTest.getGame()));
        assertFalse(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());

        controllerTest.getGame().getCurrentTurnState().updateWinner(ColTow.GREY);

        assertTrue(Checks.isThereAWinner(controllerTest.getGame()));
        assertTrue(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());
    }
}