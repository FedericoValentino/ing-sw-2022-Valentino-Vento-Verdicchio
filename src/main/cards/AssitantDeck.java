package model.cards;
import model.cards.Card;

public class AssitantDeck
{
  private int playerId;
  private Card[] deck;

  public AssitantDeck(Card[] deck, int playerId)
  {
    this.deck=deck;
    this.playerId=playerId;
  }

  /*
  public boolean checkEmpty(){}
  public Card[] getDeck(int numCard);
 */
  public int getPlayerId(){return playerId;}
}
