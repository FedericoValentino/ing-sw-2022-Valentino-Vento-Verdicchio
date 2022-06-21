package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class CharacterCard implements Serializable {
    protected CharacterName name;
    protected int baseCost;
    protected int uses;
    protected int currentCost;

    /** BaseCost, ID, and other characteristics, are detailed in the constructor of each card */
    public CharacterCard()
    {
        this.uses = 0;
    }

    /** Updates the uses of the card upon activation and updates the currentCost */
    public void updateCost() {
        this.uses++;
        currentCost = baseCost + uses;
    }

    public abstract void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color);


    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
    public CharacterName getCharacterName() {return name;}
}
