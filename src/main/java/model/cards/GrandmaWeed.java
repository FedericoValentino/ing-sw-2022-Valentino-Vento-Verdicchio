package model.cards;

public class GrandmaWeed extends CharacterCard{

    private int noEntry;
    private int idCard;

    /** Class constructor */
    public GrandmaWeed()
    {
        super();
        this.noEntry = 4;
        this.baseCost=2;
        this.currentCost=this.baseCost;
        this.idCard=5;
    }


    /** Updates the noEntry tiles using an input that can be positive or negative
     * @param value  the value to sum to the current amount of NoEntry tiles
     */
    public void updateNoEntry(int value){noEntry += value;}

    public int getIdCard() {
        return idCard;
    }
    public int getNoEntry(){return noEntry;}
}
