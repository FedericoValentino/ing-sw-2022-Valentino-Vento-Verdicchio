package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.Arrays;

public class Centaur extends CharacterCard implements Serializable {


    /** Class constructor */
    public Centaur()
    {
        super();
        super.baseCost = 3;
        super.currentCost = super.baseCost;
        super.name = CharacterName.CENTAUR;
    }



    /** Removes the towers from the desired island before calculating the influence
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland).towerNumber = 0;
        game.solveEverything(chosenIsland);
    }


}
