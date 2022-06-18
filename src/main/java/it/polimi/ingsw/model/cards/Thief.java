package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
//y
import java.io.Serializable;

public class Thief extends CharacterCard implements Serializable {

    private Col chosenColor;

    /** Class constructor */
    public Thief()
    {
        super();
        super.baseCost = 3;
        super.currentCost = super.baseCost;
        super.name = CharacterName.THIEF;
    }


    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color) {

    }

    /** Updates the color chosen by the player at the moment of activation
     * @param c  the color chosen by the player that needs to be ignored during the influence calculation
     */
    public void setChosenColor(Col c)
    {
        this.chosenColor = c;
    }

    public Col getChosenColor()
    {
        return chosenColor;
    }
}
