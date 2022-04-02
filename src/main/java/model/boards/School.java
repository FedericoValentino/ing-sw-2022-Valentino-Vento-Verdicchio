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

    public School(ColTow color, int towerCount)                     //Pouch viene dato in ingresso (dal controller suppongo) per estrarre tramite apposita
    {                                                               //funzione gli studenti da metetre nell'entrance. Il colore della torre è dato per assegnare
        this.color = color;                                         //la scuola a uno specifico team e il count delle torri va specificato in ingresso perchè cambiano
        this.towerCount = towerCount;                               //da partite a 2/4 - 3 giocatori. Il resto è standard, array della dining room vuoto, professori a false
        this.diningRoom = new int[5];
        this.roomCheckpoints = new int[5];
        for(int i=0;i<5;i++)
        {
          this.diningRoom[i]=0;
          this.roomCheckpoints[i]=2;// because we start counting from 0
        }
        this.professorTable = new boolean[5];
        this.entrance = new ArrayList<Student>();
    }


  public void placeToken(Student student)             //Riceve un oggetto studente e una posizione specifica della entrance in cui metterlo;
  {                                                                 //visto che usiamo arraylists per tutto non so wuanto serva dare la posizione.... può
      entrance.add(student);                              //dare problemi e basterebbe fare una add. Comunque con l'attuale implementazione
  }                                                                 //di placeToken va così


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

  public void placeInDiningRoom(int index)
  {
    try {
      Student student = extractStudent(index);
      Col color = student.getColor();
      diningRoom[color.ordinal()]++;
    }catch(NullPointerException e)
    {
      System.out.println("Vedi che non stai prendendo nulla bro");
    }
  }

  public void updateCheckpoint(int position)
  {
    roomCheckpoints[position] += 3;
  }


  public void updateTowerCount(int tower)
  {towerCount += tower;}

  public ArrayList<Student> getEntrance()
    {return entrance;}

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
