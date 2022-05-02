package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.Col;

public class Centaur extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Centaur()
    {
        super();
        super.baseCost=3;
        super.currentCost=this.baseCost;
        this.idCard=6;
    }



    /** Removes the towers from the desired island before calculating the influence
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland).towerNumber = 0;
        ActionController.solveEverything(game, chosenIsland);
    }


    public int getIdCard() {
        return idCard;
    }
}
