package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.characters.TruffleHunter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CharacterController
{

    /**
     * Takes the selected card from the CharDeck and puts it in the ActiveDeck;
     * handles the economy related to this action. It is necessary to call a "notify" here, even if not proper procedure,
     * to ensure that the GUI visualization works completely and reliably with all cards
     * @param game  an instance of the game
     * @param player   the player interacting with the card
     * @param characterName  the type of the chosen card in the deck
     */
    public void pickCard(CurrentGameState game, CharacterName characterName, Player player)
    {

        CharacterCard pickedCard = game.getCurrentCharacterDeck().drawCard(getCardByName( characterName, game.getCurrentCharacterDeck().getDeck()));
        game.getCurrentActiveCharacterCard().add(pickedCard);

        int gain = pickedCard.getCurrentCost() - 1;
        game.updateBankBalance(player, gain);
        game.notify(game.modelToJson());
    }


    /**
     * Checks if the desired card can be picked, by comparing its ID with the cards in the CharacterDeck. Also, it checks if
     * there aren't any other active cards, by making sure the ActiveDeck is empty, and if the player has enough coin
     * to afford the card
     * @param game  an instance of the game
     * @param characterName  the type of the desired card
     * @param player  the player responsible for the action
     * @return ture if the card can be played, false in the opposite case
     */
    public static boolean canBePicked(CurrentGameState game, CharacterName characterName, Player player)
    {
        for(int i=0; i<game.getCurrentCharacterDeck().getDeck().size(); i++)
            if(game.getCurrentCharacterDeck().getDeck().get(i).getCharacterName() == characterName && game.getCurrentActiveCharacterCard().isEmpty())
            {
                CharacterCard card = game.getCurrentCharacterDeck().getDeck().get(i);
                if(player.getCoinAmount() >= card.getCurrentCost())
                    return true;
            }
        return false;
    }


    /**
     * Checks if the effect of the desired card can be activated, by comparing the ID of the card with
     * the cards into the CurrentActiveCharacterCard list
     * @param game  an instance of the game
     * @param characterName the type of the desired card
     * @return true if the card is present, false if not
     */
    public boolean isEffectPlayable(CurrentGameState game, CharacterName characterName)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
            if(game.getCurrentActiveCharacterCard().get(i).getCharacterName() == characterName)
                return true;
        return false;
    }


    /**
     * Finds the characterCard corresponding to the given CharacterName inside the given list of cards
     * @param characterName the name of the card to find
     * @param deck the list of cards
     * @return the card if it's present, null if it's absent
     */
    protected static CharacterCard getCardByName(CharacterName characterName, ArrayList<CharacterCard> deck)
    {
        for (CharacterCard characterCard : deck)
            if (characterCard.getCharacterName() == characterName)
                return characterCard;
        return null;
    }


    /**
     * Finds the card that has been used in the ActiveCharDeck, removes it from there,
     * and places it, with updated values, in the CharacterDeck. In the end, sorts the characterDeck to reorder the cards
     * in the order they were before the card was picked: this is paramount to guarantee consistency in the view.
     * @param game  an instance of the game
     */
    public static void deckManagement(CurrentGameState game)
    {
        if(game.getExpertMode())
        {
            if (!game.getCurrentActiveCharacterCard().isEmpty())
            {
                CharacterCard card = game.getCurrentActiveCharacterCard().get(0);
                game.getCurrentActiveCharacterCard().remove(0);
                game.getCurrentCharacterDeck().getDeck().add(card);
            }
        }
        Collections.sort(game.getCurrentCharacterDeck().getDeck(), Comparator.comparingInt(CharacterCard::getDeckIndex));
    }

    /**
     * Plays the effect of the desired card found in the Active Deck. According to the strategy pattern used, the correct
     * overridden effect will be played
     * @param characterName the name of the character
     * @param game an instance of the game
     * @param firstChoice a list of integers that can represent different things based on the card played
     * @param secondChoice a list of integers that can represent different things based on the card played
     * @param currentPlayer the current player's nickname
     * @param color a parameter of type Col, useful for some cards
     */
    public void playEffect(CharacterName characterName, CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color)
    {
        if(isEffectPlayable(game, characterName))
        {
            CharacterCard card = getCardByName(characterName, game.getCurrentActiveCharacterCard());
            card.effect(game, firstChoice, secondChoice, currentPlayer, color);
        }
    }

    /**
     * Sets the color of Truffle Hunter to the desired color chosen by the player upon card effect activation
     * @param game an instance of the game
     * @param studentColor the color chosen by the player
     */
    public void setTruffleHunterColor(CurrentGameState game, Col studentColor)
    {
        for (CharacterCard card : game.getCurrentActiveCharacterCard())
        {
            if (card.getCharacterName().equals(CharacterName.TRUFFLE_HUNTER))
                ((TruffleHunter) card).setChosenColor(studentColor);
            }
    }

}
