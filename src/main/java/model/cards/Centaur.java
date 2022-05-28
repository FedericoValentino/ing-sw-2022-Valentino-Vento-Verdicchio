package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.CharacterName;
import model.boards.token.Col;

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

    @Override
    public String[] description()
    {
        String[] centaurDescription = new String[7];
        Arrays.fill(centaurDescription, "");
        centaurDescription[0] += "Centaur is a card of the \"influence calculation\" type.";
        centaurDescription[1] += "While this card is active, towers on the island on which mother nature lands do not count ";
        centaurDescription[2] += "towards influence calculation, as if the island wasn't owned by anyone in the first place!";
        centaurDescription[3] += "Use this card to help you conquer an island owned by your enemies!";
        return centaurDescription;
    }

}
