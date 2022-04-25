package model.cards;


import model.boards.Pouch;
import model.boards.token.Student;

import java.util.ArrayList;

public class Princess extends CharacterCard {

    private int idCard;
    private ArrayList<Student> students;

    public Princess() {
        super(); //costruttore sopra classe
        this.baseCost=2;
        this.idCard=11;
        this.currentCost=this.baseCost;
        this.students = new ArrayList<>();
    }

    //Adds a student from the pouch to the collection
    public void updateStudents(Pouch pouch)
    {
        students.add(pouch.extractStudent());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    //Returns the selected student, removing it from the collection
    public Student getStudent(int index)
    {
        Student student;
        student = students.get(index);
        students.remove(index);
        return student;
    }

    public int getIdCard() {
        return idCard;
    }
}
