package it.polimi.ingsw.model.cards.characters;


import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Princess allows the player to select one of the card students and places it in his dining room
 */
public class Princess extends CharacterCard implements Serializable {

    private ArrayList<Student> students;


    /**
     * Class constructor
     */
    public Princess() {
        super();
        super.baseCost = 2;
        super.name = CharacterName.PRINCESS;
        super.currentCost = super.baseCost;
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student from the pouch to the collection
     * @param pouch  the current game pouch
     */
    public void updateStudents(Pouch pouch)
    {
        students.add(pouch.extractStudent());
    }


    /**
     * Returns the selected student, removing it from the collection
     * @param index  the position of the desired student onto the Character Card
     * @return the desired student
     */
    public Student getStudent(int index)
    {
        Student student;
        student = students.get(index);
        students.remove(index);
        return student;
    }

    /**
     * Takes a student from the card at the desired position, saves its color; then finds the active player
     * and obtains its school, placing the student in the dining room (updating the dining room structure using the student's color)
     * @param game an instance of the game, needed to operate at a high level of access
     * @param studentPosition a list containing the index of the selected card student
     * @param chosenIsland not used here
     * @param currentPlayer the name of the currentPlayer, the one who played the card
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color)
    {
        color = getStudent(studentPosition.get(0)).getColor();
        MainController.findPlayerByName(game, currentPlayer).getSchool().placeInDiningRoom(color);
        updateStudents(game.getCurrentPouch());
        game.giveProfessors(false);
        game.notify(game.modelToJson());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

}
