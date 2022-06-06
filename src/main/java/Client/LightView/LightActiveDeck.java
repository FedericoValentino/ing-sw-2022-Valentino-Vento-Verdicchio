package Client.LightView;

import Observer.Observable;
import model.cards.CharacterCard;

import java.util.ArrayList;

public class LightActiveDeck extends Observable
{
    private ArrayList<CharacterCard> lightActiveDeck;

    public LightActiveDeck(ArrayList<CharacterCard> cards)
    {
        lightActiveDeck = cards;
    }

    public void updateActive(LightActiveDeck light)
    {
        if(!lightActiveDeck.isEmpty()) {
            this.lightActiveDeck = light.getLightActiveDeck();
            System.out.println("Updated LightActiveCharDeck");
            notifyLight(lightActiveDeck.get(0));
        }
    }

    public ArrayList<CharacterCard> getLightActiveDeck() {
        return lightActiveDeck;
    }
}
