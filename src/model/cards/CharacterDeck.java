package model.cards;

public class CharacterDeck
{
  private int playerId;
  private CharacterCard deck;


  public CharacterDeck(CharacterCard deck, int playerId)
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
