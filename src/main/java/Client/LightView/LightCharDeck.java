package Client.LightView;

import Observer.Observable;
import model.cards.CharacterCard;

import java.util.ArrayList;

public class LightCharDeck extends Observable
{
    private ArrayList<CharacterCard> lightCharDeck;

    public LightCharDeck(ArrayList<CharacterCard> cards)
    {
        lightCharDeck = cards;
    }

    public void updateCharDeck(LightCharDeck light)
    {
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.lightCharDeck = light.getLightCharDeck();
            System.out.println("Updated LightCharDeck");
            notifyLight(this);
        }
    }


    public ArrayList<CharacterCard> getLightCharDeck() {
        return lightCharDeck;
    }

    public CharacterCard getCard(int index)
    {
       return lightCharDeck.get(index);
    }
}
