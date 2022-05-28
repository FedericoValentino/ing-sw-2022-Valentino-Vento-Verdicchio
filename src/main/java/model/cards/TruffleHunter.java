package model.cards;

import controller.ActionController;
import model.CurrentGameState;
import model.boards.token.CharacterName;
import model.boards.token.Col;
import model.boards.token.Student;

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

        game.solveEverything(chosenIsland);

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.add(new Student(color));
        }
    }

    @Override
    public String[] description() {
        String[] truffleHunterDescription = new String[7];
        Arrays.fill(truffleHunterDescription, "");
        truffleHunterDescription[0] += "This peculiar individual, as the name might imply, is a mushroom enthusiast, his \"hunting\" sessions consisting ";
        truffleHunterDescription[1] += "of slow paced walks in the woods, looking under every fern and heap of grass to find another example to add to his prized possessions.";
        truffleHunterDescription[2] += "What on earth could this man offer to you? Well, for a significant cost, he will bring \"hunting\" with him all the students of ";
        truffleHunterDescription[3] += "the selected color that he will find on the island on which mother nature has ended her movement, ensuring that they do not count ";
        truffleHunterDescription[4] += "towards the influence calculation.";
        truffleHunterDescription[5] += "Use this character to help you mitigate your incompetence in gaining control of professors, or just to demonstrate that ";
        truffleHunterDescription[6] += "you too, like the Knight, can conquer a well defended island, albeit with a little help.";
        return truffleHunterDescription;
    }


    public Col getChosenColor()
    {
        return ChosenColor;
    }

}
