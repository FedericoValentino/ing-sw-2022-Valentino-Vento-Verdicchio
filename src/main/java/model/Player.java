package model;

import model.boards.School;
import model.boards.token.ColTow;
import model.cards.AssistantCard;
import model.cards.AssistantDeck;

public class Player
{
  private String nome;
  private int coinAmount;
  private AssistantCard currentAssistantCard;
  private AssistantCard lastPlayedCard;
  private AssistantDeck assistantDeck;
  private School school;
  private int MaxMotherMovement;
  private int movementValue;
  private int value;

  public Player(String nome, ColTow col, int towerAmount, String wizard)
  {
    this.nome = nome;
    this.school = new School(col, , towerAmount);
    this.assistantDeck = new AssistantDeck(wizard, nome);
    this.coinAmount = 1;
    this.currentAssistantCard = null;
    this.lastPlayedCard = null;
    this.movementValue = 0;
    this.MaxMotherMovement = 0;
    this.value = 0;

  }

  public void chooseAssistantCard(int idCard)
  {
    currentAssistantCard = assistantDeck.extractCard(idCard);
    MaxMotherMovement = currentAssistantCard.getMovement();
    value = currentAssistantCard.getValue();
  }

  public void gainCoin()
  {

  }

  public School getPlayerSchool()
  {
    return this.school;
  }

  public AssistantCard getCurrentAssistantCard() {
    return currentAssistantCard;
  }

  public AssistantCard getLastPlayedCard() {
    return lastPlayedCard;
  }

  public AssistantDeck getAssistantDeck() {
    return assistantDeck;
  }

  public int getCoinAmount() {
    return coinAmount;
  }

  public int getMaxMotherMovement() {
    return MaxMotherMovement;
  }
  public int getMovementValue() {
    return movementValue;
  }

  public int getValue() {
    return value;
  }

  public School getSchool() {
    return school;
  }

  public String getNome() {
    return nome;
  }
}
