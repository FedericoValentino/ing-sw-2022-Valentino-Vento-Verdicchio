package Client.Messages.ActionMessages;

import model.boards.token.CharacterName;

public class PlayCharacter extends StandardActionMessage
{
    private CharacterName name;


    public PlayCharacter(CharacterName name) {
        this.name = name;
        super.type = ACTIONMESSAGETYPE.CHARACTER_PLAY;
    }

    public CharacterName getCharacterName() {
        return name;
    }

}
