package it.polimi.ingsw.Client.LightView;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

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
        this.lightCharDeck = light.getLightCharDeck();
        System.out.println("Updated LightCharDeck");
        notifyLight(this);
    }


    public ArrayList<CharacterCard> getLightCharDeck() {
        return lightCharDeck;
    }

    public CharacterCard getCard(int index)
    {
       return lightCharDeck.get(index);
    }
}
