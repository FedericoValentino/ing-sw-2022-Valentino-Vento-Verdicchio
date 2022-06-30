package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.model.boards.token.*;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.characters.Centaur;
import it.polimi.ingsw.model.cards.characters.Knight;
import it.polimi.ingsw.model.cards.characters.Princess;
import it.polimi.ingsw.model.cards.characters.TruffleHunter;
import org.junit.Test;
import static org.junit.Assert.*;


public class ChecksTest {

    MainController controllerTest = new MainController(2, true);
    MainController controllerTestFor3 = new MainController(3, true);


    /**
     * Setups a basic game and checks if the gamePhase is what we expect it to be (setup); after this, changes the phase to
     * Action and verifies if the change really occurred
     */
    @Test
    public void testIsGamePhase()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(GamePhase.SETUP, controllerTest.getGame().getCurrentTurnState().getGamePhase());
        controllerTest.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);
        assertEquals(GamePhase.ACTION, controllerTest.getGame().getCurrentTurnState().getGamePhase());
    }

    /**
     * Manipulates the card choices to make "jack" the currentPlayer, since he chose the lowest value card. Checks if the
     * currentPlayer field in the mainController contains "jack"
     */
    @Test
    public void testIsCurrentPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(3);
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        assertTrue(Checks.isCurrentPlayer("jack", controllerTest.getCurrentPlayer()));
    }

    /**
     * Uses various combinations to verify if the method is working: sometimes it uses all correct values, sometimes gets
     * intentionally the entrance index wrong, or the island index, and so on
     */
    @Test
    public void testIsDestinationAvailable()
    {
        TestUtilities.setupTestFor2(controllerTest);
        Student student = MainController.findPlayerByName(controllerTest.getGame(), "jack").getSchool().extractStudent(0);
        controllerTest.getGame().getCurrentIslands().placeToken(student, 0);
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 6, false, 13 ));
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 4, true, 13 ));
        assertTrue(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 3, false, 13 ));
        assertTrue(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 2, true, 5 ));
        assertFalse(Checks.isDestinationAvailable(controllerTest.getGame(), "jack", 10, false, 2));
    }

    /**
     * Makes the player draw the seventh card in the deck, which has a movement value of 4. Verifies that the method returns
     * false with a movement value equal to 5 and true with a movement value of 2
     */
    @Test
    public void testIsAcceptableMovementAmount()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(6);
        assertFalse(Checks.isAcceptableMovementAmount(controllerTest.getGame(), "jack", 5));
        assertTrue(Checks.isAcceptableMovementAmount(controllerTest.getGame(), "jack", 2));
    }

    /**
     * Fills the first cloud with students. asserts that only the first of the two clouds is available; then it empties
     * te first cloud and verifies that at that point it is no longer available
     */
    @Test
    public void testIsCloudAvailable()
    {
        TestUtilities.setupTestFor2(controllerTest);
        for(int i=0; i<3; i++)
            controllerTest.getGame().getCurrentClouds()[0].placeToken(controllerTest.getGame().getCurrentPouch().extractStudent());
        assertFalse(Checks.isCloudAvailable(controllerTest.getGame(), 2));
        assertTrue(Checks.isCloudAvailable(controllerTest.getGame(), 0));
        controllerTest.getGame().getCurrentClouds()[0].EmptyCloud();
        assertFalse(Checks.isCloudAvailable(controllerTest.getGame(), 0));
    }

    /**
     * After a basic setup, verifies that the pouch is full and available; then it empties the pouch and ensures that
     * it is not available
     */
    @Test
    public void testIsPouchAvailable()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertTrue(Checks.isPouchAvailable(controllerTest.getGame()));
        controllerTest.getGame().getCurrentPouch().getContent().clear();
        assertFalse(Checks.isPouchAvailable(controllerTest.getGame()));
    }

    /**
     * First checks the response of the method with a valid and invalid cardIndex on a full assistantDeck. Then it empties
     * the deck, and verifies that even the first index (0) isn't a valid choice
     */
    @Test
    public void testIsAssistantValid()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertTrue(Checks.isAssistantValid(controllerTest.getGame(), "jack", 5));
        assertFalse(Checks.isAssistantValid(controllerTest.getGame(), "jack", 10));
        for(int i=0; i<10; i++)
            controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        assertFalse(Checks.isAssistantValid(controllerTest.getGame(), "jack", 0));
    }

    /**
     * Makes the first player choose the first card in his deck: tests the response of the method when the other player
     * tries to choose the same card first, and then another one
     */
    @Test
    public void testIsAssistantAlreadyPlayed()
    {
        TestUtilities.setupTestFor3(controllerTestFor3);
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "fede", 0));
        assertFalse(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "fede", 1));
    }

    /**
     * At first, with three full decks, checks that the same card already played by another one cannot be played by another
     * player. Then reduces the third player's deck size and renovates the choices of th first two players, accordingly
     * to what remains in the third player's deck. Now the third player's has in his hand only card that are currently being played:
     * checks if the method response is positive for both cards
     */
    @Test
    public void testCanCardStillBePlayed()
    {
        TestUtilities.setupTestFor3(controllerTestFor3);
        controllerTestFor3.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTestFor3.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(1);
        assertTrue(Checks.isAssistantAlreadyPlayed(controllerTestFor3.getGame(), "puddu", 0));
        assertFalse(Checks.canCardStillBePlayed(controllerTestFor3.getGame(), "puddu", 0));
        controllerTestFor3.getGame().getCurrentTeams().get(2).getPlayers().get(0).chooseAssistantCard(2);

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

    /**
     * Simulates a turn, in which the first player chooses the card with the minor value; checks if the second player
     * is effectively the last player of the turn
     */
    @Test
    public void testIsLastPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(1);
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        assertFalse(Checks.isLastPlayer(controllerTest.getGame()));
        controllerTest.determineNextPlayer();
        assertTrue(Checks.isLastPlayer(controllerTest.getGame()));
    }

    /**
     * Creates two cards, one is an influence character, the other isn't. Adds the influence character to the deck and picks it.
     * Checks that the method gives a positive response.
     * Clears the deck and does the same with the no influence card: checks that the method gives a negative response
     */
    @Test
    public void checkForInfluenceCharacters()
    {
        TestUtilities.setupTestFor2(controllerTest);

        Knight knight = new Knight();
        Princess princess = new Princess();

        EffectTestsUtility.setDecks(knight, controllerTest.getGame());
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(10);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertTrue(Checks.checkForInfluenceCharacter(controllerTest.getGame()));

        controllerTest.getGame().getCurrentActiveCharacterCard().clear();
        controllerTest.getGame().getCurrentActiveCharacterCard().add(princess);

        assertFalse(Checks.checkForInfluenceCharacter(controllerTest.getGame()));
    }

    /**
     * Setups a basic game with two clouds available. The clouds are empty, and it checks that the method gives a positive
     * response on the first two, not on the third. After this it fills the first clouds, and verifies the negative response of the method
     */
    @Test
    public void testCanCloudBeFilled()
    {
        TestUtilities.setupTestFor2(controllerTest);

        assertTrue(Checks.canCloudBeFilled(controllerTest.getGame(), 0));
        assertTrue(Checks.canCloudBeFilled(controllerTest.getGame(), 1));
        assertFalse(Checks.canCloudBeFilled(controllerTest.getGame(), 2));

        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 1);

        assertFalse(Checks.canCloudBeFilled(controllerTest.getGame(), 0));
        assertFalse(Checks.canCloudBeFilled(controllerTest.getGame(), 1));

    }

    /**
     * After the initial setup, verifies the negative response of the method. Then impose the last turn by setting it
     * directly, and then tests the positive response
     */
    @Test
    public void testIsLastTurn()
    {
        TestUtilities.setupTestFor2(controllerTest);

        assertFalse(Checks.isLastTurn(controllerTest.getGame()));

        controllerTest.getGame().getCurrentTurnState().setLastTurn();

        assertTrue(Checks.isLastTurn(controllerTest.getGame()));

    }

    /**
     * After a simple setup, checks the obviously negative response of the method (the assistant decks are full). After
     * emptying one of the decks, it checks for the positive response
     */
    @Test
    public void testCheckLastTurnDueToAssistants()
    {
        TestUtilities.setupTestFor2(controllerTest);

        assertFalse(Checks.checkLastTurnDueToAssistants(controllerTest.getGame(), "jack"));

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getDeck().clear();

        assertTrue(Checks.checkLastTurnDueToAssistants(controllerTest.getGame(), "jack"));
    }

    /**
     * After a simple setup, checks the obviously negative response of the method (no one could have already won). After
     * imposing directly the presence of a winning team, it checks the affirmative response
     */
    @Test
    public void testIsThereAWinner()
    {
        TestUtilities.setupTestFor2(controllerTest);

        assertFalse(Checks.isThereAWinner(controllerTest.getGame()));
        assertFalse(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());

        controllerTest.getGame().getCurrentTurnState().updateWinner(ColTow.GREY);

        assertTrue(Checks.isThereAWinner(controllerTest.getGame()));
        assertTrue(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());
    }
}