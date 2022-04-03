package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.boards.token.Col;

import java.util.ArrayList;

public class School extends Board
{
  private final ColTow color;
  private ArrayList<Student> entrance;
  private int[] diningRoom;
  private int[] roomCheckpoints;
  private boolean[] professorTable;
  private int towerCount;

    public School(ColTow color, int towerCount)
    {
        this.color = color;
        this.towerCount = towerCount;
        this.diningRoom = new int[5];
        this.roomCheckpoints = new int[5];
        this.professorTable = new boolean[5];
        this.entrance = new ArrayList<>();
    }


  public void placeToken(Student student)
  {
      entrance.add(student);
  }


  public Student extractStudent(int index)                          //Estrae un singolo studente dalla entrance e lo ritorna per la funzione che lo ha chiamato
  {
    try
    {
      Student student = entrance.get(index);
      entrance.remove(index);
      return student;
    }
    catch(IndexOutOfBoundsException e)
    {
      System.out.println("Index out of bound in EXTRACT STUDENT");
      return null;
    }
  }

  public void placeInDiningRoom(Col color)
  {
     diningRoom[color.ordinal()]++;
  }

  public void updateCheckpoint(int position)
  {
    roomCheckpoints[position] += 3;
  }


  public void updateTowerCount(int tower)
  {
    towerCount += tower;
  }

  public void updateProfessorsTable(int i, boolean t)
  {
    this.professorTable[i] = t;
  }

  public ArrayList<Student> getEntrance()
  {
    return entrance;
  }

  public ColTow getColor()
  {
    return color;
  }

  public int[] getDiningRoom()
    {
      return diningRoom;
    }

  public int[] getRoomCheckpoints()
    {
      return roomCheckpoints;
    }

  public boolean[] getProfessorTable()
    {
      return professorTable;
    }

  public int getTowerCount()
    {
      return towerCount;
    }

}
