package model.boards;
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

  public Island(int islandId)
  {
    this.islandId = islandId;
    this.currentStudents = new ArrayList<Student>();
    this.motherNature = false;
    this.Group = false;
    this.towerNumber = 0;
    this.ownership = null;
    this.teamInfluence = new int[3];
    this.noEntry = false;
  }

  public void calculateOwnership()
  {
    int max = 0;
    if(motherNature)
    {
      for(int i: teamInfluence)
      {
        if(this.teamInfluence[i] > max)
        {
          max = this.teamInfluence[i];
          switch(i)
          {
            case(0):
              ownership = ColTow.BLACK;
              break;
            case(1):
              ownership = ColTow.GREY;
              break;
            case(2):
              ownership = ColTow.WHITE;
              break;
          }
        }
      }
    }
  }

  public void updateTeamInfluence(Team[] team)
  {
    int numGREEN = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.GREEN).count();
    int numYELLOW = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
    int numRED = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.RED).count();
    int numPINK = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.PINK).count();
    int numBLUE = (int) currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count();
    int GREYinf = 0;
    int BLACKinf = 0;
    int WHITEinf = 0;
    for(Team t: team)
    {
      switch(t.getColor()) {
        case GREY:
          if (t.getControlledProfessors().contains(Col.GREEN))
            GREYinf += numGREEN;
          if (t.getControlledProfessors().contains(Col.YELLOW))
            GREYinf += numYELLOW;
          if (t.getControlledProfessors().contains(Col.RED))
            GREYinf += numRED;
          if (t.getControlledProfessors().contains(Col.PINK))
            GREYinf += numPINK;
          if (t.getControlledProfessors().contains(Col.BLUE))
            GREYinf += numBLUE;
          break;

        case BLACK:
          if (t.getControlledProfessors().contains(Col.GREEN))
            BLACKinf += numGREEN;
          if (t.getControlledProfessors().contains(Col.YELLOW))
            BLACKinf += numYELLOW;
          if (t.getControlledProfessors().contains(Col.RED))
            BLACKinf += numRED;
          if (t.getControlledProfessors().contains(Col.PINK))
            BLACKinf += numPINK;
          if (t.getControlledProfessors().contains(Col.BLUE))
            BLACKinf += numBLUE;
          break;

        case WHITE:
          if (t.getControlledProfessors().contains(Col.GREEN))
            WHITEinf += numGREEN;
          if (t.getControlledProfessors().contains(Col.YELLOW))
            WHITEinf += numYELLOW;
          if (t.getControlledProfessors().contains(Col.RED))
            WHITEinf += numRED;
          if (t.getControlledProfessors().contains(Col.PINK))
            WHITEinf += numPINK;
          if (t.getControlledProfessors().contains(Col.BLUE))
            WHITEinf += numBLUE;
          break;
      }
    }
    if(motherNature)
    {
      if(ownership == ColTow.WHITE)
      {
        WHITEinf++;
      }
      else if(ownership == ColTow.BLACK)
      {
        BLACKinf++;
      }
      else if(ownership == ColTow.GREY)
      {
        GREYinf++;
      }
    }
    teamInfluence[0] = BLACKinf;
    teamInfluence[1] = GREYinf;
    teamInfluence[2] = WHITEinf;

  }

  public void addStudent(Student s)
  {
    currentStudents.add(s);
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

  public boolean isGroup() {
    return Group;
  }

  public boolean isMotherNature() {
    return motherNature;
  }

  public boolean isNoEntry() {
    return noEntry;
  }

  public int getTowerNumber() {
    return towerNumber;
  }
}
