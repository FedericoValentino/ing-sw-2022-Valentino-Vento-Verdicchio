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
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.lightActiveDeck = light.getLightActiveDeck();
            notifyLight(this);
        }
    }

    public ArrayList<CharacterCard> getLightActiveDeck() {
        return lightActiveDeck;
    }
}
