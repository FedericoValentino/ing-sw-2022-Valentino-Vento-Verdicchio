package Server.Answers.ActionAnswers;

import model.cards.CharacterDeck;

public class ViewMessage implements StandardActionAnswer
{
    private String jsonView;
    private CharacterDeck CurrentCharacterDeck;

    public ViewMessage(String jsonView, CharacterDeck CD) {
        this.jsonView = jsonView;
        this.CurrentCharacterDeck = CD;
    }

    public String getJsonView() {
        return jsonView;
    }

    public CharacterDeck getCurrentCharacterDeck() {
        return CurrentCharacterDeck;
    }
}
