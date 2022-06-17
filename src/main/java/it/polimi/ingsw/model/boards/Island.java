package it.polimi.ingsw.model.boards;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.ColTow;

import java.util.ArrayList;


public class Island
{
  private int islandId;
  private boolean motherNature;
  private boolean group;
  private ArrayList<Student> currentStudents;
  private ColTow ownership;
  private int towerNumber;
  private int[] teamInfluence;
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
    this.group = false;
    this.towerNumber = 0;
    this.ownership = null;
    this.teamInfluence = new int[3];
    this.noEntry = false;
  }

  /** Method calculateOwnership sets the island ownership to the team with the most influence on it */
  public void calculateOwnership()
  {
    int max = 0;
    int team = 0;
    if(motherNature)
    {
      for(int i = 0; i < 3; i++)
      {
        if(teamInfluence[i] > max)
        {
          max = teamInfluence[i];
          team = i;
        }
      }
      for(int i = 0; i < 3; i++)
      {
        if(i != team && max == teamInfluence[i])
        {
          return;
        }
      }
      ownership = ColTow.values()[team];
      if(towerNumber == 0)
      {
        towerNumber = 1;
      }
    }
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
  }

  /** Method addStudent adds a student to the island
   * @param s  the student that has to be placed on the island
   */
  public void addStudent(Student s)
  {
    currentStudents.add(s);
  }

  /** Method updateNoEntry sets on or off the access of the island to motherNature */
  public void updateNoEntry()
  {
    noEntry = !getNoEntry();
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
    motherNature = !motherNature;
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
    return group;
  }
  public int getTowerNumber() {return towerNumber;}
  public void setIslandId(int id){
    this.islandId=id;
  }
  public void setMotherNature(boolean motherNature){this.motherNature=motherNature;}
  public void setGroup(boolean group){this.group =group;}
  public void setTowerNumber(int towerNumber) {this.towerNumber=towerNumber;}
}
