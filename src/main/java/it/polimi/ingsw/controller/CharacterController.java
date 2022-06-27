package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.characters.TruffleHunter;

import java.util.ArrayList;

public class CharacterController
{
    private CharacterCard pickedCard;


    /** Takes the selected card from the CharDeck and puts it in the ActiveDeck;
     handles the economy related to this action
     * @param game  an instance of the game
     * @param player   the player interacting with the card
     * @param characterName  the type of the chosen card in the deck
     */
    public void pickCard(CurrentGameState game, CharacterName characterName, Player player)
    {
        //drawCard updates the cost and the uses of the selected card
        pickedCard = game.getCurrentCharacterDeck().drawCard(getCardByName( characterName, game.getCurrentCharacterDeck().getDeck()));
        game.getCurrentActiveCharacterCard().add(pickedCard);

        /*Updates player balance and bank balance using the curentCost-1 of the card,
        which is how much the card cost when it was played  */
        int gain = pickedCard.getCurrentCost() - 1;
        game.updateBankBalance(player, gain);
    }


    /** Checks if the desired card can be picked, by comparing its ID with the cards in the CharacterDeck
     * @param game  an instance of the game
     * @param characterName  the type of the desired card
     * @param player  the player responsible for the action
     * @return ture if the card is present, false if not
     */
    public static boolean isPickable(CurrentGameState game, CharacterName characterName, Player player)
    {
        for(int i=0; i<game.getCurrentCharacterDeck().getDeck().size(); i++)
            if(game.getCurrentCharacterDeck().getDeck().get(i).getCharacterName() == characterName)
            {
                CharacterCard card = game.getCurrentCharacterDeck().getDeck().get(i);
                if(player.getCoinAmount() >= card.getCurrentCost())
                    return true;
            }
        return false;
    }


    /** Checks if the effect of the desired card can be activated, by comparing the ID of the card with
     the cards into the CurrentActiveCharacterCard list
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

    public CharacterCard getCardByName(CharacterName characterName, ArrayList<CharacterCard> deck)
    {
        for (CharacterCard characterCard : deck)
            if (characterCard.getCharacterName() == characterName)
                return characterCard;
        return null;
    }

    /** Finds the card that has been used in the ActiveCharDeck, removes it from there,
     and places it, with updated values, in the CharacterDe
     * @param game  an instance of the game
     */
    public static void deckManagement(CurrentGameState game)
    {
        if(game.getCurrentCharacterDeck() != null)
        {
            if (!game.getCurrentActiveCharacterCard().isEmpty())
            {
                CharacterCard card = game.getCurrentActiveCharacterCard().get(0);
                game.getCurrentActiveCharacterCard().remove(0);
                game.getCurrentCharacterDeck().getDeck().add(card);
            }
        }
    }

    public void playEffect(CharacterName characterName, CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        if(isEffectPlayable(game, characterName))
        {
            CharacterCard card = getCardByName(characterName, game.getCurrentActiveCharacterCard());
            card.effect(game, studentPosition, chosenIsland, currentPlayer, color);
        }
    }

    public void setTruffleHunterColor(CurrentGameState game, Col studentColor)
    {
        for(CharacterCard card: game.getCurrentActiveCharacterCard())
        {
            if(card.getCharacterName().equals(CharacterName.TRUFFLE_HUNTER))
                ((TruffleHunter) card).setChosenColor(studentColor);
        }

    }

    public CharacterCard getPickedCard()
    {
        return pickedCard;
    }
}
