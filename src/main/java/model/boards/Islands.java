package model.boards;
import model.boards.token.Student;

public class Islands implements Board {
  private Island[] islands;
  private int totalGroups;

  public Islands(Island[] islands, int totalGroups) {
    for (int i = 0; i < islands.length; i++) {
      this.islands[i] = islands[i];
    }
    this.totalGroups = totalGroups;
  }

  public void idManagement() {

  }

  public void placeToken(Student s, int pos) {
  }

  public void removeToken(Student s, int pos) {
  }

  /*
  public int getTotalGroups()
  {

  }

  public int getIsland(int)
  {

  }
  */

}
