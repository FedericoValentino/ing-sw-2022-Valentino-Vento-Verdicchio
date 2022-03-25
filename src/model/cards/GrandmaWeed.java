package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;

    public GrandmaWeed(int baseCost, int uses, int currentCost,int noEntry)
    {
        super(baseCost,uses,currentCost); //costruttore sopra classe
        this.noEntry=noEntry;
    }

    public void effect()
    {

    }

    public int getNoEntry(){return noEntry;}
}
