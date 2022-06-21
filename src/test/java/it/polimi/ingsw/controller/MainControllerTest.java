package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.cards.EffectTestsUtility;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class MainControllerTest
{
    MainController controllerTest = new MainController(2, true);
    MainController controllerTestFor3 = new MainController(3, true);


    @Test
    public void testAddPlayer()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(2, controllerTest.getGame().getCurrentTeams().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().size());
        assertEquals("jack", controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getName());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
        assertTrue(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).isTowerOwner());
        assertEquals(Wizard.LORD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getWizard());
        assertEquals("fede", controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getName());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
        assertTrue(controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).isTowerOwner());
        assertEquals(Wizard.DRUID, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getAssistantDeck().getWizard());
        controllerTest.addPlayer(0, "nico", 8, Wizard.SENSEI);
        controllerTest.addPlayer(1, "Frah", 8, Wizard.WITCH);

        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(1).getSchool().getTowerCount());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(1).getSchool().getTowerCount());
    }

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

    @Test
    public void testFindPlayerByName()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0), MainController.findPlayerByName(controllerTest.getGame(), "jack"));
        assertNull(MainController.findPlayerByName(controllerTest.getGame(), "pirla"));
    }

    @Test
    public void testGetPlayerColor()
    {
        TestUtilities.setupTestFor2(controllerTest);
        assertEquals(ColTow.values()[0], MainController.getPlayerColor(controllerTest.getGame(), "jack"));
        assertNull(MainController.getPlayerColor(controllerTest.getGame(), "giorgio"));
    }

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

    @Test
    public void testLastTurn()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.lastTurn();
        assertTrue(controllerTest.getGame().getCurrentTurnState().getLastTurn());
    }

    @Test
    public void testSelectWinner()
    {
        TestUtilities.setupTestFor2(controllerTest);

        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        controllerTest.selectWinner();

        assertTrue(controllerTest.getGame().getCurrentTurnState().getIsGameEnded());
        assertEquals(ColTow.GREY, controllerTest.getGame().getCurrentTurnState().getWinningTeam());
    }

    @Test
    public void testRemoveSlotFromTeam()
    {
        TestUtilities.setupTestFor2(controllerTest);

        controllerTest.removeSlotFromTeam(0);
        assertEquals(0, controllerTest.getAvailableTeams()[0]);
        assertEquals(1, controllerTest.getAvailableTeams()[1]);
    }

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

