package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
//y
import java.io.Serializable;
import java.util.ArrayList;

public class Jester extends CharacterCard implements Serializable {

    private ArrayList<Student> students = new ArrayList<>();


    /** Class constructor */
    public Jester()
    {
        super();
        super.baseCost = 1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.JESTER;
    }

    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> cardStudentIndexes, ArrayList<Integer> entranceStudentsIndexes, String currentPlayer, Col color)
    {
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

        EffectsUtilities.swapStudents(toDining, cardStudentIndexes, students);
        EffectsUtilities.swapStudents(students, entranceStudentsIndexes, player.getSchool().getEntrance());
        player.getSchool().getEntrance().addAll(toDining);
        game.notify(game.modelToJson());
    }



    /** Adds one student from the pouch to the collection
     * @param pouch  the current game pouch
     */
    public void updateStudents(Pouch pouch)
    {
        students.add(pouch.extractStudent());
    }


    /** Returns the student selected, eliminating it from the collection
     * @param index  the position of the desired student on the Character Card
     * @return the selected student
     */
    public Student getStudent(int index)
    {
        Student student;
        student = students.get(index);
        students.remove(index);
        return student;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
