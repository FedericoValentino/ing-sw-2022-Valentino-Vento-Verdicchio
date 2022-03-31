package model.cards;

public class Centaur extends CharacterCard{
    private int idCard;
    public Centaur()
    {
        super(); //costruttore sopra classe
        super.baseCost=3;
        super.currentCost=this.baseCost;
        this.idCard=6;
    }
    public int getIdCard() {
        return idCard;
    }
}
