package model.cards;

import model.boards.token.Col;

public class TruffleHunter extends CharacterCard{
    private int idCard;
    private Col ChosenColor;
    public TruffleHunter()
    {
        super(); //costruttore sopra classe
        this.baseCost=3;
        this.idCard=9;
        this.currentCost=this.baseCost;
        this.ChosenColor = null;
    }

    public void setChosenColor(Col c)
    {
        this.ChosenColor = c;
    }

    public Col getChosenColor()
    {
        return ChosenColor;
    }

    public int getIdCard() {
        return idCard;
    }
}
