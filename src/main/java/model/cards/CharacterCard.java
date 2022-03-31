package model.cards;

public abstract class CharacterCard {
    protected int baseCost;
    protected int uses;
    protected int currentCost;

    //(enry) Ho modificato il costruttore base delle character cards: com'era implementato prima
    //prevedeva di ricevere in ingresso non solo baseCost, che ha senso, ma anche currentCost e uses.
    //Al momento della crezione delle carte però currentCost sarà identico a baseCost e uses = 0 per
    //ovvi motivi. Ho cambiato anche tutti i costruttori delle singole carte per matchare i cambiamenti
    public CharacterCard()
    {
        this.uses = 0;
    }

    public void updateCost() {
        this.uses++;
        currentCost=baseCost+uses;
    }

    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
}
