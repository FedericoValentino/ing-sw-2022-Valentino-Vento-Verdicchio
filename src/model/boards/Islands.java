package model.boards;

import model.boards.token.Token;

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

  public void placeToken(Token s, int pos) {
  }

  public void removeToken(Token s, int pos) {
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
