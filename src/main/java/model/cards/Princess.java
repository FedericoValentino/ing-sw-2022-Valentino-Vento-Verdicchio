package model.cards;


import controller.MainController;
import model.CurrentGameState;
import model.boards.Pouch;
import model.boards.token.CharacterName;
import model.boards.token.Col;
import model.boards.token.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class Princess extends CharacterCard implements Serializable {

    private ArrayList<Student> students;


    /** Class constructor */
    public Princess() {
        super();
        this.baseCost=2;
        this.name = CharacterName.PRINCESS;
        this.currentCost=this.baseCost;
        this.students = new ArrayList<>();
    }

    /** Adds a student from the pouch to the collection
     * @param pouch  the current game pouch
     */
    public void updateStudents(Pouch pouch)
    {
        students.add(pouch.extractStudent());
    }


    /** Returns the selected student, removing it from the collection
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

    /** Takes a student from the card at the desired position, saves its color; then finds the active player
     and obtains its school, placing the student in the dining room (updating the dining room structure using the student's color)
     * @param game  an instance of the game
     * @param studentPosition  the position of the chosen student onto the Princess card
     * @param currentPlayer  the name of the player who plays the effect
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        color = getStudent(studentPosition).getColor();
        MainController.findPlayerByName(game, currentPlayer).getSchool().placeInDiningRoom(color);
        updateStudents(game.getCurrentPouch());
    }

    @Override
    public String[] description() {
        return new String[0];
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

}
