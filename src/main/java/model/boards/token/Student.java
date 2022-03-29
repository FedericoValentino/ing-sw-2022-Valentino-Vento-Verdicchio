package model.boards.token;

import model.boards.Board;
public class Student {

    private Col color;

    public Student(Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }
}
