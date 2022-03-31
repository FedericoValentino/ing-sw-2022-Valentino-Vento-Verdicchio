package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;
    private int idCard;
    public GrandmaWeed()
    {
        super(); //costruttore sopra classe
        this.noEntry = 0;
        this.baseCost=2;
        this.currentCost=this.baseCost;
        this.idCard=5;
    }

    public int getIdCard() {
        return idCard;
    }
    public int getNoEntry(){return noEntry;}
}
