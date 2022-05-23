package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.CharacterName;
import model.boards.token.Col;

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
        ActionController.solveEverything(game, chosenIsland);
        game.getCurrentIslands().getIslands().get(chosenIsland).updateMotherNature();
    }

    @Override
    public String[] description() {
        String[] heraldDescription = new String[7];
        Arrays.fill(heraldDescription, "");
        heraldDescription[0] += "The herald lets you ignore any rules of decency by letting you start the influence calculation ";
        heraldDescription[1] += "on an island of your choice, even if Mother Nature hasn't ended her movement there. That's right, ";
        heraldDescription[2] += "you won't need to wait for anyone to make your dreams of conquest come true!";
        heraldDescription[3] += "Use this effect in combination with Mother Nature to try and conquer more than one island ";
        heraldDescription[4] += "in just one turn!";
        return heraldDescription;
    }

}