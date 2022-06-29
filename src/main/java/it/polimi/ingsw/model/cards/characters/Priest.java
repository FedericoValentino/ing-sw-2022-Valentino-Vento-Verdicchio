package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Allows the player to send on eof the student on the card to an island of his choice
 */
public class Priest extends CharacterCard implements Serializable {

    private ArrayList<Student> students;

    /**
     * Class constructor
     */
    public Priest() {
        super();
        super.baseCost=1;
        super.currentCost=super.baseCost;
        super.name = CharacterName.PRIEST;
        this.students = new ArrayList<>();
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

    /**
     * Takes a Student from the Priest card residing at the desired position; places it on the
     * chosen island. Then, refills the Priest card with another student from the pouch.
     * @param game an instance of the game, needed to operate at a high level of access
     * @param studentPosition the position of the card student
     * @param chosenIsland the index of the chosen island
     * @param currentPlayer not used there
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().placeToken(getStudent(studentPosition.get(0)), chosenIsland.get(0));
        updateStudents(game.getCurrentPouch());
        game.notify(game.modelToJson());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}