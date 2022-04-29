package model.cards;

public class Herald extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Herald()
    {
        super();
        this.baseCost=3;
        this.currentCost=this.baseCost;
        this.idCard=3;
    }
    public int getIdCard() {
        return idCard;
    }
}