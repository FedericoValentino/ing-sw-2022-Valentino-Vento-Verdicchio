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

    public int getIdCard() {
        return idCard;
    }
    public void updateStudents(Pouch pouch)
        {
            students.add(pouch.extractStudent());
        }


    public Student getStudent(int index)
    {
      Student student;
      student = students.get(index);
      students.remove(index);
      return student;
    }

}