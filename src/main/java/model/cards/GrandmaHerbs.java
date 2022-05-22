package model.cards;

import model.CurrentGameState;
import model.boards.token.CharacterName;
import model.boards.token.Col;

import java.io.Serializable;
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
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland).updateNoEntry();
        updateNoEntry(-1);
    }

    @Override
    public String[] description()
    {
        String[] grandmaDescription = new String[7];
        Arrays.fill(grandmaDescription, "");
        grandmaDescription[0] += "Grandma Herbs has the power to place up to four No Entry tile onto an island of your choice (one per island).";
        grandmaDescription[1] += "You may ask yourself \"But why would I do that?\". Well, if Mother Nature ends her movement on ";
        grandmaDescription[2] += "said island, there won't be any influence calculation happening. It's the power of the magical herbs!";
        grandmaDescription[3] += "Use this ability to prevent your islands from being captured by your foes, but be advised: once ";
        grandmaDescription[4] += "Mother Nature ends her movement on an island sporting a No Entry tile, said No Entry will be consumed, and thus ";
        grandmaDescription[5] += "it will return on the Grandma Herbs card.";
        return grandmaDescription;
    }

    public int getNoEntry(){return noEntry;}
}
