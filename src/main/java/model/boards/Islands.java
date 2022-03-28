package model.boards;
import model.boards.token.ColTow;
import model.boards.token.Student;

import java.util.ArrayList;

public class Islands extends Board {
  private ArrayList<Island> islands;
  private int totalGroups;

  public Islands()
  {
    for(int i = 0; i < 12; i++)
    {
      islands.add(new Island(i));
    }
    totalGroups = 12;
  }

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
        if(nextIsland.getMotherNature() || previousIsland.getMotherNature())
        {
          currentIsland.motherNature = true;
        }
        currentIsland.currentStudents.addAll(nextIsland.currentStudents);
        currentIsland.currentStudents.addAll(previousIsland.currentStudents);
        currentIsland.towerNumber += 2;
        currentIsland.teamInfluence[0] += nextIsland.teamInfluence[0] + previousIsland.teamInfluence[0];
        currentIsland.teamInfluence[1] += nextIsland.teamInfluence[1] + previousIsland.teamInfluence[1];
        currentIsland.teamInfluence[2] += nextIsland.teamInfluence[2] + previousIsland.teamInfluence[2];
        currentIsland.Group = true;
        islands.set(i, currentIsland);
        islands.remove(nextIsland);
        islands.remove(previousIsland);
      }
      else if(currentIsland.isGroup())  //unione doppia(attuabile solo se currentIsland è di già un gruppo di isole
      {
        if(currentIsland.getOwnership() == nextIsland.getOwnership())
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
          islands.set(i, currentIsland);
          islands.remove(nextIsland);
        }
        else if(currentIsland.getOwnership() == previousIsland.getOwnership())
        {
          if(previousIsland.getMotherNature())
          {
            currentIsland.motherNature = true;
          }
          currentIsland.currentStudents.addAll(previousIsland.currentStudents);
          currentIsland.towerNumber += 1;
          currentIsland.teamInfluence[0] += previousIsland.teamInfluence[0];
          currentIsland.teamInfluence[1] += previousIsland.teamInfluence[1];
          currentIsland.teamInfluence[2] += previousIsland.teamInfluence[2];
          islands.set(i, currentIsland);
          islands.remove(previousIsland);
        }
      }
    }
    for(int i = 0; i < islands.size(); i++)
    {
      Island I = islands.get(i);
      I.islandId = i;
      islands.set(i, I);
    }

  }

  public void placeToken(Student s, int pos) {
    Island I = islands.get(pos);
    I.addStudent(s);
    islands.set(pos, I);
  }

  public ColTow getMaxCol()
  {
    int[] Towers = new int[3];
    int max = 0;
    int Winner = 0;
    for(Island I: islands)
    {
      if(I.getOwnership() == ColTow.BLACK)
      {
        Towers[0] += I.getTowerNumber();
      }
      if(I.getOwnership() == ColTow.GREY)
      {
        Towers[1] += I.getTowerNumber();
      }
      if(I.getOwnership() == ColTow.WHITE)
      {
        Towers[2] += I.getTowerNumber();
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
    switch(Winner)
    {
      case(0):
        return ColTow.BLACK;
      case(1):
        return ColTow.GREY;
      case(2):
        return ColTow.WHITE;
    }
    return null;
  }

  public ArrayList<Island> getIslands() {
    return islands;
  }
  public int getTotalGroups() {
    return totalGroups;
  }
}
