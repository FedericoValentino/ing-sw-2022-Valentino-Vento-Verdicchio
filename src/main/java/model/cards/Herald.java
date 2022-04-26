package model.cards;

public class Herald extends CharacterCard{
    private int idCard;
    public Herald()
    {
        super(); //costruttore sopra classe
        this.baseCost=3;
        this.currentCost=this.baseCost;
        this.idCard=3;
    }
    public int getIdCard() {
        return idCard;
    }
}