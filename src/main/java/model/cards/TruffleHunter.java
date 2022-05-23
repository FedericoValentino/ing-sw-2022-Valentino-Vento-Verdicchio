package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.CharacterName;
import model.boards.token.Col;
import model.boards.token.Student;

import java.io.Serializable;

public class TruffleHunter extends CharacterCard implements Serializable {
    private Col ChosenColor;

    /** Class constructor */
    public TruffleHunter()
    {
        super();
        super.baseCost=3;
        super.name = CharacterName.TRUFFLE_HUNTER;
        super.currentCost=super.baseCost;
        this.ChosenColor = null;
    }

    /** Updates the color chosen by the player at the moment of activation
     * @param c  the color chosen by the player that needs to be ignored during the influence calculation
     */
    public void setChosenColor(Col c)
    {
        this.ChosenColor = c;
    }


    /** Ignores a color of student in the influence calculation
     * @param game  an instance of the game
     * @param color  the color of student not to take into consideration during the influence calculation
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        /*Uses this for cycle to remove the students of the selected color from the island: uses a
        counter to save how many students were removed  */
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.get(i).getColor() == color)
            {
                game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.remove(i);
                cont++;
            }
        }

        ActionController.solveEverything(game, chosenIsland);

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.add(new Student(color));
        }
    }

    @Override
    public String[] description() {
        return new String[0];
    }


    public Col getChosenColor()
    {
        return ChosenColor;
    }

}
