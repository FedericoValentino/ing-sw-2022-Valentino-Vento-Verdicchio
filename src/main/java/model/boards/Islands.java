package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;

import java.util.ArrayList;

public class Islands extends Board {
  private ArrayList<Island> islands=new ArrayList<>();
  private int totalGroups;


  /*
   * Class Constructor, creates 12 Island and sets the total group to 12;
   */

  public Islands()
  {
    for(int i = 0; i < 12; i++)
    {
      islands.add(new Island(i));
    }
    totalGroups = 12;
  }

  /*
   * Method idManagement handles the unification of islands and updates the totalGroup parameter to match with the ArrayList.size();
   */
  public void idManagement()
  {
    Island currentIsland;
    Island nextIsland;
    Island previousIsland;
    for(int i = 0; i < islands.size(); i++)
    {
      currentIsland = islands.get(i);
      if(i == 0)
      {
        nextIsland = islands.get(i+1);
        previousIsland = islands.get(islands.size()-1);
      }
      else if(i == islands.size()-1)
      {
        nextIsland = islands.get(0);
        previousIsland = islands.get(i-1);
      }
      else
      {
        nextIsland = islands.get(i+1);
        previousIsland = islands.get(i-1);
      }
      if(currentIsland.getOwnership() == nextIsland.getOwnership() && currentIsland.getOwnership() == previousIsland.getOwnership()) //Unione tripla
      {
        unifyIslands(currentIsland, nextIsland);
        unifyIslands(currentIsland, previousIsland);
        islands.set(i, currentIsland);
        islands.remove(nextIsland);
        islands.remove(previousIsland);
      }
      else if(currentIsland.getGroup())  //unione doppia(attuabile solo se currentIsland è di già un gruppo di isole
      {
        if(currentIsland.getOwnership() == nextIsland.getOwnership())
        {
          unifyIslands(currentIsland, nextIsland);
          islands.set(i, currentIsland);
          islands.remove(nextIsland);
        }
        else if(currentIsland.getOwnership() == previousIsland.getOwnership())
        {
          unifyIslands(currentIsland, previousIsland);
          islands.set(i, currentIsland);
          islands.remove(previousIsland);
        }
      }
    }
    resetId();
  }

  /*
   * Method unifyIslands handles the unification of 2 islands
   */
  private void unifyIslands(Island currentIsland, Island nextIsland)
  {
    if(nextIsland.getMotherNature())
    {
      currentIsland.motherNature = true;
    }
    currentIsland.currentStudents.addAll(nextIsland.currentStudents);
    currentIsland.towerNumber += 1;
    currentIsland.teamInfluence[0] += nextIsland.teamInfluence[0];
    currentIsland.teamInfluence[1] += nextIsland.teamInfluence[1];
    currentIsland.teamInfluence[2] += nextIsland.teamInfluence[2];
    currentIsland.Group = true;

  }


  private void resetId()
  {
    for(int i = 0; i < islands.size(); i++)
    {
      Island I = islands.get(i);
      I.islandId = i;
      islands.set(i, I);
    }
    totalGroups = islands.size();
  }

  /*
   * Method placeToken places a token on an island tile
   *
   * @param student token
   * @param position of the island tile
   */

  public void placeToken(Student s, int pos) {
    Island I = islands.get(pos);
    I.addStudent(s);
    islands.set(pos, I);
  }

  /*
   * Metod getMaxCol returns the color of the team with the most built towers
   *
   * @return the current winning team
   */

  public ColTow getMaxCol()
  {
    int[] Towers = new int[3];
    int max = 0;
    int Winner = 0;
    for(Island I: islands)
    {
      if(I.getOwnership() == ColTow.BLACK)
      {
        Towers[ColTow.BLACK.ordinal()] += I.getTowerNumber();
      }
      if(I.getOwnership() == ColTow.GREY)
      {
        Towers[ColTow.GREY.ordinal()] += I.getTowerNumber();
      }
      if(I.getOwnership() == ColTow.WHITE)
      {
        Towers[ColTow.WHITE.ordinal()] += I.getTowerNumber();
      }
    }
    for(int i = 0; i < 2; i++)
    {
      if(Towers[i] > max)
      {
        max = Towers[i];
        Winner = i;
      }
    }
    return ColTow.values()[Winner];
  }

  public ArrayList<Island> getIslands()
  {
    return islands;
  }
  public int getTotalGroups()
  {
    return totalGroups;
  }
}
