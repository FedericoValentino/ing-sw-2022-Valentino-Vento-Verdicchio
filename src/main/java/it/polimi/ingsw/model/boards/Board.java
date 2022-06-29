package it.polimi.ingsw.model.boards;


/**
 * The abstract class Board has the function to unify the boards into a logical ensemble: a board is what it is
 * because it has the possibility of placing a token on top of it
 */
public abstract class Board
{
  /**
   * This is a standard method for placing students onto Boards,
   * specialized in the various boards of the game
   */
  public void placeToken(){}
}
