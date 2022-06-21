package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public final class EffectsUtilities
{
    protected static void swapStudents(ArrayList<Student> toFill, ArrayList<Integer> indexes, ArrayList<Student> toEmpty)
    {
        for(Integer index: indexes)
            toFill.add(toEmpty.get(index));
        toEmpty.removeAll(toFill);
    }

    protected static void adjustCheckpoints(School school)
    {
        for(Col c : Col.values())
        {
            if(school.getDiningRoom()[c.ordinal()] < school.getRoomCheckpoints()[c.ordinal()] - 3)
            {
                school.updateCheckpoint(c.ordinal(), false);
            }
        }
    }
}
