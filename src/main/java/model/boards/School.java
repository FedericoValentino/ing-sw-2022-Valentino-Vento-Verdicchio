package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.boards.token.Col;

import java.util.ArrayList;

public class School extends Board
{
  private ColTow color;
  private ArrayList<Student> entrance;
  private int[] DiningRoom;
  private boolean[] professorTable;
  private int towerCount;

    public School(ColTow color, int towerCount)                     //Pouch viene dato in ingresso (dal controller suppongo) per estrarre tramite apposita
    {                                                               //funzione gli studenti da metetre nell'entrance. Il colore della torre è dato per assegnare
        this.color = color;                                         //la scuola a uno specifico team e il count delle torri va specificato in ingresso perchè cambiano
        this.towerCount = towerCount;                               //da partite a 2/4 - 3 giocatori. Il resto è standard, array della dining room vuoto, professori a false
        this.DiningRoom = new int[5];
        this.professorTable = new boolean[5];
        this.entrance = new ArrayList<Student>();
    }


  public void placeToken(Student student)             //Riceve un oggetto studente e una posizione specifica della entrance in cui metterlo;
  {                                                                 //visto che usiamo arraylists per tutto non so wuanto serva dare la posizione.... può
      entrance.add(student);                              //dare problemi e basterebbe fare una add. Comunque con l'attuale implementazione
  }                                                                 //di placeToken va così


  public ColTow getColor()
  {
    return color;
  }

  public Student extractStudent(int index)                          //Estrae un singolo studente dalla entrance e lo ritorna per la funzione che lo ha chiamato
  {
    try
    {
      Student student = entrance.get(index);
      entrance.remove(index);
      return student;
    }
    catch(NullPointerException e)
    {
      System.out.println("i can't give you correctly a specific position of student entrance, maybe because it's a null pointer");
      return null;
    }
  }

  public void placeInDiningRoom(int index)
  {
     Student student = extractStudent(index);
     Col color = student.getColor();
     diningRoom[color.ordinal()]++;
  }

  public void updateCheckpoint(int position)
  {
    roomCheckpoints[position] += 3;
  }


  public void updateTowerCount(int tower)
  {towerCount += tower;}

  public ArrayList<Student> getEntrance()
    {return entrance;}

  public int[] getDiningRoom()
    {return diningRoom;}

  public int[] getRoomCheckpoints()
    {return roomCheckpoints;}

  public boolean[] getProfessorTable()
    {return professorTable;}

  public int getTowerCount()
    {return towerCount;}

}
