package model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterDeck
{
  private ArrayList<CharacterCard> deck;

  public CharacterDeck()                                       //Riempie il deck di tutte e 8 le carte, dopodichè le mischia ed elimina
  {
    this.deck = new ArrayList<CharacterCard>();                //le 5 carte in coda, tenendo le 3 "estratte" in testa
    this.deck.add(new Centaur());                   //implementazione brutta, da ripensare con currentGameState/controller
    this.deck.add(new GrandmaWeed());
    this.deck.add(new Knight());
    this.deck.add(new Herald());
    this.deck.add(new Postman());
    this.deck.add(new Priest());
    this.deck.add(new Princess());
    this.deck.add(new TruffleHunter());
    Collections.shuffle(this.deck);
    for (int i = 0; i<5; i++)
    {
      this.deck.remove(0);
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
