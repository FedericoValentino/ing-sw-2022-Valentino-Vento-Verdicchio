package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.boards.token.Token;

public class School implements Board
{
  private ColTow color;
  private Student[] entrance;
  private int[] colors;
  private boolean[] professorTable;
  private int towerCount;

    public School(ColTow color, Student[] entrance, int[] colors, boolean[] professorTable, int towerCount)
    {
        this.color=color;
        this.entrance=entrance;
        this.colors=colors;
        this.professorTable=professorTable;
        this.towerCount=towerCount;
    }
  /*
  public int gainCoin()
  {
  }
  public int checkRemainingTowers()
  {
  }
  public int checkProfessors()
  {
  }
   */
  public void placeToken(Token s, int pos)
  {

  }
  public void removeToken(Token s,int pos)
  {

  }

  public ColTow getColor()
  {
    return color;
  }
 /* public Student getEntrance(int pos)
  {
    try
    {
      for(int i=0;i<entrance.length;i++)
      {
        if(i==pos)
          return entrance[i];
      }
    }catch(NullPointerException e)
    {
      System.out.println("i can't give you correctly a specific position of student entrance, maybe because it's a null pointer");
    }
  }*/
  public int[] getColors()
  {
    return colors;
  }
  public boolean[] getProfessorTable()
  {
    return professorTable;
  }
  public int getTowerCount(){return towerCount;}

}
