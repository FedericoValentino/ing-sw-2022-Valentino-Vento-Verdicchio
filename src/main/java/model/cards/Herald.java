package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.Col;

import java.io.Serializable;

public class Herald extends CharacterCard implements Serializable {
    private int idCard;

    /** Class constructor */
    public Herald()
    {
        super();
        this.baseCost=3;
        this.currentCost=this.baseCost;
        this.idCard=3;
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

    public int getIdCard() {
        return idCard;
    }
}