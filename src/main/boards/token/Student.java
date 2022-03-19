package model.boards.token;

public class Student implements Token
{

    private Col color;

    public Student(Col StudentColor)
    {
      this.color = StudentColor;
    }


    public Col getColor()
    {
        return color;
    }
    /*public int getPosition()
    {
        //da capire che fa
    }*/
}
