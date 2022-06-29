package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.Island;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Centaur is a "Influence calculation" card, and as such it will be called upon the influence calculation on an island
 */
public class Centaur extends CharacterCard implements Serializable {


    /**
     * Class constructor
     */
    public Centaur()
    {
        super();
        super.baseCost = 3;
        super.currentCost = super.baseCost;
        super.name = CharacterName.CENTAUR;
    }


    /**
     * Removes the towers from the desired island before calculating the influence. After the influence calculation, if
     * the Ownership didn't switch, puts the towers back onto the island.
     * @param game  an instance of the game
     * @param firstChoice not used here
     * @param secondChoice  the island on which the influence calculation must occur
     * @param currentPlayer not used here
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color)
    {
        Island island = game.getCurrentIslands().getIslands().get(secondChoice.get(0));
        int previousTowers = island.getTowerNumber();
        ColTow previousOwner = island.getOwnership();
        island.setTowerNumber(0);
        game.solveEverything(secondChoice.get(0));
        if(previousOwner.equals(island.getOwnership()))
            island.setTowerNumber(previousTowers);
        game.notify(game.modelToJson());
    }


}
