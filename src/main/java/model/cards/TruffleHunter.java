package model.cards;

public class TruffleHunter extends CharacterCard{
    private int idCard;
    public TruffleHunter()
    {
        super(); //costruttore sopra classe
        this.baseCost=3;
        this.currentCost=this.baseCost;
    }
    public int getIdCard() {
        return idCard;
    }
}
