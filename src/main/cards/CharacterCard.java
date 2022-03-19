package model.cards;

public abstract class CharacterCard {
    private int baseCost;
    private int uses;
    private int currentCost;

    public CharacterCard(int baseCost, int uses, int currentCost)
    {
        this.baseCost=baseCost;
        this.currentCost=currentCost;
        this.uses=uses;
    }

    public void playCard(){

    }
    public void updateCost() {

    }

    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
}
