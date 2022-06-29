package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Truffle Hunter, a card of th "Influence Calculation" type makes the influence calculation ignore the selected color
 */
public class TruffleHunter extends CharacterCard implements Serializable {

    private Col chosenColor;

    /**
     * Class constructor
     */
    public TruffleHunter()
    {
        super();
        super.baseCost=3;
        super.name = CharacterName.TRUFFLE_HUNTER;
        super.currentCost=super.baseCost;
        this.chosenColor = null;
    }

    /**
     * Updates the color chosen by the player at the moment of activation
     * @param color  the color chosen by the player that needs to be ignored during the influence calculation
     */
    public void setChosenColor(Col color)
    {
        this.chosenColor = color;
    }


    /**
     * Removes the student of the selected color on the island, and saves their number for later. After calling the influence
     * calculation function, it creates and adds to the island the same number of students, of the same color, that were
     * removed
     * @param game an instance of the game, needed to operate at a high level of access
     * @param studentPosition not used here
     * @param chosenIsland the island on which the influence calculation must occur
     * @param currentPlayer not used here
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        int cont = 0;
        ArrayList<Student> studentsOnIsland = game.getCurrentIslands().getIslands().get(chosenIsland.get(0)).getCurrentStudents();
        for(int i=0; i < studentsOnIsland.size(); i++)
        {
            if(studentsOnIsland.get(i).getColor() == getChosenColor())
            {
                studentsOnIsland.remove(i);
                cont++;
            }
        }

        game.solveEverything(chosenIsland.get(0));

        for(int i=0; i<cont; i++)
        {
            studentsOnIsland.add(new Student(getChosenColor()));
        }
        game.notify(game.modelToJson());
    }

    public Col getChosenColor()
    {
        return chosenColor;
    }

}
