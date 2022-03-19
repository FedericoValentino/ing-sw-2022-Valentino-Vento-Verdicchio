package model.cards;
import model.cards.Card;

public class CharacterDeck
{
  private int playerId;
  private Card[] deck;


  public CharacterDeck(Card[] deck, int playerId)
  {
    this.deck=deck;
    this.playerId=playerId;
  }
  /*
  public Card drawCard(){return }
  public boolean checkEmpty(){}
  public Card getDeck(int numCard){}
 */
  public int getPlayerId(){return playerId;}

}
