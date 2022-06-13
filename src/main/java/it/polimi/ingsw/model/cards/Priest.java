package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.Col;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Priest extends CharacterCard implements Serializable {

    private ArrayList<Student> students;

    /** Class constructor */
    public Priest() {
        super();
        super.baseCost=1;
        super.currentCost=super.baseCost;
        super.name = CharacterName.PRIEST;
        this.students = new ArrayList<>();
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

    /** Takes a Student from the Priest card residing at the desired position; places it on the
     chosen island. Then, refills the Priest card with another student from the pouch.
     * @param game  an instance of the game
     * @param studentPosition  the position of the chosen student onto the Priest card
     * @param chosenIsland  the island on which the student must be placed
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().placeToken(getStudent(studentPosition), chosenIsland);
        updateStudents(game.getCurrentPouch());
        game.notify(game.modelToJson());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}