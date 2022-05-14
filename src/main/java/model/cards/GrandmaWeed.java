package model.cards;

import model.CurrentGameState;
import model.boards.token.Col;

import java.io.Serializable;

public class GrandmaWeed extends CharacterCard implements Serializable {

    private int noEntry;
    private int idCard;

    /** Class constructor */
    public GrandmaWeed()
    {
        super();
        this.noEntry = 4;
        this.baseCost=2;
        this.currentCost=this.baseCost;
        this.idCard=5;
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


    public int getIdCard() {
        return idCard;
    }
    public int getNoEntry(){return noEntry;}
}
