package model.cards;

public class Centaur extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Centaur()
    {
        super();
        super.baseCost=3;
        super.currentCost=this.baseCost;
        this.idCard=6;
    }

    public int getIdCard() {
        return idCard;
    }
}
