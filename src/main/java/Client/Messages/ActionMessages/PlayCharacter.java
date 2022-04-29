package Client.Messages.ActionMessages;

import model.boards.token.Col;

public class PlayCharacter implements StandardActionMessage
{
    private int characterId;


    public PlayCharacter(int characterId) {
        this.characterId = characterId;

    }

    public int getCharacterId() {
        return characterId;
    }

}
