package model.boards.token;

import model.boards.Board;
public class Student {

    private Col color;

    //Creates a new student given the student color
    public Student(Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }
}
