package model.cards;

import controller.MainController;
import model.CurrentGameState;
import model.boards.token.Col;

public class Postman extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Postman()
    {
        super();
        this.baseCost=1;
        this.currentCost=this.baseCost;
        this.idCard=4;
    }


    /** Adds 2 to the active players' maximum mother nature movement field
     * @param game  an instance of the game
     * @param currentPlayer  the name of the player who plays the effect
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        MainController.findPlayerByName(game, currentPlayer).updateMaxMotherMovement(2);
    }


    public int getIdCard() {
        return idCard;
    }
}
