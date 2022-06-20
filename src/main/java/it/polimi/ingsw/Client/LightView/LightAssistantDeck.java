package it.polimi.ingsw.Client.LightView;
//
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.Wizard;
import it.polimi.ingsw.model.cards.AssistantCard;

import java.util.ArrayList;

public class LightAssistantDeck
{
    private ArrayList<AssistantCard> deck;
    private String playerName;
    private Wizard wizard;

    /** Json constructor */
    public LightAssistantDeck(
            @JsonProperty("deck") ArrayList<AssistantCard> deck,
            @JsonProperty("playerName")String playerName,
            @JsonProperty("wizard") Wizard wizard)
    {
        this.deck = deck;
        this.playerName = playerName;
        this.wizard = wizard;
    }

    public ArrayList<AssistantCard> getDeck() {
        return deck;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
