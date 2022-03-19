package model.cards;

import model.cards.Card;

public class Assistant implements Card
{
  private int movement;
  private int value;
  private String wizard;

  public Assistant(){}

  public int getMovement(){return movement;}
  public int getValue(){ return value;}
  public String getWizard(){ return wizard;}
  public void playCard(){
    //da implementare
  }

}
