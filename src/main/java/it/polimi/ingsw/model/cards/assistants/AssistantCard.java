package it.polimi.ingsw.model.cards.assistants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A very simple class, it models an assistant card with its basic attributes
 */
public class AssistantCard
{
  private final int movement;
  private final int value;


    /** Class constructor: assigns permanently movement and turn values to the card
     * @param movement  the Mother Nature movement value of the Card; defined in the Assistant Deck constructor
     * @param value  the Turn value of the card; defined in the Assistant Deck constructor
     */
  public AssistantCard(@JsonProperty("movement") int movement,
                       @JsonProperty("value")int value)
  {
      this.movement = movement;
      this.value = value;
  }

  public int getMovement()
      {return movement;}

  public int getValue()
      { return value;}

}
