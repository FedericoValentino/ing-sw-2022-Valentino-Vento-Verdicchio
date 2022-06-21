package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.characters.Jester;
import it.polimi.ingsw.model.cards.characters.Priest;
import it.polimi.ingsw.model.cards.characters.Princess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
//
public class CharacterDeck implements Serializable
{
  private ArrayList<CharacterCard> deck;

  /** Class constructor. Creates every character Object, shuffles the collection, and eliminates 5 of them.
   This ensures a random Character Deck composition for each game instance
   */
  public CharacterDeck(CurrentGameState game)
  {
    this.deck = new ArrayList<>();
    ArrayList<Integer> cardOrdinals = new ArrayList<>();
    for(int i = 0; i < 12; i++)
      cardOrdinals.add(i);
    Collections.shuffle(cardOrdinals);
    for(int i = 0; i < 3; i++)
    {
      deck.add(CharacterCreator.getCharacter(CharacterName.values()[cardOrdinals.get(0)]));
      cardOrdinals.remove(0);
    }
  }

  /** Checks if the deck is empty
   * @return whether the deck is empty
   */
  public boolean checkEmpty()
  {return deck.isEmpty();}

  /** Gets the card "ready" for its further activation: removes it from
   the CharacterDeck, updates its cost (and with is its uses) and returns it
   * @param card the selected card in the Character Deck
   * @return the desired card
   */
  public CharacterCard drawCard(CharacterCard card)
  {
    deck.remove(card);
    card.updateCost();
    return card;
  }

  public void SetupCards(Pouch pouch)
  {
    for (CharacterCard card : deck) {
      if (card.getCharacterName().equals(CharacterName.PRIEST))
        for (int j = 0; j < 4; j++)
          ((Priest) card).updateStudents(pouch);
      else if (card.getCharacterName().equals(CharacterName.PRINCESS))
        for (int j = 0; j < 4; j++)
          ((Princess) card).updateStudents(pouch);
      else if(card.getCharacterName().equals(CharacterName.JESTER))
        for(int j = 0; j < 6; j++)
          ((Jester) card).updateStudents(pouch);
    }
  }


  public ArrayList<CharacterCard> getDeck()
    {return deck;}

  public CharacterCard getCard(int index)
  {return deck.get(index);}

}
