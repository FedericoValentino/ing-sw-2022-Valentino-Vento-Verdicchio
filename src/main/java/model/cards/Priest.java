package model.cards;

import model.CurrentGameState;
import model.boards.Pouch;
import model.boards.token.Student;

import java.util.ArrayList;

public class Priest extends CharacterCard {

    private int idCard;
    private ArrayList<Student> students;

    public Priest() {
        super();
        this.baseCost=1;
        this.currentCost=this.baseCost;
        this.idCard=1;
        this.students = new ArrayList<>();
    }

    //Adds one student from the pouch to the collection
    public void updateStudents(Pouch pouch)
        {
            students.add(pouch.extractStudent());
        }

    public ArrayList<Student> getStudents() {
        return students;
    }

    //Returns the student selected, eliminating it from the collection
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