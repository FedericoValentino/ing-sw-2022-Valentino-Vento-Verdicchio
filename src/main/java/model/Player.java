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
  public School school;
  private int MaxMotherMovement;
  private int movementValue;
  private int value;
  private boolean TowerOwner;

  public Player(String nome, ColTow col, int towerAmount, String wizard, boolean expertGame)
  {
    this.nome = nome;
    this.school = new School(col, towerAmount);
    if(towerAmount != 0)
    {
      this.TowerOwner = true;
    }
    else
    {
      this.TowerOwner = false;
    }
    this.assistantDeck = new AssistantDeck(wizard, nome);
    this.currentAssistantCard = null;
    this.lastPlayedCard = null;
    this.movementValue = 0;
    this.MaxMotherMovement = 0;
    this.value = (int) (Math.random() * 10 + 1);
    if(expertGame)
      this.coinAmount = 1;
    else
      this.coinAmount = 0;
  }

  public void chooseAssistantCard(int cardPosition)
  {
    currentAssistantCard = assistantDeck.extractCard(cardPosition);
    MaxMotherMovement = currentAssistantCard.getMovement();
    value = currentAssistantCard.getValue();
  }

  public void Discard()
  {
    lastPlayedCard = currentAssistantCard;
    currentAssistantCard = null;
  }

  public int gainCoin()
  {
      int gainedCoins = 0;
      for(int i = 0; i < 5; i++)
      {
        while(school.getDiningRoom()[i] > school.getRoomCheckpoints()[i])
        {
          gainedCoins++;
          school.updateCheckpoint(i);
        }
      }
      updateCoins(gainedCoins);
      return gainedCoins;
  }


  public void updateCoins(int gain)
    {
      if(coinAmount + gain < 0)
      {
        coinAmount = 0;
      }
      else
      {
        coinAmount += gain;
      }
    }

  public void updateMaxMotherMovement(int movement){MaxMotherMovement += movement;}

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

  public boolean isTowerOwner() {
    return TowerOwner;
  }
}
