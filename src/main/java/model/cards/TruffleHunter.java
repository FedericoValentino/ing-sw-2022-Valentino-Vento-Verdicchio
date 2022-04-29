package model.cards;

import model.boards.token.Col;

public class TruffleHunter extends CharacterCard{
    private int idCard;
    private Col ChosenColor;

    /** Class constructor */
    public TruffleHunter()
    {
        super();
        this.baseCost=3;
        this.idCard=9;
        this.currentCost=this.baseCost;
        this.ChosenColor = null;
    }

    /** Updates the color chosen by the player at the moment of activation
     * @param c  the color chosen by the player that needs to be ignored during the influence calculation
     */
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
