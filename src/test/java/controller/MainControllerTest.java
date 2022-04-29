package controller;

import model.CurrentGameState;
import model.boards.Island;
import model.boards.token.ColTow;
import model.boards.token.Wizard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
        assertEquals("Franco", controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getAssistantDeck().getWizard());
        assertEquals("fede", controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getNome());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(true, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).isTowerOwner());
        assertEquals("Giulio", controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getAssistantDeck().getWizard());
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
    }

    @Test
    public void testGetPlayerColor()
    {
        setupTest();
        assertEquals(ColTow.values()[0], MainController.getPlayerColor(controllerTest.getGame(), "jack"));
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
}
