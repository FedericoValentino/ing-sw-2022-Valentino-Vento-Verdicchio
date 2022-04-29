package Client.Messages.ActionMessages;

import model.boards.token.Col;

public class PlayCharacterEffect implements StandardActionMessage
{
    private int characterId;
    private int first;
    private int second;
    private Col studentColor;

    public PlayCharacterEffect(int characterId, int first, int second, Col studentColor) {
        this.characterId = characterId;
        this.first = first;
        this.second = second;
        this.studentColor = studentColor;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public Col getStudentColor() {
        return studentColor;
    }
}
