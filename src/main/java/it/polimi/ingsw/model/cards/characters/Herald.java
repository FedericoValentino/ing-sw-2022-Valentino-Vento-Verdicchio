package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

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
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        if(!game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).getMotherNature())
            game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).updateMotherNature();
        game.solveEverything(chosenIsland.get(0));
        game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).updateMotherNature();
        game.notify(game.modelToJson());
    }


}