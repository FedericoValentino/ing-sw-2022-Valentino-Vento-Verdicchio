package model;

import model.boards.token.Col;
import model.boards.token.ColTow;

public class Team
{
  private ColTow color;
  private Player[] players;
  private int controlledIslands;
  private Col[] controlledProfessors;


  public Team(){}

  public void addPlayer(Player p){
    //da implementare
  }

  public Team(ColTow color, Player[] players, int controlledIslands, Col[] controlledProfessors)
  {
    this.color=color;
    this.controlledIslands=controlledIslands;
    this.controlledProfessors=controlledProfessors;
    for(int i=0;i<players.length;i++)
    {
      this.players[i]=players[i];
    }

  }

  public ColTow getColor()
  {
    return color;
  }

  /*
  public Player getSingleplayers(int idPl)

  {
    try{
      for(int i=0;i<players.length;i++)
        if(players.playerId==idPl)
          return players[i];
    }
    catch (NullPointerException e)
    {
      System.out.println("Null pointer exceprtion  in getPlayer(int)");
    }
  }
 */


  public Player[] getPlayers() {
    return players;
  }

  public int getControlledIslands()
  {
    return controlledIslands;
  }
  public Col[] getControlledProfessors() {
    return controlledProfessors;
  }
}
