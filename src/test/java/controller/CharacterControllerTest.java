package controller;

import controller.MainController;
import model.CurrentGameState;
import model.cards.CharacterCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterControllerTest{

    MainController controllerTest = new MainController(2, true);

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, "Franco" );
        controllerTest.AddPlayer(1, "fede", 8, "Giulio");
        controllerTest.Setup();
    }

    @Test
    public void testPickCard()
    {
        setupTest();
        CharacterCard card1 = controllerTest.getGame().getCurrentCharacterDeck().getCard(0);
        CharacterCard card2 = controllerTest.getGame().getCurrentCharacterDeck().getCard(1);
        CharacterCard card3 = controllerTest.getGame().getCurrentCharacterDeck().getCard(2);
        int baseInitialCost = card1.getBaseCost();
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card1.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getIdCard());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getUses());
        assertEquals(baseInitialCost + 1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCurrentCost());

    }

    @Test
    public void testEffect() {
    }

    @Test
    public void testTestEffect() {
    }

    @Test
    public void testTestEffect1() {
    }

    @Test
    public void testTestEffect2() {
    }

    @Test
    public void testTestEffect3() {
    }

    @Test
    public void testTestEffect4() {
    }

    @Test
    public void testTestEffect5() {
    }

    @Test
    public void testTestEffect6() {
    }

    @Test
    public void testGetPickedCard() {
    }
}