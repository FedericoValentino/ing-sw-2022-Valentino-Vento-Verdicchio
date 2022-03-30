package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;
    private int idCard;
    public GrandmaWeed(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
        this.noEntry = 0;
        this.idCard=5;
    }

    public int getIdCard() {
        return idCard;
    }
    public int getNoEntry(){return noEntry;}
}
