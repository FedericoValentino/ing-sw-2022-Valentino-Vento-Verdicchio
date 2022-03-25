package model;

import model.cards.AssitantDeck;

public class Player
{
  private int playerId;
  private String nome;
  private int coinAmount;
  private Card currentAssistantCard;
  private AssitantDeck assistantDeck;
  private AssitantDeck characterDeck;
  private Character[] activeCharacterCards;
  private int maxMotherMovement;
  private int movementValue;

  public Player(){}

  public void chooseAssistantCard(int idCard){} //immagino, non ricordo bene cosa faccia
  /*
  public int getPlayerId();
  public String getName();
  public int getCoinAmount();
  public Card getCurrentAssistantCard();
  public Deck getAssistantDeck();
  public Deck getCharacterDeck();
  public Character[] getActiveCharacterCards();
  public int getMovementValue();
  public int getMaxMotherMovement();
  */
}
