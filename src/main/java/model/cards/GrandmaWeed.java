package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;
    private int idCard;
    public GrandmaWeed()
    {
        super(); //costruttore sopra classe
        this.noEntry = 4;
        this.baseCost=2;
        this.currentCost=this.baseCost;
        this.idCard=5;
    }

    //Updates the noEntry tiles using an input that can be positive or negative
    public void updateNoEntry(int value){noEntry += value;}

    public int getIdCard() {
        return idCard;
    }
    public int getNoEntry(){return noEntry;}
}
