package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.Arrays;

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
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        /*Uses this for cycle to remove the students of the selected color from the island: uses a
        counter to save how many students were removed  */
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(chosenIsland).getCurrentStudents().size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(chosenIsland).getCurrentStudents().get(i).getColor() == getChosenColor())
            {
                game.getCurrentIslands().getIslands().get(chosenIsland).getCurrentStudents().remove(i);
                cont++;
            }
        }

        game.solveEverything(chosenIsland);

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(chosenIsland).getCurrentStudents().add(new Student(getChosenColor()));
        }
        game.notify(game.modelToJson());
    }

    public Col getChosenColor()
    {
        return ChosenColor;
    }

}
