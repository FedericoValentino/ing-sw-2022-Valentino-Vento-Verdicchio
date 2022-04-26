package model.cards;

public abstract class CharacterCard {
    protected int idCard;
    protected int baseCost;
    protected int uses;
    protected int currentCost;

    /* BaseCost, ID, and other characteristics, are detailed in
    the constructor of each card */
    public CharacterCard()
    {
        this.uses = 0;
    }

    //Updates the uses of the card upon activation and updates the currentCost
    public void updateCost() {
        this.uses++;
        currentCost=baseCost+uses;
    }

    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
    public abstract int getIdCard();
}
