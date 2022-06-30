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
     * After a basic setup and some preliminary checks about the economy, it saves the initial cost of the card, picks it,
     * and then checks, in order:
     * - if the decks have been manipulated correctly
     * - if the card values and game economy have been handled correctly
     */
    @Test
    public void testPickCard() {

        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.gainCoins(controllerTest);
        assertEquals(104, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(103, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        CharacterCard card = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0);
        int baseInitialCost = card.getBaseCost();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), card.getCharacterName(), controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card.getCharacterName(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCharacterName());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());

        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getUses());
        assertEquals(baseInitialCost + 1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCurrentCost());
        assertEquals(104 - baseInitialCost, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals((baseInitialCost - 1) + 18, controllerTest.getGame().getBankBalance());
    }

    /**
     * Setups three different cards and picks them all, placing them in the active deck. Then, after saving the first card
     * in the active deck, deckManagement is called. The checks ensure, in order:
     * - that the sizes of the decks are what we expect them to be
     * - that the card that was acted upon was effectively the right card, by comparing IDs and values with the card we have saved
     */
    @Test
    public void deckManagement()
    {
        TestUtilities.setupTestFor2(controllerTest);

        CharacterName cardName1 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getCharacterName();
        CharacterName cardName2 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(1).getCharacterName();
        CharacterName cardName3 = controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(2).getCharacterName();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName1, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName2, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), cardName3, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        CharacterController.deckManagement(controllerTest.getGame());

        assertEquals(2, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getCharacterName(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i).getCharacterName());
        assertEquals(card.getCharacterName(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getCharacterName());
    }


    /**
     * After a game and deck setups, checks whether the card can be picked, i.e. if it is in the inactive deck and the player
     * has enough funding. After this, it decreases the player's coinAmount until it is impossible for him to afford the card.
     * Checks if the player cannot afford the card
     */
    @Test
    public void canBePicked()
    {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.gainCoins(controllerTest);

        Herald testCard = new Herald();
        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());

        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        assertTrue(CharacterController.canBePicked(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(-103);

        assertFalse(CharacterController.canBePicked(controllerTest.getGame(), CharacterName.HERALD, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0)));
    }

    /**
     * After the usual setups, checks if the card effect can be played, i.e. if the card is in the active deck. After this, it
     * calls deck management and verifies the inability by the player to now play the effect
     */
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

    /**
     * Sets up the active deck with a Herald and a knight card, and checks if we can find both cards in that structure.
     * Then calls deckManagement two times, and then does the ame checks again, but this time with the inactive deck
     */
    @Test
    public void getCardByName()
    {
        TestUtilities.setupTestFor2(controllerTest);

        Herald herald = new Herald();
        EffectTestsUtility.setDecks(herald, controllerTest.getGame());
        Knight knight = new Knight();

        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(knight);

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

    /**
     * After a basic setup of the charDecks and the input parameters, verifies if the decks have remained the same after the
     * effect has been played (as it should until the end of the turn)
     */
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

    /**
     * Sets up the deck with a truffle hunter card, then picks it and sets a certain color on it. Checks whether the color
     * on the card is exactly what we expect it to be
     */
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
