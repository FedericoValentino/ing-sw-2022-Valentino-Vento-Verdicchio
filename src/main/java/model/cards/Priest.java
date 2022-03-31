package model.cards;

public class Priest extends CharacterCard {

    private int idCard;
    public Priest() {
        super();
        this.baseCost=1;
        this.currentCost=this.baseCost;
        this.idCard=1;
    }

    public int getIdCard() {
        return idCard;
    }

}