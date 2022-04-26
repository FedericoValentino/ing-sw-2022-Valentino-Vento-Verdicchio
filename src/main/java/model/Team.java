package model;

import model.boards.token.Col;
import model.boards.token.ColTow;

import java.util.ArrayList;

public class Team
{
  private final ColTow color;
  private ArrayList<Player> players;
  private int controlledIslands;
  private ArrayList<Col> controlledProfessors;

  /** Class Constructor */
  public Team(ColTow color)
  {
    this.color = color;
    this.players = new ArrayList<>();
    this.controlledIslands = 0;
    this.controlledProfessors = new ArrayList<>();
  }


  /** Method addPlayer adds a player to the team
   * @param p  reference to the player that has to be added to the team
   */
  public void addPlayer(Player p)
  {
    this.players.add(p);
  }


  /** Method updateProfessors checks whether a player in the team has a professor or not.
      If it does then it adds it to its collection of held professors
   */
  public void updateProfessors()
  {
    controlledProfessors = new ArrayList<>();
    for(Player p : players)
    {
      for(int i = 0; i < 5; i ++)
      {
        if(p.getSchool().getProfessorTable()[i])
        {
          switch(i)
          {
            case(0):
              if(!controlledProfessors.contains(Col.GREEN))
                controlledProfessors.add(Col.GREEN);
              break;
            case(1):
              if(!controlledProfessors.contains(Col.RED))
                controlledProfessors.add(Col.RED);
              break;
            case(2):
              if(!controlledProfessors.contains(Col.YELLOW))
                controlledProfessors.add(Col.YELLOW);
              break;
            case(3):
              if(!controlledProfessors.contains(Col.PINK))
                controlledProfessors.add(Col.PINK);
              break;
            case(4):
              if(!controlledProfessors.contains(Col.BLUE))
                controlledProfessors.add(Col.BLUE);
              break;
          }
        }
      }
    }

  }


  /** Updates the team's controlled islands by adding the specified value to the ControlledIslands field
   * @param value  the value to add to the ControlledIslands field
   */
  public void updateControlledIslands(int value)
  {
    if(controlledIslands == 0 && value<0){}
    else
      controlledIslands += value;
  }

  public ColTow getColor() {
    return color;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public int getControlledIslands() {
    return controlledIslands;
  }

  public ArrayList<Col> getControlledProfessors() {
    return controlledProfessors;
  }
}
