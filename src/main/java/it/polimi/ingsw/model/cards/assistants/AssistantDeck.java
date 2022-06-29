package it.polimi.ingsw.model.cards.assistants;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.util.ArrayList;

/**
 * Represents a collection of Assistant Cards. Every Player holds one assistant Deck, and initially every Deck holds ten cards.
 */
public class AssistantDeck
{
    private ArrayList<AssistantCard> deck;
    private final Wizard wizard;

    /**
     * Class constructor. Creates a Deck of Assistant Cards with a wizard and a player assigned. Calls the
     * Assistant Card constructor and assigns a Turn Value and Maximum Mother Movement value to the card: since the turn values
     * go from 1 to 10 and the movement values from 1 to 5, it increments the counter relative to the movement values when
     * encountering even turn values
     * @param wizard  the wizard chosen by the player
     */
    public AssistantDeck(Wizard wizard)
    {
        this.wizard = wizard;
        int j=1;
        this.deck = new ArrayList<>();

        for(int i = 1; i<=10; i++)
            {
                this.deck.add(new AssistantCard(j, i));
                if((i % 2) == 0)
                    {
                        j++;
                    }

            }
    }

    /**
     * Returns the desired card and removes it from the deck
     * @param cardPosition  the index identifying the position of the card into the Assistant Deck
     * @return the selected card
     */
    public AssistantCard extractCard(int cardPosition)
    {
        AssistantCard card = deck.get(cardPosition);
        deck.remove(cardPosition);
        return card;
    }


    public Wizard getWizard()
        {return wizard;}

    public ArrayList<AssistantCard> getDeck()
        {return deck;}

}
