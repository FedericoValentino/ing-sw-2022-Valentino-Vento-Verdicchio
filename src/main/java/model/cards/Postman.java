package model.cards;

public class Postman extends CharacterCard{
    private int idCard;
    public Postman(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
        this.idCard=4;
    }
    public int getIdCard() {
        return idCard;
    }
}
