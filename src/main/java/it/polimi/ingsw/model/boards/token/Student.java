package it.polimi.ingsw.model.boards.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.io.Serializable;

/**
 * Student class models a student, identified by its color
 */
public class Student implements Serializable {

    private Col color;


    /**
     * Class constructor. Creates a new student given the student color. Being student a bean, we can directly pass it to the view;
     * that's the motivation of the json constructor
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
