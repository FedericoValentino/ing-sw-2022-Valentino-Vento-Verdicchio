package model.cards;

public class Postman extends CharacterCard{
    private int idCard;
    public Postman()
    {
        super(); //costruttore sopra classe
        this.baseCost=1;
        this.currentCost=this.baseCost;
        this.idCard=4;
    }
    public int getIdCard() {
        return idCard;
    }
}
