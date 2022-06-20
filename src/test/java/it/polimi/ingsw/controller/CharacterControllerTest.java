package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Wizard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.EffectTestsUtility;
import it.polimi.ingsw.model.cards.Herald;
import it.polimi.ingsw.model.cards.Knight;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CharacterControllerTest {

    MainController controllerTest = new MainController(2, true);

    /** Creates two teams of one player each.
     Runs the standard setup procedure.
     Assigns a sufficient number of coins to the players.
     */
    public static void setupTest(MainController controller)
    {
        controller.addPlayer(0, "jack", 8, Wizard.LORD );
        controller.addPlayer(1, "fede", 8, Wizard.DRUID);
        controller.setup();
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
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), card.getCharacterName(), controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        //Initially it checks if the decks have been manipulated correctly
        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card.getCharacterName(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCharacterName());
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

        CharacterName cardName1 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getCharacterName();
        CharacterName cardName2 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(1).getCharacterName();
        CharacterName cardName3 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(2).getCharacterName();

        //Picks all three cards in the CharDeck
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame());

        //Checks if the sizes of the decks have been handled correctly (2 cards in the Active, one in the Char)
        assertEquals(2, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        //Checks if the correct Card has been acted upon by comparing the IDs of the cards in the Active and in the CharDeck.
        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getCharacterName(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i).getCharacterName());
        assertEquals(card.getCharacterName(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getCharacterName());
    }

    @Test
    public void testGetPickedCard ()
    {
        setupTest(controllerTest);

        //Creates a dummy card
        Knight testCard = new Knight();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterCard card = controllerTest.getCharacterController().getPickedCard();

        //Verifies if the method returns the right card by comparing the correct ID with the dummy's ID
        assertEquals(CharacterName.KNIGHT, card.getCharacterName());
    }

    @Test
    public void isPickable()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        assertTrue(controllerTest.getCharacterController().isPickable(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(-3);
        //System.out.println(controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        assertFalse(controllerTest.getCharacterController().isPickable(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));
    }

    @Test
    public void isEffectPlayable()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertTrue(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), CharacterName.HERALD));
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame());

        assertFalse(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), CharacterName.HERALD));
    }

    @Test
    public void getCardByName()
    {
        setupTest(controllerTest);

        Herald testCard1 = new Herald();
        EffectTestsUtility.setDecks(testCard1, controllerTest.getGame());
        Knight testCard2 = new Knight();

        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0));

        CharacterCard resultCard1 = controllerTest.getCharacterController().getCardByName(CharacterName.KNIGHT, controllerTest.getGame().getCurrentActiveCharacterCard());
        assert(resultCard1 instanceof Knight);

        CharacterCard resultCard2 = controllerTest.getCharacterController().getCardByName(CharacterName.HERALD, controllerTest.getGame().getCurrentActiveCharacterCard());
        assert(resultCard2 instanceof Herald);

        for(int i=0; i<2; i++)
            controllerTest.getCharacterController().deckManagement(controllerTest.getGame());

        CharacterCard resultCard3 = controllerTest.getCharacterController().getCardByName(CharacterName.KNIGHT, controllerTest.getGame().getCurrentCharacterDeck().getDeck());
        assert(resultCard3 instanceof Knight);

        CharacterCard resultCard4 = controllerTest.getCharacterController().getCardByName(CharacterName.HERALD, controllerTest.getGame().getCurrentCharacterDeck().getDeck());
        assert(resultCard4 instanceof Herald);
    }

    @Test
    public void playEffect()
    {
        setupTest(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        ArrayList<Integer> studentPositions = new ArrayList<>();
        studentPositions.add(-1);
        ArrayList<Integer> chosenIslands = new ArrayList<>();
        chosenIslands.add(3);
        controllerTest.getCharacterController().playEffect(CharacterName.HERALD, controllerTest.getGame(), studentPositions, chosenIslands, null, null);

        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(0, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());


    }
}
