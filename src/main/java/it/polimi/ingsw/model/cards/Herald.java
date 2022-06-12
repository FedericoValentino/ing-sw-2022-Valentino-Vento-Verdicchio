package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.Arrays;

public class Herald extends CharacterCard implements Serializable {

    /** Class constructor */
    public Herald()
    {
        super();
        super.baseCost=3;
        super.currentCost=super.baseCost;
        super.name = CharacterName.HERALD;
    }


    /** Resolves the influence calculation on the island as if MN has ended there her movement
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        if(!game.getCurrentIslands().getIslands().get(chosenIsland).getMotherNature())
            game.getCurrentIslands().getIslands().get(chosenIsland).updateMotherNature();
        game.solveEverything(chosenIsland);
        game.getCurrentIslands().getIslands().get(chosenIsland).updateMotherNature();
    }


}