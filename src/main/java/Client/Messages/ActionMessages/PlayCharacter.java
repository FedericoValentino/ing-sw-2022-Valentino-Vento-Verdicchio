package Client.Messages.ActionMessages;

import model.boards.token.CharacterName;
import model.boards.token.Col;

public class PlayCharacter implements StandardActionMessage
{
    private CharacterName name;


    public PlayCharacter(CharacterName name) {
        this.name = name;

    }

    public CharacterName getCharacterName() {
        return name;
    }

}
