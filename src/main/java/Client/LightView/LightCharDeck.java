package Client.LightView;

import model.cards.CharacterCard;

import java.util.ArrayList;

public class LightCharDeck
{
    private ArrayList<CharacterCard> lightCharDeck;

    public LightCharDeck(ArrayList<CharacterCard> cards)
    {
        lightCharDeck = cards;
    }


    public ArrayList<CharacterCard> getLightCharDeck() {
        return lightCharDeck;
    }
}
