package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.characters.GrandmaHerbs;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GrandmaHerbsTest {
    GrandmaHerbs g=new GrandmaHerbs();


    @Test
    public void testGetUses()
    {
        assertEquals(g.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {
        Assert.assertEquals(g.getBaseCost(),2);
        Assert.assertEquals(g.currentCost,g.getBaseCost());
        g.updateCost();
        assertEquals(g.getCurrentCost(),(g.getUses()+g.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        Assert.assertEquals(g.getCharacterName(), CharacterName.GRANDMA_HERBS);
    }

    @Test
    public void checkGetUses()
    {
        assertEquals(g.getNoEntry(),4);
    }

    @Test
    public void testUpdateNoEntry()
    {
        int i=g.getNoEntry();
        g.updateNoEntry(1);
        assertEquals(i+1,g.getNoEntry());
    }

    @Test
    /** Grandma Weed effect test */
    public void testTestEffect3()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
        GrandmaHerbs testCard = new GrandmaHerbs();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = (int) ((Math.random()*11));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.GRANDMA_HERBS, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        ArrayList<Integer> chosenIsland = new ArrayList<>();
        chosenIsland.add(island);
        testCard.effect(controllerTest.getGame(), null, chosenIsland, null, null);



        /*It checks if the noEntry has been placed on the chosen island and if the counter on
        GWeed has been updated accordingly */
        assertTrue(controllerTest.getGame().getCurrentIslands().getIslands().get(island).getNoEntry());
        assertEquals(3, testCard.getNoEntry());

    }
}
