package model.boards;
import model.CurrentGameState;
import model.Team;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;

import java.util.ArrayList;


public class Island
{
  public int islandId;
  public boolean motherNature;
  public boolean Group;
  public ArrayList<Student> currentStudents;
  private ColTow ownership;
  public int towerNumber;
  public int[] teamInfluence;
  private boolean noEntry;
  private CurrentGameState game;


  /** Class Constructor
   * @param islandId  the ID to assign to the new island
   */
  public Island(int islandId, CurrentGameState game)
  {
    this.game = game;
    this.islandId = islandId;
    this.currentStudents = new ArrayList<>();
    this.motherNature = false;
    this.Group = false;
    this.towerNumber = 0;
    this.ownership = null;
    this.teamInfluence = new int[3];
    this.noEntry = false;
  }

  /** Method calculateOwnership sets the island ownership to the team with the most influence on it */
  public void calculateOwnership()
  {
    int max = 0;
    if(motherNature)
    {
      for(int i = 0; i < 3; i++)
      {
        if(this.teamInfluence[i] > max)
        {
          if(towerNumber == 0)
          {
            towerNumber = 1;
          }
          max = this.teamInfluence[i];
          this.ownership = ColTow.values()[i];
        }
      }
    }
    game.notify(game.modelToJson());
  }

  /** Method updateTeamInfluence updates the teamInfluence array with each team influence on the island
   * @param team  the arraylist teams currently competing
   */
  public void updateTeamInfluence(ArrayList<Team> team)
  {
    if(!noEntry)
    {
      int numGREEN = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.GREEN).count();
      int numYELLOW = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
      int numRED = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.RED).count();
      int numPINK = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.PINK).count();
      int numBLUE = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count();
      this.teamInfluence[0] = 0;
      this.teamInfluence[1] = 0;
      this.teamInfluence[2] = 0;
      for(Team t: team)
      {
        if (t.getControlledProfessors().contains(Col.GREEN))
          this.teamInfluence[t.getColor().ordinal()] += numGREEN;
        if (t.getControlledProfessors().contains(Col.YELLOW))
          this.teamInfluence[t.getColor().ordinal()]  += numYELLOW;
        if (t.getControlledProfessors().contains(Col.RED))
          this.teamInfluence[t.getColor().ordinal()] += numRED;
        if (t.getControlledProfessors().contains(Col.PINK))
          this.teamInfluence[t.getColor().ordinal()] += numPINK;
        if (t.getControlledProfessors().contains(Col.BLUE))
          this.teamInfluence[t.getColor().ordinal()] += numBLUE;
      }
      if(motherNature)
      {
        if(ownership != null)
          this.teamInfluence[ownership.ordinal()] += towerNumber;
      }
    }
    game.notify(game.modelToJson());
  }

  /** Method addStudent adds a student to the island
   * @param s  the student that has to be placed on the island
   */
  public void addStudent(Student s)
  {
    currentStudents.add(s);
    game.notify(game.modelToJson());
  }

  /** Method updateNoEntry sets on or off the access of the island to motherNature */
  public void updateNoEntry()
  {
    if(getNoEntry())
      noEntry = false;
    else
      noEntry = true;
    game.notify(game.modelToJson());
  }

  /** Overload of updateTeamInfluence, only used by CharacterCards which actively modify the influence on an island
   * @param value  value to sum to the current Team Influence on the island
   * @param team  the index identifying the correct influence value in the Team Influence array
   */
  public void updateTeamInfluence(int value, int team)
  {
    teamInfluence[team] += value;
  }

  /** Method updateMotherNature sets on or off the presence of mother nature on the island */
  public void updateMotherNature()
  {
    if(motherNature)
    {
      motherNature = false;
    }
    else
    {
      motherNature = true;
    }
    game.notify(game.modelToJson());
  }

  public ArrayList<Student> getCurrentStudents(){
    return currentStudents;
  }
  public int getIslandId(){
    return islandId;
  }
  public boolean getMotherNature(){
    return motherNature;
  }
  public ColTow getOwnership(){return ownership;  }
  public int[] getTeamInfluence() {return teamInfluence;  }
  public boolean getNoEntry(){return noEntry;  }
  public boolean getGroup() {
    return Group;
  }
  public int getTowerNumber() {return towerNumber;}
}
