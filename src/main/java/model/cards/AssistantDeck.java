package model.cards;

import model.boards.token.Wizard;

import java.util.ArrayList;


public class AssistantDeck
{
    private ArrayList<AssistantCard> deck;
    private final String playerName;
    private final Wizard wizard;

    /** Class constructor. Creates a Deck of Assistant Cards with a wizard and a player assigned. Calls the
     Assistant Card constructor and assigns a Turn Value and Maximum Mother Movement value to the card, as specified below
     * @param wizard  the wizard chosen by the player
     * @param playerName  the player to which we must assign the Deck
     */
    public AssistantDeck(Wizard wizard, String playerName)
    {
        this.wizard = wizard;
        this.playerName = playerName;
        int j=1;
        this.deck = new ArrayList<>();

        /* We ensure here that the cards are created with the correct values: the Value
        of a card goes from 1 to 10, and the Movement from 1 to 5. Each deck contains 10 cards,
        where the Value increments card by card, and the Movement every 2 cards (for example
        1,1 - 2,1 - 3,2 - 4,2 - 5,2 etc).
        To ensure this, the counter responsible for the Movement is incremented every time
        the counter i is an even number.
         */
        for(int i = 1; i<=10; i++)
            {
                this.deck.add(new AssistantCard(j, i));
                if((i % 2) == 0)
                    {
                        j++;
                    }

            }
    }


    /** Checks if the deck is empty, useful for some end game circumstances
     * @return  whether the deck is empty
     */
    public boolean checkEmpty()
    {
        return deck.isEmpty();
    }

    /** Returns the desired card and removes it from the deck
     * @param cardPosition  the index identifying the position of the card into the Assistant Deck
     * @return the selected card
     */
    public AssistantCard extractCard(int cardPosition)
    {
        AssistantCard card = deck.get(cardPosition);
        deck.remove(cardPosition);
        return card;
    }


    public String getPlayerName()
        {return playerName;}

    public Wizard getWizard()
        {return wizard;}

    public ArrayList<AssistantCard> getDeck()
        {return deck;}

    public AssistantCard getCard(int index)
    {
       return deck.get(index);
    }

}
