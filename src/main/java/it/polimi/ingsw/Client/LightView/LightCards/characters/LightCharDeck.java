package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.util.ArrayList;

public class LightCharDeck extends Observable
{
    private ArrayList<LightCharacterCard> lightCharDeck;
    private LightCharacterFactory factory = new LightCharacterFactory();

    public LightCharDeck(ArrayList<CharacterCard> cards)
    {
        this.lightCharDeck = new ArrayList<>();
        for(CharacterCard card : cards)
        {
            lightCharDeck.add(factory.characterCreator(card));
        }
    }

    public void updateCharDeck(LightCharDeck light)
    {
        lightCharDeck.clear();
        lightCharDeck.addAll(light.getLightCharDeck());
    }


    public ArrayList<LightCharacterCard> getLightCharDeck() {
        return lightCharDeck;
    }

    public LightCharacterCard getCard(int index)
    {
       return lightCharDeck.get(index);
    }
}
