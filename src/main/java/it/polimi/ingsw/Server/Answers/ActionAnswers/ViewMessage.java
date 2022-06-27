package it.polimi.ingsw.Server.Answers.ActionAnswers;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.CharacterDeck;

import java.util.ArrayList;

public class ViewMessage extends StandardActionAnswer
{

    private String jsonView;
    private CharacterDeck CurrentCharacterDeck;
    private ArrayList<CharacterCard> CurrentActiveCharacterCard;

    /**
     * Class Constructor, used to send the current game model to the client via JSON serialization, the Character deck
     * and the active character card are not serialized
     */
    public ViewMessage(String jsonView, CharacterDeck CD, ArrayList<CharacterCard> CACD) {
        this.jsonView = jsonView;
        this.CurrentCharacterDeck = CD;
        this.CurrentActiveCharacterCard = CACD;
        super.type = ACTIONANSWERTYPE.VIEW;
    }

    public String getJsonView() {
        return jsonView;
    }

    public CharacterDeck getCurrentCharacterDeck() {
        return CurrentCharacterDeck;
    }

    public ArrayList<CharacterCard> getCurrentActiveCharacterCard() {
        return CurrentActiveCharacterCard;
    }
}
