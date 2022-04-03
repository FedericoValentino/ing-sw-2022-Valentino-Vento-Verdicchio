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

  public Team(ColTow color)
  {
    this.color = color;
    this.players = new ArrayList<>();
    this.controlledIslands = 0;
    this.controlledProfessors = new ArrayList<>();
  }

  public void addPlayer(Player p)
  {
    this.players.add(p);
  }

  public void updateProfessors()
  {
    for(Player p : players)
    {
      for(int i = 0; i < 5; i ++)
      {
        if(p.getPlayerSchool().getProfessorTable()[i])
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
