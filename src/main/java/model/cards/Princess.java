package model.cards;


public class Princess extends CharacterCard {
    private int idCard;
    public Princess() {
        super(); //costruttore sopra classe
        this.baseCost=2;
        this.idCard=11;
        this.currentCost=this.baseCost;
    }
    public int getIdCard() {
        return idCard;
    }
}
