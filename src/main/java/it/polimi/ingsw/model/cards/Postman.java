package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.Arrays;

public class Postman extends CharacterCard implements Serializable {

    /** Class constructor */
    public Postman()
    {
        super();
        super.baseCost=1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.POSTMAN;
    }


    /** Adds 2 to the active players' maximum mother nature movement field
     * @param game  an instance of the game
     * @param currentPlayer  the name of the player who plays the effect
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        MainController.findPlayerByName(game, currentPlayer).updateMaxMotherMovement(2);
        game.notify(game.modelToJson());
    }

}
