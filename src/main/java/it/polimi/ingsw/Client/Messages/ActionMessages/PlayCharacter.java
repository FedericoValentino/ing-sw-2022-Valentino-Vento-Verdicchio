package it.polimi.ingsw.Client.Messages.ActionMessages;

import it.polimi.ingsw.model.boards.token.CharacterName;

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
