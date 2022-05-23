package model.cards;


import controller.MainController;
import model.CurrentGameState;
import model.boards.Pouch;
import model.boards.token.CharacterName;
import model.boards.token.Col;
import model.boards.token.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Princess extends CharacterCard implements Serializable {

    private ArrayList<Student> students;


    /** Class constructor */
    public Princess() {
        super();
        super.baseCost = 2;
        super.name = CharacterName.PRINCESS;
        super.currentCost = super.baseCost;
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
        String[] princessDescription = new String[7];
        Arrays.fill(princessDescription, "");
        princessDescription[0] += "The Princess-warrior of the lands has host of valorous and combat ready knights, always at her service. They loyalty is ";
        princessDescription[1] += "without question; but, like everything, it has a price. For as small as two coins, you can convince one of these ";
        princessDescription[2] += "skilled warriors to sit for the whole game at your dining room, patiently waiting for your - we are sure - genius ";
        princessDescription[3] += "strategies to unfold.";
        princessDescription[4] += "Use this card to grant you a significant advantage in the professors - control race!";
        return princessDescription;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

}
