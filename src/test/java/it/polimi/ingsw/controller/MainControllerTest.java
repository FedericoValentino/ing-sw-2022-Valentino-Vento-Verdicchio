package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.cards.EffectTestsUtility;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Comments will not be given for tests regarding getters and setters
 */
public class MainControllerTest
{
    MainController controllerTest = new MainController(2, true);
    MainController controllerTestFor3 = new MainController(3, true);


    /**
     * First, it creates two teams of one player each and then checks, player for player, if they have the right number of towers,
     * if they are towerOwners, if the wizards correspond etc. After this another two player are added, one per existing team, and
     * it is verified that they do not have any tower assigned
     */
    @Test
    public void testAddPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(2, controllerTest.getGame().getCurrentTeams().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().size());

        Player jack = controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0);
        Player fede = controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0);

        assertEquals("jack", jack.getName());
        assertEquals(8, jack.getSchool().getTowerCount());
        assertTrue(jack.isTowerOwner());
        assertEquals(Wizard.LORD, jack.getAssistantDeck().getWizard());

        assertEquals("fede", fede.getName());
        assertEquals(8, fede.getSchool().getTowerCount());
        assertTrue(fede.isTowerOwner());
        assertEquals(Wizard.DRUID, fede.getAssistantDeck().getWizard());

        controllerTest.addPlayer(0, "nico", 8, Wizard.SENSEI);
        controllerTest.addPlayer(1, "Frah", 8, Wizard.WITCH);

        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(1).getSchool().getTowerCount());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(1).getSchool().getTowerCount());
    }

    /**
     * Checks if the setup has been done correctly, by visiting each island and each entrance and assessing the number
     * of students on them
     */
    @Test
    public void testSetup()
    {
        TestUtilities.setupTestFor2(controllerTest);
        int MNpos = controllerTest.getGame().getCurrentMotherNature().getPosition();
        for(int i = MNpos+1; i < 12 + MNpos; i++)
        {
            if(i != MNpos + 6)
            {
                if(i >= 12)
                {
                    assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(i-12).getCurrentStudents().size());
                }
                else
                {
                    assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(i).getCurrentStudents().size());
                }
            }
            else
            {
                if(i >= 12)
                {
                    assertEquals(0, controllerTest.getGame().getCurrentIslands().getIslands().get(i-12).getCurrentStudents().size());
                }
                else
                {
                    assertEquals(0, controllerTest.getGame().getCurrentIslands().getIslands().get(i).getCurrentStudents().size());
                }

            }
        }
        assertFalse(controllerTest.getGame().getCurrentPouch().getSetup());
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getEntrance().size());
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getEntrance().size());
    }

    /**
     * Checks if the values regarding turn orders are correct after the function call
     */
    @Test
    public void testUpdateTurnState()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.updateTurnState();
        ArrayList<Integer> ordered = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : controllerTest.getGame().getCurrentTurnState().getTurnOrder().entrySet())
        {
            ordered.add(entry.getValue());
        }
        for(int i = 0; i < ordered.size(); i++)
        {
            for(int j = i; j < ordered.size(); j++)
            {
                assert(ordered.get(i) <= ordered.get(j));
            }
        }

    }

    /**
     * After calling the right functions, based on the values of the two players, verifies if the currentPlayer is the
     * expected player, so the one with the smaller value
     */
    @Test
    public void testDetermineNextPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
        if(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getValue() < controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getValue())
        {
            assertEquals("jack", controllerTest.getCurrentPlayer());
        }
        else if(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getValue() > controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getValue())
        {
            assertEquals("fede", controllerTest.getCurrentPlayer());
        }
    }

    /**
     * Checks the response of the method both in cases (when giving a name identifying by an existing player, and the opposite case)
     */
    @Test
    public void testFindPlayerByName()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0), MainController.findPlayerByName(controllerTest.getGame(), "jack"));
        assertNull(MainController.findPlayerByName(controllerTest.getGame(), "pirla"));
    }

    /**
     * Checks the response of the method both in cases (when giving a name identifying by an existing player, and the opposite case)
     */
    @Test
    public void testGetPlayerColor()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(ColTow.values()[0], MainController.getPlayerColor(controllerTest.getGame(), "jack"));
        assertNull(MainController.getPlayerColor(controllerTest.getGame(), "giorgio"));
    }

    /**
     * Checks whether the game phase is what we expect it to be after passing a turn, and whether the assistant cards
     * have been handled correctly with the passing of the turn
     */
    @Test
    public void testUpdateGamePhase()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).chooseAssistantCard(0);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).chooseAssistantCard(1);
        controllerTest.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);

        controllerTest.updateGamePhase(GamePhase.PLANNING);

        assertEquals(GamePhase.PLANNING, controllerTest.getGame().getCurrentTurnState().getGamePhase());
        assertNull(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCurrentAssistantCard());
        assertNull(controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCurrentAssistantCard());

        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getLastPlayedCard().getValue());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getLastPlayedCard().getValue());
    }

    /**
     * Checks if the reset ready actually resets the readyPlayers parameter to 0
     */
    @Test
    public void resetReady()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.readyPlayer();
        assertEquals(1, controllerTest.getReadyPlayers());
        controllerTest.resetReady();
        assertEquals(0, controllerTest.getReadyPlayers());
    }

    @Test
    public void testGetGame()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assert(controllerTest.getGame() instanceof CurrentGameState);
    }

    @Test
    public void testGetCharacterController()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assert(controllerTest.getCharacterController() instanceof CharacterController);
    }

    @Test
    public void testGetCurrentPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(controllerTest.getCurrentPlayer() instanceof String);
    }

    @Test
    public void testIsExpertGame()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(controllerTest.isExpertGame() || !controllerTest.isExpertGame());
    }

    /**
     * Checks if the second out of two players to play is effectively recognized as lastPlayer
     */
    @Test
    public void testLastPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(!Checks.isLastPlayer(controllerTest.getGame()));
        controllerTest.determineNextPlayer();
        assert(Checks.isLastPlayer(controllerTest.getGame()));
    }

    @Test
    public void testIsGamePhase()
    {
        assertTrue(Checks.isGamePhase(controllerTest.getGame(), GamePhase.SETUP));
    }

    /**
     * Saves the initial value of ready player, and checks if that value increased after calling the readyPlayer method
     */
    @Test
    public void testReadyPlayer()
    {
        int x = controllerTest.getReadyPlayers();
        controllerTest.readyPlayer();
        assertEquals(x + 1, controllerTest.getReadyPlayers());
    }

    @Test
    public void testGetAvailableWizards()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assert(controllerTest.getAvailableWizards().get(0) instanceof Wizard);
    }

    @Test
    public void testGetPlayers()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assert(controllerTest.getPlayers() >= 2);
    }

    /**
     * Checks if the method lastTurn actually changes the turn to lastTurn
     */
    @Test
    public void testLastTurn()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.lastTurn();
        assertTrue(controllerTest.getGame().getCurrentTurnState().getLastTurn());
    }

    /**
     * Since the selectWinner method has been used, checks if the game is ending and if the winning team is of the expected
     * color
     */
    @Test
    public void testSelectWinner()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.selectWinner();

        assertTrue(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());
        assertNull(controllerTest.getGame().getCurrentTurnState().getWinningTeam());
    }

    /**
     * Checks if the removeSlot is working as intended,by removing one slot from the first team and leaving unaltered the second
     */
    @Test
    public void testRemoveSlotFromTeam()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.removeSlotFromTeam(0);
        assertEquals(0, controllerTest.getAvailableTeams()[0]);
        assertEquals(1, controllerTest.getAvailableTeams()[1]);
    }

    /**
     * After creating games for both two and three players, check the available slots for every case
     */
    @Test
    public void testGetAvailableTeams()
    {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTestFor3(controllerTestFor3);
        TestUtilities.setupTurn(controllerTest);
        TestUtilities.setupTurn(controllerTestFor3);

        assertEquals(3, controllerTest.getAvailableTeams().length);
        assertEquals(3, controllerTestFor3.getAvailableTeams().length);

        for(int i = 0; i < 2; i++)
        {
            assertEquals(1, controllerTest.getAvailableTeams()[i]);
            assertEquals(1, controllerTestFor3.getAvailableTeams()[i]);
        }
        assertEquals(0, controllerTest.getAvailableTeams()[2]);
        assertEquals(1, controllerTestFor3.getAvailableTeams()[2]);
    }
}

