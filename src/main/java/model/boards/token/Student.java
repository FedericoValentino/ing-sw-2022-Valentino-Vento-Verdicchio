package model.boards.token;

import model.boards.Board;
public class Student {

    private Col color;
    private Board currentPosition;

    public Student(Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }

    public Board getCurrentPosition()
    {
        try
        {
            return currentPosition;
        }catch (NullPointerException e)
        {
            System.out.println("Torno nullo perché var null");
        return null;} //purtroppo
    }


}
