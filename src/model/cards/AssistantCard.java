package model.cards;

public class AssistantCard
{
  private final int movement;
  private final int value;


  public AssistantCard(int movement, int value)     //banale, crea carta con valori che verranno dati dal costruttore in AssistantDeck
  {
      this.movement = movement;
      this.value = value;
  }

  public int getMovement()
      {return movement;}

  public int getValue()
      { return value;}

}
