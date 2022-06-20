package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.ArrayList;


public class Cook extends CharacterCard implements Serializable {

    /** Class constructor */
    public Cook()
    {
        super();
        super.baseCost = 2;
        super.currentCost = super.baseCost;
        super.name = CharacterName.COOK;
    }


    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        game.giveProfessors(true);
    }
}
