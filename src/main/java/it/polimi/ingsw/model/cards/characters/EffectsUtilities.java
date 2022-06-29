package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

/**
 * A utility class containing methods useful to multiple character effects
 */
public final class EffectsUtilities
{
    /**
     * Using three lists, one that has to be filled, one that has to be emptied, and one containing the indexes of the
     * selected students from the toEmpty list, swaps those students between the lists
     * @param toFill list of students that needs to be refilled
     * @param indexes list of indexes of the students that need to be extracted from the toEmpty list
     * @param toEmpty list of students from which the selected students must be extracted
     */
    static void swapStudents(ArrayList<Student> toFill, ArrayList<Integer> indexes, ArrayList<Student> toEmpty)
    {
        for(Integer index: indexes)
            toFill.add(toEmpty.get(index));
        toEmpty.removeAll(toFill);
    }

    /**
     * The method resets the school checkpoints to their correct value after some students have been extracted from the
     * dining room
     * @param school the school whose dining room needs readjusting
     */
    static void adjustCheckpoints(School school)
    {
        for(Col c : Col.values())
        {
            if(school.getDiningRoom()[c.ordinal()] < school.getRoomCheckpoints()[c.ordinal()] - 3)
            {
                school.updateCheckpoint(c.ordinal(), false);
            }
        }
    }

    /**
     * Given the list of teams and the current player's name, it searches for and returns the current player object
     * @param currentPlayerName the current player's name
     * @param teams a list of present teams
     * @return the current player object
     */
    static Player searchForCurrentPlayer(String currentPlayerName, ArrayList<Team> teams)
    {
        Player player = null;

        for(Team team: teams)
        {
            for (Player p : team.getPlayers())
            {
                if(p.getName().equals(currentPlayerName))
                {
                    player = p;
                }
            }
        }
        return player;
    }
}
