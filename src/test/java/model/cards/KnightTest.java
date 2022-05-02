package model.cards;

import controller.MainController;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
import static org.junit.Assert.assertEquals;

public class KnightTest {
    Knight k=new Knight();

    @Test
    public void testGetUses()
    {
        assertEquals(k.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(k.getBaseCost(),2);
        assertEquals(k.currentCost,k.getBaseCost());
        k.updateCost();
        assertEquals(k.getCurrentCost(),(k.getUses()+k.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(k.getIdCard(),8);
    }

    @Test
    /** Knight effect test */
    public void testTestEffect6()
    {

        MainController controllerTest = new MainController(2, true);

        setupTest(controllerTest);
        Knight testCard = new Knight();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, island, "fede", null);
        controllerTest.getCharacterController().deckManagement(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        /*Checks if the winning team is the WHITE team, after the effect has boosted its influence on the island
        (without the effect, the GREY team would have won).
         Checks if the WHITE influence has been re-updated accordingly at the end of the influence calculation*/
        EffectTestsUtility.checksAfterInfluenceCalculation(controllerTest.getGame(), 1, island);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);

    }

}
