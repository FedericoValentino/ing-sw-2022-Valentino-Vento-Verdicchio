package model.cards;

public class Knight extends CharacterCard{
    private int idCard;
    public Knight(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
    }
    public int getIdCard() {
        return idCard;
    }
}
