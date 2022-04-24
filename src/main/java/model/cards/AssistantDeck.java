package model.cards;

import java.util.ArrayList;


public class AssistantDeck
{
    private ArrayList<AssistantCard> deck;
    private final String playerName;
    private final String wizard;

    public AssistantDeck(String wizard, String playerName)      //crea nuovo deck, con ingresso wizard scelto dal player, e il player da assegnare
    {
        this.wizard = wizard;
        this.playerName = playerName;
        int j=1;
        this.deck = new ArrayList<>();
        for(int i = 1; i<=10; i++)
            {
                this.deck.add(new AssistantCard(j, i));     //il valore di movimento va da 1 a 5, quello del turno da 1 a 10
                if((i % 2) == 0)                               // sono accoppiati sulle singole carte nel seguente modo (1,1 - 1,2 - 2,3 - 2,4 ecc)
                    {                                          // dunque incremento j (relativo al movimento) solo quando i è pari
                        j++;
                    }

            }
    }

    public boolean checkEmpty()         //checka se il deck è vuoto, può esser utile per controlli vittoria complessi..
    {
        return deck.isEmpty();
    }

    public AssistantCard extractCard(int cardPosition)     //estrae una carta dal deck e lo aggiorna
    {
        AssistantCard card = deck.get(cardPosition);
        deck.remove(cardPosition);
        return card;
    }


    public String getPlayerName()
        {return playerName;}

    public String getWizard()
        {return wizard;}

    public ArrayList<AssistantCard> getDeck()
        {return deck;}

    public AssistantCard getCard(int index)
    {
       return deck.get(index);
    }

}
