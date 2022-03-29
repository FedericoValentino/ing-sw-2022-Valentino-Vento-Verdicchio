package model.cards;

public class Centaur extends CharacterCard{
    private int idCard;
    public Centaur(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
        this.idCard=6;
    }
    public int getIdCard() {
        return idCard;
    }
}
