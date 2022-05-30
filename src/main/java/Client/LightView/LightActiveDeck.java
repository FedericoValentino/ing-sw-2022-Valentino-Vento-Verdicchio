package Client.LightView;

import model.cards.CharacterCard;

import java.util.ArrayList;

public class LightActiveDeck
{
    private ArrayList<CharacterCard> lightActiveDeck;

    public LightActiveDeck(ArrayList<CharacterCard> cards)
    {
        lightActiveDeck = cards;
    }


    public ArrayList<CharacterCard> getLightActiveDeck() {
        return lightActiveDeck;
    }
}
