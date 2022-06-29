package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Grandma herbs prevents the influence calculation on an island, by placing special game tiles
 */
public class GrandmaHerbs extends CharacterCard implements Serializable {

    private int noEntry;

    /**
     * Class constructor
     */
    public GrandmaHerbs()
    {
        super();
        this.noEntry = 4;
        super.baseCost=2;
        super.currentCost=super.baseCost;
        super.name = CharacterName.GRANDMA_HERBS;
    }

    /**
     * Updates the noEntry tiles using an input that can be positive or negative
     * @param value  the value to sum to the current amount of NoEntry tiles
     */
    public void updateNoEntry(int value){noEntry += value;}


    /**
     * Sets the noEntry field on the desired island to true; decrements the noEntry field on the card by 1
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice not used here
     * @param chosenIsland the selected island
     * @param currentPlayer not used here
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).updateNoEntry();
        updateNoEntry(-1);
        game.notify(game.modelToJson());
    }

    public int getNoEntry(){return noEntry;}
}
