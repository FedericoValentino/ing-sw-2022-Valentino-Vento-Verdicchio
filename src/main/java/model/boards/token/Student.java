package model.boards.token;

import model.boards.Board;
public class Student {

    private Col color;


    /** Class constructor. Creates a new student given the student color
     * @param StudentColor  the color assigned to the new student
     */
    public Student(Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }
}
