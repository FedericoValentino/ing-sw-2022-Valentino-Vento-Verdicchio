package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

//y
public class TruffleHunter extends CharacterCard implements Serializable {

    private Col chosenColor;

    /** Class constructor */
    public TruffleHunter()
    {
        super();
        super.baseCost=3;
        super.name = CharacterName.TRUFFLE_HUNTER;
        super.currentCost=super.baseCost;
        this.chosenColor = null;
    }

    /** Updates the color chosen by the player at the moment of activation
     * @param c  the color chosen by the player that needs to be ignored during the influence calculation
     */
    public void setChosenColor(Col c)
    {
        this.chosenColor = c;
    }


    /** Ignores a color of student in the influence calculation
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        /*Uses this for cycle to remove the students of the selected color from the island: uses a
        counter to save how many students were removed  */
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

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
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
