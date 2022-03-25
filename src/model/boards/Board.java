package model.boards;

import model.boards.token.Student;

public interface Board
{
  public void placeToken(Student s, int pos);
}
