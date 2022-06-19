package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class Minstrel extends CharacterCard implements Serializable {

    /** Class constructor */
    public Minstrel()
    {
        super();
        super.baseCost = 1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.MINSTREL;
    }


    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> entranceStudentIndexes, ArrayList<Integer> colorOrdinals, String currentPlayer, Col color)
    {
        Collections.sort(entranceStudentIndexes);

        Player player = null;

        for(Team t: game.getCurrentTeams())
        {
            for (Player p : t.getPlayers())
            {
                if(p.getName().equals(game.getCurrentTurnState().getCurrentPlayer()))
                {
                    player = p;
                }
            }
        }

        ArrayList<Student> toDining = new ArrayList<>();
        swapStudents(toDining, entranceStudentIndexes, player.getSchool().getEntrance());

        for(Integer colorIndex: colorOrdinals)
        {
            Student student = new Student(player.getSchool().removeFromDiningRoom(colorIndex));
            player.getSchool().placeToken(student);
        }

        for(Student student : toDining)
            player.getSchool().placeInDiningRoom(student.getColor());

        adjustCheckpoints(player.getSchool());
    }

    private void adjustCheckpoints(School school)
    {
        for(Col c : Col.values())
        {
            if(school.getDiningRoom()[c.ordinal()] < school.getRoomCheckpoints()[c.ordinal()] - 3)
            {
                school.updateCheckpoint(c.ordinal(), false);
            }
        }
    }

    private void swapStudents(ArrayList<Student> destination, ArrayList<Integer> indexes, ArrayList<Student> origin)
    {
        for(int index : indexes)
        {
            destination.add(origin.get(index));
            origin.remove(index);
            for(int i = 0; i < indexes.size(); i++)
            {
                if(indexes.get(i) != 0)
                    indexes.set(i, indexes.get(i)-1);
            }
        }
    }
}
