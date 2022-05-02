package model.cards;

import controller.MainController;
import org.junit.Assert;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GrandmaWeedTest {
    GrandmaWeed g=new GrandmaWeed();


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
        Assert.assertEquals(g.getIdCard(),5);
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

        setupTest(controllerTest);
        GrandmaWeed testCard = new GrandmaWeed();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        int island = (int) ((Math.random()*11));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, island, null, null);
        controllerTest.getCharacterController().deckManagement(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        /*It checks if the noEntry has been placed on the chosen island and if the counter on
        GWeed has been updated accordingly */
        assertTrue(controllerTest.getGame().getCurrentIslands().getIslands().get(island).getNoEntry());
        assertEquals(3, testCard.getNoEntry());

    }
}
