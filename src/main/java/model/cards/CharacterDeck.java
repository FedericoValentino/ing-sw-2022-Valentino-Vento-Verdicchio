package model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterDeck
{
  private ArrayList<CharacterCard> deck;

  public CharacterDeck()                                        //Riempie il deck di tutte e 8 le carte, dopodichè le mischia ed elimina
  {                                                             //le 5 carte in coda, tenendo le 3 "estratte" in testa
    this.deck.set(0, new Centaur(3));                   //implementazione brutta, da ripensare con currentGameState/controller
    this.deck.set(1, new GrandmaWeed(2));
    this.deck.set(2, new Knight(2));
    this.deck.set(3, new Herald(3));
    this.deck.set(4, new Postman(1));
    this.deck.set(5, new Priest(1));
    this.deck.set(6, new Princess(2));
    this.deck.set(7, new TruffleHunter(3));
    Collections.shuffle(this.deck);
    for (int i = 2; i<8; i++)
    {
      this.deck.remove(i);
    }
  }

  public boolean checkEmpty()
  {return deck.isEmpty();}

  public CharacterCard drawCard(CharacterCard card)           //Quando una carta viene scelta, viene semplicemente "attivata";
  {                                                           //dunque ne updateremo il current cost col metodo updateCost e ritorneremo
    card.updateCost();                                        //la carta affinchè sia piazzata nelle active nel current game state; la carta
    return card;                                              //non viene rimossa dal deck, o almeno credo che il return non implichi il remove.
  }                                                           //Possiamo passare l'oggetto direttamente, e non l'indice della lista, perchè, pur
                                                              //essendo tutti character, sono oggetti tutti diversi, di classi diverse: non c'è ambiguità (in teoria)
  public ArrayList<CharacterCard> getDeck()
    {return deck;}

  public CharacterCard getCard(CharacterCard card)
  {return card;}

}
