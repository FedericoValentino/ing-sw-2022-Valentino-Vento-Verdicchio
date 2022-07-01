package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.util.ArrayList;

/**
 * LightActiveDeck represent the arraylist of active character cards
 */
public class LightActiveDeck extends Observable
{
    private ArrayList<LightCharacterCard> lightActiveDeck;
    private LightCharacterFactory factory = new LightCharacterFactory();

    /**
     * Class constructor. Receives a list of CharacterCards in order to create corresponding LightCharacetrCards for further use,
     * using a factory pattern
     * @param cards the list of character cards
     */
    public LightActiveDeck(ArrayList<CharacterCard> cards)
    {
        this.lightActiveDeck = new ArrayList<>();
        for(CharacterCard card : cards)
        {
            lightActiveDeck.add(factory.characterCreator(card));
        }
    }

    /**
     * Receiving the active deck directly from the view, the update function differentiates between two cases:
     * - if the active deck is empty and the inactive deck isn't, it adds the first card in the list and notifies the observer
     * - in the opposite scenario, it calls the clear function on itself, and passes a null object in the "notify"
     * @param light
     */
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
