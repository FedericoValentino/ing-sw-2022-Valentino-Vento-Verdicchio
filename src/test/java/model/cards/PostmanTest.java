package model.cards;

import controller.MainController;
import org.junit.Test;

import static controller.CharacterControllerTest.setupTest;
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
        assertEquals(p.getIdCard(),4);
    }

    @Test
    /** Postman effect test */
    public void testTestEffect2()
    {
        MainController controllerTest = new MainController(2, true);

        setupTest(controllerTest);
        Postman testCard = new Postman();


        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        int island = (int) ((Math.random()*11));

        //It gives starting values to maxMovementMovement to both players
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(6);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateMaxMotherMovement(2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, 0, "jack", null);
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        //Checks if the player who has called the effect has had its maxMotherMovement increased by 2
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getMaxMotherMovement());

    }
}
