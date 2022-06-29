package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cook operates by granting the player who played it the control of professors even in case of a draw
 */
public class Cook extends CharacterCard implements Serializable {

    /**
     * Class constructor
     */
    public Cook()
    {
        super();
        super.baseCost = 2;
        super.currentCost = super.baseCost;
        super.name = CharacterName.COOK;
    }

    /**
     * It simply calls the method to run the professors check upon activation, giving a "true" input: this means that, in case
     * of a draw, the professor will still be assigned to the player who played the card
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice not used here
     * @param secondChoice not used here
     * @param currentPlayer not used here
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color)
    {
        game.giveProfessors(true);
        game.notify(game.modelToJson());
    }
}
