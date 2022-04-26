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

    /*
    Creates a school receiving as inputs the game-mode dependent
    attributes, such as TeamColor and number of Towers.
    The diningRoom doesn't operate with students, but it is instead an array
    of int; each index corresponds to the ordinal of the enumeration used to detail
    students' colors. So it can be manipulated through the color of the selected student.
    The roomCheckpoints array is handled in a similar way, and is used to determine if a
    player should receive a coin upon placement of a student in the dining room.
    The professorTable is handled as an array of booleans, which tells us which professor is
    being controlled by the player owning the school: the professor's color ordinal value
    allow us to comfortably handle this structure without using professors as objects.
    The entrance is a normal arraylist containing actual student objects
    */
    public School(ColTow color, int towerCount)
    {
        this.color = color;
        this.towerCount = towerCount;
        this.diningRoom = new int[5];
        this.roomCheckpoints = new int[5];

        /* Each checkpoint has a distance of two tiles from the next one. The roomCheckpoints structure
        memorises the actual distance to the next checkpoint for each row  */
        for(int i=0;i<5;i++)
        {
          this.roomCheckpoints[i]=2;
          this.diningRoom[i]=0;
        }
        this.professorTable = new boolean[5];
        this.entrance = new ArrayList<>();
    }

  //Places a student in the entrance
  public void placeToken(Student student)
  {
      entrance.add(student);
  }

 //Removes the selected student from the entrance and returns it
  public Student extractStudent(int index)
  {
    try
    {
      Student student = entrance.get(index);
      entrance.remove(index);
      return student;
    }
    catch(IndexOutOfBoundsException e)
    {
      //System.out.println("SCHOOL : Index out of bound in EXTRACT STUDENT");
      return null;
    }
  }

  //Increments the value of the dining room at the index corresponding to the student's color ordinal value
  public void placeInDiningRoom(Col color)
  {
     diningRoom[color.ordinal()]++;
  }

  //Takes the right value of the updateCheckpoint structure to "point" to the next checkpoint
  public void updateCheckpoint(int position)
  {
    roomCheckpoints[position] += 3;
  }

  //Increments or decrements the number of towers in the school based on the input received
  public void updateTowerCount(int tower)
  {
    towerCount += tower;
  }

  //Sets the value of the professorTable at the right index to True or False
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
