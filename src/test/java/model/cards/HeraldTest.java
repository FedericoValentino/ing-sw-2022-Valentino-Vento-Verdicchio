package model.cards;

import controller.MainController;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HeraldTest {
    Herald h=new Herald();

    @Test
    public void testGetUses()
    {
        assertEquals(h.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(h.getBaseCost(),3);
        assertEquals(h.currentCost,h.getBaseCost());
        h.updateCost();
        assertEquals(h.getCurrentCost(),(h.getUses()+h.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(h.getIdCard(),2);
    }

    @Test
    /** Herald effect test */
    public void testTestEffect1()
    {

        MainController controllerTest = new MainController(2, true);

        setupTest(controllerTest);
        Herald testCard = new Herald();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, island, null, null);




        /*After the influence calculation, the first team should control the selected island: fisrt it checks if
        the teamInfluence structure in the island has the correct values, then it checks that the winning team
        is chosen correctly. In the end it ensures that motherNature has been set at false on the island   */
        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        EffectTestsUtility.checksAfterInfluenceCalculation(controllerTest.getGame(), 0, island);
        assertFalse(controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature);
    }


}



