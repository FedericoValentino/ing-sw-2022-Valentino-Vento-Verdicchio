package model.boards;

import model.boards.token.Token;

public interface Board
{
  public void placeToken(Token s, int pos);
  public void removeToken(Token s,int pos);
}
