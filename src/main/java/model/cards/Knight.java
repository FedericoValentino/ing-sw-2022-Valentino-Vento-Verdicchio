package model.cards;

public class Knight extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Knight()
    {
        super();
        this.baseCost=2;
        this.currentCost=this.baseCost;
        this.idCard=8;
    }
    public int getIdCard() {
        return idCard;
    }
}
