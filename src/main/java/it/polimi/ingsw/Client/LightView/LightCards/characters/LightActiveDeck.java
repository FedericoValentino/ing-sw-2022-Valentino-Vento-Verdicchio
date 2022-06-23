package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.util.ArrayList;

public class LightActiveDeck extends Observable
{
    private ArrayList<LightCharacterCard> lightActiveDeck;
    private LightCharacterFactory factory = new LightCharacterFactory();

    public LightActiveDeck(ArrayList<CharacterCard> cards)
    {
        this.lightActiveDeck = new ArrayList<>();
        for(CharacterCard card : cards)
        {
            lightActiveDeck.add(factory.characterCreator(card));
        }
    }

    public void updateActive(LightActiveDeck light)
    {
        if(lightActiveDeck.isEmpty() && !light.getLightActiveDeck().isEmpty())
        {
            lightActiveDeck.add(light.getLightActiveDeck().get(0));
            notifyLight(light.getLightActiveDeck().get(0));
        }
        else if(!lightActiveDeck.isEmpty() && light.getLightActiveDeck().isEmpty())
        {
            lightActiveDeck.clear();
            notifyLight(null);
        }
    }

    public ArrayList<LightCharacterCard> getLightActiveDeck() {
        return lightActiveDeck;
    }
}
