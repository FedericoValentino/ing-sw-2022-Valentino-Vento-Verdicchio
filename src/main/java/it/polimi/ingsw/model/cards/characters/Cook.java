package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

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
        game.notify(game.modelToJson());
    }
}
