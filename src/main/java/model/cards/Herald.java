package model.cards;

public class Herald extends CharacterCard{
    private int idCard;
    public Herald(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
        this.idCard=3;
    }
    public int getIdCard() {
        return idCard;
    }
}