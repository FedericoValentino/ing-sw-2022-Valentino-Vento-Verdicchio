package model.cards;

public class Knight extends CharacterCard{
    private int idCard;
    public Knight()
    {
        super(); //costruttore sopra classe
        this.baseCost=2;
        this.currentCost=this.baseCost;
    }
    public int getIdCard() {
        return idCard;
    }
}
