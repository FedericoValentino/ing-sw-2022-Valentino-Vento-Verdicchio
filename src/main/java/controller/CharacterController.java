package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.cards.*;

public class CharacterController
{
    private CharacterCard pickedCard;


    /** Takes the selected card from the CharDeck and puts it in the ActiveDeck;
     handles the economy related to this action
     * @param game  an instance of the game
     * @param player   the player interacting with the card
     * @param position  the position of the chosen card in the deck
     */
    public void pickCard(CurrentGameState game, int position, Player player)
    {
        //drawCard updates the cost and the uses of the selected card
        pickedCard = game.getCurrentCharacterDeck().drawCard(position);
        game.getCurrentActiveCharacterCard().add(pickedCard);

        /*Updates player balance and bank balance using the currentCost-1 of the card,
        which is how much the card cost when it was played  */
        int gain = pickedCard.getCurrentCost() - 1;
        game.updateBankBalance(player, gain);
        game.notify(game.modelToJson());
    }


    /** Checks if the desired card can be picked, by comparing its ID with the cards in the CharacterDeck
     * @param game  an instance of the game
     * @param characterID  the ID of the desired card
     * @param player  the player responsible for the action
     * @return ture if the card is present, false if not
     */
    public static boolean isPickable(CurrentGameState game, int characterID, Player player)
    {
        for(int i=0; i<game.getCurrentCharacterDeck().getDeck().size(); i++)
            if(game.getCurrentCharacterDeck().getDeck().get(i).getIdCard() == characterID)
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
     * @param characterID  the ID of the desired card
     * @return true if the card is present, false if not
     */
    public boolean isEffectPlayable(CurrentGameState game, int characterID)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
            if(game.getCurrentActiveCharacterCard().get(i).getIdCard() == characterID)
                return true;
        return false;
    }

    public CharacterCard getCardByID(CurrentGameState game, int characterID)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
            if(game.getCurrentActiveCharacterCard().get(i).getIdCard() == characterID)
                return game.getCurrentActiveCharacterCard().get(i);
        return null;
    }

    /** Finds the card that has been used in the ActiveCharDeck, removes it from there,
     and places it, with updated values, in the CharacterDe
     * @param game  an instance of the game
     */
    public static void deckManagement(CurrentGameState game)
    {
        CharacterCard card = game.getCurrentActiveCharacterCard().get(0);
        game.getCurrentActiveCharacterCard().clear();
        game.getCurrentCharacterDeck().getDeck().add(card);
    }

    public void playEffect(int cardID, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        if(isEffectPlayable(game, cardID))
        {
            CharacterCard card = getCardByID(game, cardID);
            card.effect(game, studentPosition, chosenIsland, currentPlayer, color);
            deckManagement(game);
        }
    }


    public CharacterCard getPickedCard()
    {
        return pickedCard;
    }
}
