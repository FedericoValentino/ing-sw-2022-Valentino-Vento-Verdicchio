package it.polimi.ingsw.model.boards;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.Col;

import java.util.ArrayList;

public class School extends Board
{
  private final ColTow color;
  private ArrayList<Student> entrance;
  private int[] diningRoom;
  private int[] roomCheckpoints;
  private boolean[] professorTable;
  private int towerCount;
  private CurrentGameState game;

  /** Class constructor. Creates a school receiving as inputs the game-mode dependent attributes, such as TeamColor
     and number of Towers. The diningRoom doesn't operate with students, but it is instead an array of int;
     each index corresponds to the ordinal of the enumeration used to detail students' colors. So it can be manipulated
     through the color of the selected student. The roomCheckpoints array is handled in a similar way, and is used to
     determine if a player should receive a coin upon placement of a student in the dining room.
     The professorTable is handled as an array of booleans, which tells us which professor is being controlled by the
     player owning the school: the professor's color ordinal value allow us to comfortably handle this structure
     without using professors as objects. The entrance is a normal arraylist containing actual student objects
     * @param color  the color of the player's team
     * @param towerCount  the number of towers assigned to the player, depending on the number of players present
     */
    public School(ColTow color, int towerCount, CurrentGameState game)
    {
        this.game = game;
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

    /** Places a student in the entrance
     * @param student  the student to place into the entrance
     * */
  public void placeToken(Student student)
  {
      entrance.add(student);
  }

  public void placeToken(ArrayList<Student> students)
  {
      entrance.addAll(students);
  }

    /** Removes the selected student from the entrance and returns it
     * @param index  the position of the desired student in the School Entrance
     * @return the selected student
     */
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

    /** Increments the value of the dining room at the index corresponding to the student's color ordinal value
     * @param color  the color of the student that has to be placed into the dining room
     */
  public void placeInDiningRoom(Col color)
  {
     diningRoom[color.ordinal()]++;
  }

    /** Takes the right value of the updateCheckpoint structure in order to "point" towards the next checkpoint
     * @param position  the position of the previous checkpoint
     */
  public void updateCheckpoint(int position)
  {
    roomCheckpoints[position] += 3;
  }

    /** Increments or decrements the number of towers in the school based on the input received
     * @param tower  the number of towers to add to the school tower count
     */
  public void updateTowerCount(int tower)
  {
    towerCount += tower;
  }

    /** Sets the value of the professorTable at the right index to True or False
     * @param i  index of the Professor Table corresponding to the correct color of the professor to gain or to lose
     * @param t  tells us whether to gain the professor or to lose him
     */
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
