package model.cards;

import model.CurrentGameState;
import model.boards.Pouch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CharacterDeck implements Serializable
{
  private ArrayList<CharacterCard> deck;
  private CurrentGameState game;

  /** Class constructor. Creates every character Object, shuffles the collection, and eliminates 5 of them.
   This ensures a random Character Deck composition for each game instance
   */
  public CharacterDeck(CurrentGameState game)
  {
    this.game = game;
    this.deck = new ArrayList<>();
    this.deck.add(new Centaur());
    this.deck.add(new GrandmaHerbs());
    this.deck.add(new Knight());
    this.deck.add(new Herald());
    this.deck.add(new Postman());
    this.deck.add(new Priest());
    this.deck.add(new Princess());
    this.deck.add(new TruffleHunter());
    Collections.shuffle(this.deck);
    this.deck.subList(0, 5).clear();
  }

  /** Checks if the deck is empty
   * @return whether the deck is empty
   */
  public boolean checkEmpty()
  {return deck.isEmpty();}

  /** Gets the card "ready" for its further activation: removes it from
   the CharacterDeck, updates its cost (and with is its uses) and returns it
   * @param index  the position of the selected card in the Character Deck
   * @return the desired card
   */
  public CharacterCard drawCard(int index)
  {
    CharacterCard card = getCard(index);
    deck.remove(index);
    card.updateCost();
    game.notify(game.modelToJson());
    return card;
  }

  public void SetupCards(Pouch pouch)
  {
    for(int i=0; i< deck.size(); i++)
    {
      if(deck.get(i) instanceof Priest)
        ((Priest) deck.get(i)).updateStudents(pouch);
      else if(deck.get(i) instanceof Princess)
        ((Princess) deck.get(i)).updateStudents(pouch);
      i++;
    }
  }

  public ArrayList<CharacterCard> getDeck()
    {return deck;}

  public CharacterCard getCard(int index)
  {return deck.get(index);}

}
