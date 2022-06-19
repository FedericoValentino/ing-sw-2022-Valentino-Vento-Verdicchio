package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GrandmaHerbs extends CharacterCard implements Serializable {

    private int noEntry;

    /** Class constructor */
    public GrandmaHerbs()
    {
        super();
        this.noEntry = 4;
        super.baseCost=2;
        super.currentCost=super.baseCost;
        super.name = CharacterName.GRANDMA_HERBS;
    }

    /** Updates the noEntry tiles using an input that can be positive or negative
     * @param value  the value to sum to the current amount of NoEntry tiles
     */
    public void updateNoEntry(int value){noEntry += value;}


    /** Sets the noEntry field on the desired island to true; decrements the noEntry field on the card by 1
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the NoEntry tile must be placed
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).updateNoEntry();
        updateNoEntry(-1);
        game.notify(game.modelToJson());
    }

    public int getNoEntry(){return noEntry;}
}
