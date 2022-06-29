package it.polimi.ingsw.controller;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.characters.Herald;
import it.polimi.ingsw.model.cards.characters.Knight;
import it.polimi.ingsw.model.cards.characters.TruffleHunter;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CharacterControllerTest {

    MainController controllerTest = new MainController(2, true);

    /**
     * Checks if the function pickCard, in charge of moving a card from the CharDeck
     * to the ActiveCharDeck and modifying the economy of the game, is working properly.
     */
    @Test
    public void testPickCard() {

        //Does a basic setup and checks if the desired number of coins has been correctly assigned
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.gainCoins(controllerTest);
        assertEquals(104, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(103, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        //Saves the reference to the card and its initial cost
        CharacterCard card = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0);
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
        assertEquals(104 - baseInitialCost, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals((baseInitialCost - 1) + 18, controllerTest.getGame().getBankBalance());
    }

    @Test
    public void deckManagement()
    {
        TestUtilities.setupTestFor2(controllerTest);

        CharacterName cardName1 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getCharacterName();
        CharacterName cardName2 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(1).getCharacterName();
        CharacterName cardName3 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(2).getCharacterName();

        //Picks all three cards in the CharDeck
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        CharacterController.deckManagement(controllerTest.getGame());

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
        TestUtilities.setupTestFor2(controllerTest);

        //Creates a dummy card
        Knight testCard = new Knight();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.KNIGHT, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterCard card = controllerTest.getCharacterController().getPickedCard();

        //Verifies if the method returns the right card by comparing the correct ID with the dummy's ID
        assertEquals(CharacterName.KNIGHT, card.getCharacterName());
    }

    @Test
    public void isPlayable()
    {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.gainCoins(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        assertTrue(CharacterController.isPlayable(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(-103);
        System.out.println(controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        assertFalse(CharacterController.isPlayable(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));
    }

    @Test
    public void isEffectPlayable()
    {
        TestUtilities.setupTestFor2(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertTrue(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), CharacterName.HERALD));
        CharacterController.deckManagement(controllerTest.getGame());

        assertFalse(controllerTest.getCharacterController().isEffectPlayable(controllerTest.getGame(), CharacterName.HERALD));
    }

    @Test
    public void getCardByName()
    {
        TestUtilities.setupTestFor2(controllerTest);

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
            CharacterController.deckManagement(controllerTest.getGame());

        CharacterCard resultCard3 = controllerTest.getCharacterController().getCardByName(CharacterName.KNIGHT, controllerTest.getGame().getCurrentCharacterDeck().getDeck());
        assert(resultCard3 instanceof Knight);

        CharacterCard resultCard4 = controllerTest.getCharacterController().getCardByName(CharacterName.HERALD, controllerTest.getGame().getCurrentCharacterDeck().getDeck());
        assert(resultCard4 instanceof Herald);
    }

    @Test
    public void playEffect()
    {
        TestUtilities.setupTestFor2(controllerTest);

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

    @Test
    public void testSetTruffleHunterColor()
    {
        TestUtilities.setupTestFor2(controllerTest);

        TruffleHunter testCard = new TruffleHunter();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.TRUFFLE_HUNTER, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        controllerTest.getCharacterController().setTruffleHunterColor(controllerTest.getGame(), Col.BLUE);

        assertEquals(Col.BLUE, testCard.getChosenColor());
    }
}
