package model.cards;

public class Priest extends CharacterCard {

    private int idCard;
    public Priest(int baseCost) {
        super(baseCost);
        this.idCard=1;
    }

    public int getIdCard() {
        return idCard;
    }

}