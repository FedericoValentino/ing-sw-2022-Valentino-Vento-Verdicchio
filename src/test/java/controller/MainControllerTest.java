package controller;

import model.CurrentGameState;
import model.boards.Island;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;
import model.boards.token.Wizard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MainControllerTest
{
    MainController controllerTest = new MainController(2, true);

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, Wizard.LORD );
        controllerTest.AddPlayer(1, "fede", 8, Wizard.DRUID);
        controllerTest.Setup();
    }

    @Test
    public void testAddPlayer()
    {
        setupTest();
        assertEquals(2, controllerTest.getGame().getCurrentTeams().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().size());
        assertEquals("jack", controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getNome());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(true, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).isTowerOwner());
        assertEquals(Wizard.LORD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getWizard());
        assertEquals("fede", controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getNome());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(true, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).isTowerOwner());
        assertEquals(Wizard.DRUID, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getAssistantDeck().getWizard());
        controllerTest.AddPlayer(0, "nico", 8, Wizard.SENSEI);
        controllerTest.AddPlayer(1, "Frah", 8, Wizard.WITCH);

        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(1).getSchool().getTowerCount());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(1).getSchool().getTowerCount());
    }

    @Test
    public void testSetup()
    {
        setupTest();
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
        assertEquals(false, controllerTest.getGame().getCurrentPouch().getSetup());
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getEntrance().size());
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getEntrance().size());
    }

    @Test
    public void testUpdateTurnState()
    {
        setupTest();
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
        setupTest();
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
        setupTest();
        assertEquals(controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0), MainController.findPlayerByName(controllerTest.getGame(), "jack"));
        assertNull(MainController.findPlayerByName(controllerTest.getGame(), "pirla"));
    }

    @Test
    public void testGetPlayerColor()
    {
        setupTest();
        assertEquals(ColTow.values()[0], MainController.getPlayerColor(controllerTest.getGame(), "jack"));
        assertNull(MainController.getPlayerColor(controllerTest.getGame(), "giorgio"));
    }

    @Test
    public void testUpdateGamePhase()
    {
        setupTest();
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
        setupTest();
        controllerTest.readyPlayer();
        assertEquals(1, controllerTest.getReadyPlayers());
        controllerTest.resetReady();
        assertEquals(0, controllerTest.getReadyPlayers());
    }

    @Test
    public void testGetGame()
    {
        setupTest();
        assert(controllerTest.getGame() instanceof CurrentGameState);
    }

    @Test
    public void testGetCharacterController()
    {
        setupTest();
        assert(controllerTest.getCharacterController() instanceof CharacterController);
    }

    @Test
    public void testGetCurrentPlayer()
    {
        setupTest();
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(controllerTest.getCurrentPlayer() instanceof String);
    }

    @Test
    public void testIsExpertGame()
    {
        setupTest();
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(controllerTest.isExpertGame() == true || controllerTest.isExpertGame() == false);
    }

    @Test
    public void testLastPlayer()
    {
        setupTest();
        controllerTest.getGame().updateTurnState();
        controllerTest.determineNextPlayer();
        assert(!Checks.isLastPlayer(controllerTest.getGame()));
        controllerTest.determineNextPlayer();
        assert(Checks.isLastPlayer(controllerTest.getGame()));
    }

    @Test
    public void testIsGamePhase()
    {
        assertEquals(true, Checks.isGamePhase(controllerTest.getGame(), GamePhase.SETUP));
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
        setupTest();
        assert(controllerTest.getAvailableWizards().get(0) instanceof Wizard);
    }

    @Test
    public void testGetPlayers()
    {
        setupTest();
        assert(controllerTest.getPlayers() >= 2);
    }
}

