package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.characters.Postman;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostmanTest {
    Postman p=new Postman();


    @Test
    public void testGetUses()
    {
        assertEquals(p.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(p.getBaseCost(),1);
        assertEquals(p.currentCost,1);
        p.updateCost();
        assertEquals(p.getCurrentCost(),(p.getUses()+p.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(p.getCharacterName(), CharacterName.POSTMAN);
    }

    /**
     * It gives starting values to maxMovementMovement to both players.
     * Checks if the player who has called the effect has had its maxMotherMovement increased by 2
     */
    @Test
    public void testTestEffect2()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
        Postman testCard = new Postman();


        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = (int) ((Math.random()*11));

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(6);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateMaxMotherMovement(2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.POSTMAN, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), null, null, "jack", null);

        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getMaxMotherMovement());

    }
}
