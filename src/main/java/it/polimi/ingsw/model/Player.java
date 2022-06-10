package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.Wizard;
import it.polimi.ingsw.model.cards.AssistantDeck;


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
  private CurrentGameState game;


  /** Class constructor
   * @param nome  the chosen nickname of the player
   * @param col  the color of the towers assigned to the player
   * @param towerAmount  the amount of towers assigned to the player's school, depending on the game mode
   * @param wizard  the wizard chosen by the player
   * @param expertGame  the game mode chosen by the host: determines whether the player is assigned coins
   */
  public Player(String nome, ColTow col, int towerAmount, Wizard wizard, boolean expertGame, CurrentGameState game)
  {
    this.game = game;
    this.nome = nome;
    this.school = new School(col, towerAmount, game);
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


  /** Plays the Assistant Card chosen by the player, by removing it from the Assistant Deck and by
   updating the player's fields "Value" and "MaxMotherMovement" accordingly
   * @param cardPosition  the position of the chosen card into the Assistant Deck
   */
  public void chooseAssistantCard(int cardPosition)
  {
    currentAssistantCard = assistantDeck.extractCard(cardPosition);
    MaxMotherMovement = currentAssistantCard.getMovement();
    value = currentAssistantCard.getValue();
  }

  /** Removes the last played Assistant Card from the Current Assistant Card field and places it into the
   Last Played Card field
   */
  public void Discard()
  {
    lastPlayedCard = currentAssistantCard;
    currentAssistantCard = null;
    this.MaxMotherMovement = 0;
  }

  /** Calculates the amount of coins gained by the players in relation to the "Checkpoints" in his Dining Room
   * @return the amount of coins gained
   */
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

  /** Updates the player's reserve of coins by adding the desired value to it
   * @param gain  the amount of coins that the player has gained or lost
   */
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

  /** Modifies the Maximum Mother Nature Movement by adding to it the desired value
   * @param movement  the desired amount used to increase or decrease the MaxMotherMovement field
   */
  public void updateMaxMotherMovement(int movement)
  {
    MaxMotherMovement += movement;
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

  public boolean isTowerOwner() {
    return TowerOwner;
  }
}
