package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Allows the player to perform a movement of plus two on the previous movement value
 */
public class Postman extends CharacterCard implements Serializable {

    /**
     * Class constructor
     */
    public Postman()
    {
        super();
        super.baseCost=1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.POSTMAN;
    }


    /**
     * Adds 2 to the active players' maximum mother nature movement field
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice not used here
     * @param secondChoice not used here
     * @param currentPlayer the name of the currentPlayer, the one who played the card
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color)
    {
        MainController.findPlayerByName(game, currentPlayer).updateMaxMotherMovement(2);
        game.notify(game.modelToJson());
    }

}
