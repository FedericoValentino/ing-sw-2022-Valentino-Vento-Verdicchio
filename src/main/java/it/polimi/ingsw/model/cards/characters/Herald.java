package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.Island;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Herald lets the player resolve an island of his choice instantly upon activation, with no need for the presence of
 * Mother Nature
 */
public class Herald extends CharacterCard implements Serializable {

    /**
     * Class constructor
     */
    public Herald()
    {
        super();
        super.baseCost=3;
        super.currentCost=super.baseCost;
        super.name = CharacterName.HERALD;
    }


    /**
     * Resolves the influence calculation on the island as if MN has ended there her movement
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice not used here
     * @param chosenIsland the island chosen by the player
     * @param currentPlayer not used here
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        game.solveEverything(chosenIsland.get(0));
        game.notify(game.modelToJson());
    }


}