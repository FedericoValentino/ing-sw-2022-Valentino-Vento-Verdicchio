package model.cards;

public abstract class CharacterCard {
    private int baseCost;
    private int uses;
    private int currentCost;

    public CharacterCard(int baseCost)                 //(enry) Ho modificato il costruttore base delle character cards: com'era implementato prima
    {                                                  //prevedeva di ricevere in ingresso non solo baseCost, che ha senso, ma anche currentCost e uses.
        this.baseCost = baseCost;                      //Al momento della crezione delle carte però currentCost sarà identico a baseCost e uses = 0 per
        this.currentCost = baseCost;                   //ovvi motivi. Ho cambiato anche tutti i costruttori delle singole carte per matchare i cambiamenti
        this.uses = 0;
    }

    public void playCard(){

    }
    public void updateCost() {

    }

    public int getBaseCost() {return baseCost;}
    public int getUses() {return uses;}
    public int getCurrentCost() {return currentCost;}
}
