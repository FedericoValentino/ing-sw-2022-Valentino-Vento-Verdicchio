package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Character Card is an abstract class from which each Character Card subclass inherits base common attributes and methods.
 * The effect method, as for the strategy pattern, is overridden by each particular card.
 * Regarding the implementation of Serializable interface, due to the intrinsic dynamic nature of the character cards, it
 * is difficult to serialize them with a json file with the rest of the currentGameState without incurring in copious runtime
 * problems; our solution is to handle them separately.
 */
public abstract class CharacterCard implements Serializable {
    protected CharacterName name;
    protected int baseCost;
    protected int uses;
    protected int currentCost;
    protected int deckIndex;

    /**
     * Class constructor. BaseCost, ID, and other characteristics, are detailed in the constructor of each card
     */
    public CharacterCard()
    {
        this.uses = 0;
    }

    /**
     * Updates the uses of the card upon activation and updates the currentCost
     */
    public void updateCost() {
        this.uses++;
        currentCost = baseCost + uses;
    }

    /**
     * The effect each card must have. This method will be overridden by the cards. By default, it receives a number of
     * useful input parameters and structures that will be useful to the cards.
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice a list of integers that can represent multiple things: the position of a student on a card, multiple
     *                    island positions, different color values. Using a list gives us room to add new cards that may require
     *                    different amount of choices to make
     * @param secondChoice the same as firstChoice
     * @param currentPlayer the name of the currentPlayer, the one who played the card
     * @param color representing a student color, useful for cards that require to choose a color
     */
    public abstract void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color);

    public void setDeckIndex(int index)
    {
        this.deckIndex = index;
    }

    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
    public CharacterName getCharacterName() {return name;}
    public int getDeckIndex(){
        return deckIndex;
    }
}
