package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.CharacterControllerTest;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.CharacterName;
import org.junit.Test;

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
        assertEquals(k.currentCost, k.getBaseCost());
        k.updateCost();
        assertEquals(k.getCurrentCost(),(k.getUses()+k.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(k.getCharacterName(), CharacterName.KNIGHT);
    }

    /** Knight effect test */
    @Test
    public void testTestEffect6()
    {

        MainController controllerTest = new MainController(2, true);

        CharacterControllerTest.setupTest(controllerTest);
        Knight testCard = new Knight();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).getMotherNature())
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, island, "fede", null);




        /*Checks if the winning team is the WHITE team, after the effect has boosted its influence on the island
        (without the effect, the GREY team would have won).
         Checks if the WHITE influence has been re-updated accordingly at the end of the influence calculation*/
        EffectTestsUtility.checksAfterInfluenceCalculation(controllerTest.getGame(), 1, island);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getTeamInfluence()[1]);

    }

}
