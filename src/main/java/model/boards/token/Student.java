package model.boards.token;

import model.boards.Board;
public class Student {

    private Col color;
    private Board currentPosition;//da aggiungere anche un setter di curPos allora

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
            System.out.println("I'll return null, because is not initialized");
        return null;} //purtroppo
    }


}
