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

/**
 * Jester lets the player swap up to three students between his school entrance and the card itself
 */
public class Jester extends CharacterCard implements Serializable {

    private ArrayList<Student> students = new ArrayList<>();


    /**
     * Class constructor
     */
    public Jester()
    {
        super();
        super.baseCost = 1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.JESTER;
    }

    /**
     * The method searches for the currentPlayer, then uses a support list, toEntrance, to swap the students between the
     * card and the player's entrance
     * @param game an instance of the game, needed to operate at a high level of access
     * @param cardStudentIndexes list containing the indexes of the students on the card
     * @param entranceStudentsIndexes list containing the indexes of the students in the entrance
     * @param currentPlayer the player who activated the card
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> cardStudentIndexes, ArrayList<Integer> entranceStudentsIndexes, String currentPlayer, Col color)
    {
        Player player = EffectsUtilities.searchForCurrentPlayer(currentPlayer, game.getCurrentTeams());

        ArrayList<Student> toEntrance = new ArrayList<>();

        EffectsUtilities.swapStudents(toEntrance, cardStudentIndexes, students);
        EffectsUtilities.swapStudents(students, entranceStudentsIndexes, player.getSchool().getEntrance());
        player.getSchool().getEntrance().addAll(toEntrance);
        game.notify(game.modelToJson());
    }



    /**
     * Adds one student from the pouch to the collection
     * @param pouch  the current game pouch
     */
    public void updateStudents(Pouch pouch)
    {
        students.add(pouch.extractStudent());
    }


    /**
     * Returns the student selected, eliminating it from the collection
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
