package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;

public class Island
{
  private int islandId;
  private boolean motherNature;
  private Student[] currentStudents;
  private ColTow ownership;
  private int[] teamInfluence;
  private boolean noEntry;

  public Island(int islandId,boolean motherNature, Student[] currentStudents, ColTow ownership, int[] teamInfluence, boolean noEntry)
  {
    this.islandId=islandId;
    this.currentStudents=currentStudents;
    this.motherNature=motherNature;
    this.ownership=ownership;
    this.teamInfluence=teamInfluence;
    this.noEntry=noEntry;
  }

  public void calculateOwnership(){

  }
  public void updateTeamInfluence(){

  }

  public Student[] getCurrentStudents(){
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
}
