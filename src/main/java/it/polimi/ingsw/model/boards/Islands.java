package it.polimi.ingsw.model.boards;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.ColTow;

import java.util.ArrayList;

public class Islands extends Board {
  private ArrayList<Island> islands=new ArrayList<>();
  private int totalGroups;
  private CurrentGameState game;


  /** Class Constructor, creates 12 Island and sets the total group to 12 */
  public Islands(CurrentGameState game)
  {
    for(int i = 0; i < 12; i++)
    {
      islands.add(new Island(i, game));
    }
    totalGroups = 12;
    this.game = game;
  }

  /** Method idManagement handles the unification of islands and updates the totalGroup parameter to match
   with the ArrayList.size()
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
      if(currentIsland.getOwnership() == nextIsland.getOwnership() && (currentIsland.motherNature || nextIsland.motherNature)
              && currentIsland.getOwnership() != null)
      {
        unifyIslands(currentIsland, nextIsland);
        islands.set(i, currentIsland);
        islands.remove(nextIsland);
        i = 0;
      }
      else if(currentIsland.getOwnership() == previousIsland.getOwnership() && (currentIsland.motherNature || previousIsland.motherNature)
              && currentIsland.getOwnership() != null)
      {
        unifyIslands(currentIsland, previousIsland);
        islands.set(i, currentIsland);
        islands.remove(previousIsland);
        i = 0;
      }
    }
    resetId();
  }

  /** Method unifyIslands handles the unification of 2 islands
   * @param currentIsland  one of the islands object of the unification process
   * @param nextIsland  the second island object of the unification process
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

  /** Reorders the IDs of the islands after the eventual unifications */
  private void resetId()
  {
    for(int i = 0; i < islands.size(); i++)
    {
      Island I = islands.get(i);
      I.islandId = i;
      islands.set(i, I);
      if(islands.get(i).getMotherNature())
      {
        game.getCurrentMotherNature().updatePositionAfterMerge(i);
      }
    }
    totalGroups = islands.size();
  }


  /** Method placeToken places a token on an island tile
   * @param s  student to place onto the island
   * @param pos  index identifying the position of the island into the Islands structure
   */
  public void placeToken(Student s, int pos) {
    Island I = islands.get(pos);
    I.addStudent(s);
    islands.set(pos, I);
  }

  /** Method getMaxCol determines the color of the team with the highest number of towers built
   * @param Teams  the arraylist of the teams present in the game
   * @return the color of the team with the highest number of towers built
   */
  public ColTow getMaxCol(ArrayList<Team> Teams)
  {
    int[] ControlledProfessors = new int[3];
    ControlledProfessors[0] = 0;
    ControlledProfessors[1] = 0;
    ControlledProfessors[2] = 0;
    int[] Towers = new int[3];
    Towers [0]=0;
    Towers [1]=0;
    Towers [2]=0;
    int max = 0;
    int Winner = 0;
    for(Island I: islands)
    {
      if(I.getOwnership() == ColTow.BLACK)
      {
        Towers[ColTow.BLACK.ordinal()] += I.getTowerNumber();
        ControlledProfessors[ColTow.BLACK.ordinal()] += getTeam(Teams, ColTow.BLACK).getControlledProfessors().size();
      }
      else if(I.getOwnership() == ColTow.GREY)
      {
        Towers[ColTow.GREY.ordinal()] += I.getTowerNumber();
        ControlledProfessors[ColTow.GREY.ordinal()] += getTeam(Teams, ColTow.GREY).getControlledProfessors().size();
      }
      else if(I.getOwnership() == ColTow.WHITE)
      {
        Towers[ColTow.WHITE.ordinal()] += I.getTowerNumber();
        ControlledProfessors[ColTow.WHITE.ordinal()] += getTeam(Teams, ColTow.WHITE).getControlledProfessors().size();
      }
    }
    for(int i = 0; i < 3; i++)
    {
      if(Towers[i] == max)
      {
        if(Towers[i] + ControlledProfessors[i] > Towers[Winner] + ControlledProfessors[Winner])
        {
          max = Towers[i];
          Winner = i;
        }
      }
      if(Towers[i] > max)
      {
        max = Towers[i];
        Winner = i;
      }
    }

    return ColTow.values()[Winner];
  }

  private Team getTeam(ArrayList<Team> Teams, ColTow color)
  {
    for(Team t: Teams)
    {
      if(t.getColor() == color)
      {
        return t;
      }
    }
    return null;
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
