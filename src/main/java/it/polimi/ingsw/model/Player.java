package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import it.polimi.ingsw.model.cards.assistants.AssistantDeck;

/**
 * Player class contains everything needed to represent and manipulate a Player object, such as its schools, coins, assistants
 * and other helpful information.
 */
public class Player
{
  private String name;
  private int coinAmount;
  private AssistantCard currentAssistantCard;
  private AssistantCard lastPlayedCard;
  private AssistantDeck assistantDeck;
  public School school;
  private int maxMotherMovement;
  private int movementValue;
  private int value;
  private final boolean towerOwner;


  /**
   * Class constructor. It instantiates the Player's attributes and in particular it gives a random value to the Value
   * parameter at the start of the game: this ensures that the first player to play will be chosen randomly through existing
   * methods in classes higher in the hierarchy.
   * @param name  the chosen nickname of the player
   * @param col  the color of the towers assigned to the player
   * @param towerAmount  the amount of towers assigned to the player's school, depending on the game mode
   * @param wizard  the wizard chosen by the player
   * @param expertGame  the game mode chosen by the host: determines whether the player is assigned coins
   */
  public Player(String name, ColTow col, int towerAmount, Wizard wizard, boolean expertGame)
  {
    this.name = name;
    this.school = new School(col, towerAmount);
    this.towerOwner = towerAmount != 0;
    this.assistantDeck = new AssistantDeck(wizard);
    this.currentAssistantCard = null;
    this.lastPlayedCard = null;
    this.movementValue = 0;
    this.maxMotherMovement = 0;
    this.value = (int) (Math.random() * 10 + 1);
    if(expertGame)
      //TODO revert this to 1 later
      this.coinAmount = 99;
    else
      this.coinAmount = 0;
  }


  /**
   * Plays the Assistant Card chosen by the player, by removing it from the Assistant Deck and by
   * updating the player's fields "Value" and "MaxMotherMovement" accordingly
   * @param cardPosition  the position of the chosen card into the Assistant Deck
   */
  public void chooseAssistantCard(int cardPosition)
  {
    currentAssistantCard = assistantDeck.extractCard(cardPosition);
    maxMotherMovement = currentAssistantCard.getMovement();
    value = currentAssistantCard.getValue();
  }

  /**
   * Removes the last played Assistant Card from the Current Assistant Card field and places it into the Last Played Card field
   */
  public void discard()
  {
    lastPlayedCard = currentAssistantCard;
    currentAssistantCard = null;
    this.maxMotherMovement = 0;
  }

  /**
   * Calculates the amount of coins gained by the players in relation to the "Checkpoints" in his Dining Room. If there
   * are coins to gain, it also updates the "Checkpoints" in the dining room
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
          school.updateCheckpoint(i, true);
        }
      }
      updateCoins(gainedCoins);
      return gainedCoins;
  }

  /**
   * Updates the player's reserve of coins by adding the desired value to it
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

  /**
   * Modifies the Maximum Mother Nature Movement by adding to it the desired value
   * @param movement  the desired amount used to increase or decrease the MaxMotherMovement field
   */
  public void updateMaxMotherMovement(int movement)
  {
    maxMotherMovement += movement;
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
    return maxMotherMovement;
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
  public String getName() {
    return name;
  }

  public boolean isTowerOwner() {
    return towerOwner;
  }
}
