package model.cards;

public class TruffleHunter extends CharacterCard{
    private int idCard;
    public TruffleHunter(int baseCost)
    {
        super(baseCost); //costruttore sopra classe
    }
    public int getIdCard() {
        return idCard;
    }
}
