package model.cards;


import model.boards.Pouch;
import model.boards.token.Student;

import java.util.ArrayList;

public class Princess extends CharacterCard {

    private int idCard;
    private ArrayList<Student> students;

    /** Class constructor */
    public Princess() {
        super(); //costruttore sopra classe
        this.baseCost=2;
        this.idCard=11;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getIdCard() {
        return idCard;
    }
}
