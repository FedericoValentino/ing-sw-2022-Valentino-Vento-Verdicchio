package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;

    public GrandmaWeed(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
        this.noEntry = 0;
    }

    public void effect()
    {

    }

    public int getNoEntry(){return noEntry;}
}
