package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.util.ArrayList;

/**
 * Deck of inactive character cards
 */
public class LightCharDeck extends Observable
{
    private ArrayList<LightCharacterCard> lightCharDeck;
    private LightCharacterFactory factory = new LightCharacterFactory();

    /**
     * Class constructor, acts similarly to the Active deck
     * @param cards list of character cards
     */
    public LightCharDeck(ArrayList<CharacterCard> cards)
    {
        this.lightCharDeck = new ArrayList<>();
        for(CharacterCard card : cards)
        {
            lightCharDeck.add(factory.characterCreator(card));
        }
    }

    /**
     * Contrary to the Active deck, it simply clears the deck and adds the updated cards back in
     * @param light updated deck
     */
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
