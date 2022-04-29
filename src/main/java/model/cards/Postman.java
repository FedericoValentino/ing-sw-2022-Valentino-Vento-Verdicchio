package model.cards;

public class Postman extends CharacterCard{
    private int idCard;

    /** Class constructor */
    public Postman()
    {
        super();
        this.baseCost=1;
        this.currentCost=this.baseCost;
        this.idCard=4;
    }
    public int getIdCard() {
        return idCard;
    }
}
