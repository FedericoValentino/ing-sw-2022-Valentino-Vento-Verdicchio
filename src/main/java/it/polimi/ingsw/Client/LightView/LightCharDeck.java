package it.polimi.ingsw.Client.LightView;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.util.ArrayList;

public class LightCharDeck extends Observable
{
    private ArrayList<LightCharacterCard> lightCharDeck;

    public LightCharDeck(ArrayList<CharacterCard> cards)
    {
        LightCharacterFactory factory = new LightCharacterFactory();
        this.lightCharDeck = new ArrayList<>();
        for(CharacterCard card : cards)
        {
            lightCharDeck.add(factory.characterCreator(card));
        }
    }

    public void updateCharDeck(LightCharDeck light)
    {
        this.lightCharDeck = light.getLightCharDeck();
        System.out.println("Updated LightCharDeck");
        notifyLight(this);
    }


    public ArrayList<LightCharacterCard> getLightCharDeck() {
        return lightCharDeck;
    }

    public LightCharacterCard getCard(int index)
    {
       return lightCharDeck.get(index);
    }
}
