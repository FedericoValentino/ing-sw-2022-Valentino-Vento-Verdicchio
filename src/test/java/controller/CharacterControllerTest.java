package controller;

import controller.MainController;
import model.CurrentGameState;
import model.boards.token.*;
import model.cards.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterControllerTest {

    MainController controllerTest = new MainController(2, true);

    /** Creates two teams of one player each.
     Runs the standard Setup procedure.
     Assigns a sufficient number of coins to the players.
     */
    public static void setupTest(MainController controller)
    {
        controller.AddPlayer(0, "jack", 8, Wizard.LORD );
        controller.AddPlayer(1, "fede", 8, Wizard.DRUID);
        controller.Setup();
        controller.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controller.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
    }


    @Test
    /** Checks if the function pickCard, in charge of moving a card from the CharDeck
     to the ActiveCharDeck and modifying the economy of the game, is working properly.
     */
    public void testPickCard() {

        //Does a basic setup and checks if the desired number of coins has been correctly assigned
        setupTest(controllerTest);
        assertEquals(6, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(5, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        //Saves the reference to the card and its initial cost
        CharacterCard card = controllerTest.getGame().getCurrentCharacterDeck().getCard(0);
        int baseInitialCost = card.getBaseCost();

        //picks the card
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        //Initially it checks if the decks have been manipulated correctly
        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getIdCard());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());

        //Then it checks if the card values and the economy have been updated correctly
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getUses());
        assertEquals(baseInitialCost + 1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCurrentCost());
        assertEquals(6 - baseInitialCost, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals((baseInitialCost - 1) + 18, controllerTest.getGame().getBankBalance());
    }

    @Test
    public void deckManagement()
    {
        setupTest(controllerTest);

        //Picks all three cards in the CharDeck
        for(int i=0; i<3; i++)
            controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame());

        //Checks if the sizes of the decks have been handled correctly (2 cards in the Active, on in the Char)
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        //Checks if the correct Card has been acted upon by comparing the IDs of the cards in the Active and in the CharDeck.
        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i).getIdCard());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getIdCard());
    }

    @Test
    public void testGetPickedCard ()
    {
        setupTest(controllerTest);

        //Creates a dummy card
        Knight testCard = new Knight();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterCard card = controllerTest.getCharacterController().getPickedCard();

        //Verifies if the method returns the right card by comparing the correct ID with the dummy's ID
        assertEquals(7, card.getIdCard());
    }

    @Test
    public void isPickable()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        assertTrue(controllerTest.getCharacterController().isPickable(controllerTest.getGame(), 2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(-3);
        System.out.println(controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        assertFalse(controllerTest.getCharacterController().isPickable(controllerTest.getGame(), 2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));
    }

    @Test
    public void isEffectPlayable()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertTrue(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), 2));
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame());

        assertFalse(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), 3));
    }

    @Test
    public void getCardByID()
    {
        setupTest(controllerTest);

        Herald testCard1 = new Herald();
        EffectTestsUtility.setDecks(testCard1, controllerTest.getGame());
        Knight testCard2 = new Knight();

        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0));

        CharacterCard resultCard = controllerTest.getCharacterController().getCardByID(controllerTest.getGame(), 7);

        assertNull(controllerTest.getCharacterController().getCardByID(controllerTest.getGame(), 6));
        assert(resultCard instanceof Knight);
        assert(!(resultCard instanceof Herald));
    }

    @Test
    public void playEffect()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().playEffect(2, controllerTest.getGame(), -1, 3, null, null);

        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());


        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getIdCard());
    }
}
