package model.cards;

public class AssistantCard
{
  private final int movement;
  private final int value;

  public AssistantCard(int movement, int value)
  {
      this.movement = movement;
      this.value = value;
  }

  public int getMovement()
      {return movement;}

  public int getValue()
      { return value;}

}
