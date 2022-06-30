package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.characters.Jester;
import it.polimi.ingsw.model.cards.characters.Priest;
import it.polimi.ingsw.model.cards.characters.Princess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The deck that will hold the inactive character cards. It contains methods to draw a selected card and to manipulate
 * those cards that need extra processing in initial stages
 */
public class CharacterDeck implements Serializable
{
  private ArrayList<CharacterCard> deck;

  /**
   * Class constructor. Creates a list of integers, from 0 to 11, representing the ordinal value of the Character Name enumeration.
   * The list is then shuffled, and the first three integers are used to add a character card, by calling the Character Factory
   * using the appropriate Character Name. This ensures that the deck is always formed by random cards every match.
   */
  public CharacterDeck()
  {
    this.deck = new ArrayList<>();
    ArrayList<Integer> cardOrdinals = new ArrayList<>();
    for(int i = 0; i < 12; i++)
      cardOrdinals.add(i);
    Collections.shuffle(cardOrdinals);
    //TODO to remove in the future
    cardOrdinals.add(0, 10);
    cardOrdinals.add(1, 3);
    for(int i = 0; i < 3; i++)
    {
      deck.add(CharacterCreator.getCharacter(CharacterName.values()[cardOrdinals.get(0)], i));
      cardOrdinals.remove(0);
    }
  }


  /**
   * Gets the card "ready" for its further activation: removes it from
   * the CharacterDeck, updates its cost (and with it, its uses) and returns it
   * @param card the selected card in the Character Deck
   * @return the desired card
   */
  public CharacterCard drawCard(CharacterCard card)
  {
    deck.remove(card);
    card.updateCost();
    return card;
  }

  /**
   * Handles the further processing needed by some of the more complex character cards in the setup phase. Now, it fills
   * with students the cards that need them
   * @param pouch an instance of the game pouch
   */
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
}
