package model.boards.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.Board;

import java.io.Serializable;

public class Student implements Serializable {

    private Col color;


    /** Class constructor. Creates a new student given the student color
     * @param StudentColor  the color assigned to the new student
     */
    public Student(@JsonProperty("color") Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }
}
