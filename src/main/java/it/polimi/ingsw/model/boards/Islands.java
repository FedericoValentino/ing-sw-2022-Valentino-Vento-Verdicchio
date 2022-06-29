package it.polimi.ingsw.model.boards;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;

import java.util.ArrayList;

/**
 * Islands is generally used as a collection of island objects. It contains methods that need to access more islands at once,
 * for example the methods that rule the merging of island objects after the influence calculation
 */
public class Islands extends Board {
  private ArrayList<Island> islands=new ArrayList<>();
  private int totalGroups;
  CurrentGameState game;


  /**
   * Class Constructor, creates 12 Island and sets the total group to 12
   * @param game an instance of the game
   */
  public Islands(CurrentGameState game)
  {
    this.game = game;
    for(int i = 0; i < 12; i++)
    {
      islands.add(new Island(i));
    }
    totalGroups = 12;
  }

  /**
   * Method idManagement handles the unification of the islands, and updates the totalGroup parameter to match
   * with the ArrayList.size()
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


      if(currentIsland.getOwnership() == nextIsland.getOwnership() && (currentIsland.getMotherNature() || nextIsland.getMotherNature())
              && currentIsland.getOwnership() != null)
      {
        unifyIslands(currentIsland, nextIsland);
        i = 0;
      }
      else if(currentIsland.getOwnership() == previousIsland.getOwnership() && (currentIsland.getMotherNature() || previousIsland.getMotherNature())
              && currentIsland.getOwnership() != null)
      {
        unifyIslands(previousIsland, currentIsland);
        i = 0;
      }
      resetId();
    }
  }

  /**
   * Method unifyIslands handles the unification of 2 islands
   * @param currentIsland  one of the islands object of the unification process
   * @param nextIsland  the second island object of the unification process
   */
  private void unifyIslands(Island currentIsland, Island nextIsland)
  {
    if(nextIsland.getMotherNature())
    {
      currentIsland.setMotherNature(true);
    }
    currentIsland.getCurrentStudents().addAll(nextIsland.getCurrentStudents());
    currentIsland.setTowerNumber(currentIsland.getTowerNumber() + nextIsland.getTowerNumber());
    currentIsland.getTeamInfluence()[0] += nextIsland.getTeamInfluence()[0];
    currentIsland.getTeamInfluence()[1] += nextIsland.getTeamInfluence()[1];
    currentIsland.getTeamInfluence()[2] += nextIsland.getTeamInfluence()[2];
    currentIsland.setGroup(true);
    islands.remove(nextIsland);
  }

  /**
   * Reorders the IDs of the islands after the eventual unification
   */
  private void resetId()
  {
    for(int i = 0; i < islands.size(); i++)
    {
      islands.get(i).setIslandId(i);
      if(islands.get(i).getMotherNature())
      {
        game.getCurrentMotherNature().updatePositionAfterMerge(i);
      }
    }
    totalGroups = islands.size();
  }


  /**
   * Method placeToken places a token on an island tile
   * @param student  student to place onto the island
   * @param position  index identifying the position of the island into the Islands structure
   */
  public void placeToken(Student student, int position) {
    Island I = islands.get(position);
    I.addStudent(student);
    islands.set(position, I);
  }

  /**
   * Method getMaxCol determines the color of the team with the highest number of towers built
   * @param Teams  the arraylist of the teams present in the game
   * @return the color of the team with the highest number of towers built
   */
  public ColTow getMaxCol(ArrayList<Team> Teams)
  {
    int[] ControlledProfessors = new int[3];
    int[] Towers = new int[3];
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

  /**
   * Given a color, finds the team associated to said color
   * @param Teams a list of the competing teams
   * @param color the color of the desired team
   * @return the team corresponding to said color
   */
  private Team getTeam(ArrayList<Team> Teams, ColTow color)
  {
    for(Team team: Teams)
    {
      if(team.getColor() == color)
      {
        return team;
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
