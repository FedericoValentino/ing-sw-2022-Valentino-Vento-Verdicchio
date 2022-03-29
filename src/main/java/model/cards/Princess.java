package model.cards;


public class Princess extends CharacterCard {
    private int idCard;
    public Princess(int baseCost) {
        super(baseCost); //costruttore sopra classe
    }
    public int getIdCard() {
        return idCard;
    }
}
