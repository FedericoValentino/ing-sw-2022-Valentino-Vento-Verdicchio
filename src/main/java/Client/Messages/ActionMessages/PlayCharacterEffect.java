package Client.Messages.ActionMessages;

import model.boards.token.Col;

public class PlayCharacterEffect implements StandardActionMessage
{
    private int characterId;
    private int student;
    private int island;
    private String currentPlayer;
    private Col studentColor;

    public PlayCharacterEffect(int characterId, int student, int island, String currentPlayer, Col studentColor) {
        this.characterId = characterId;
        this.student = student;
        this.island = island;
        this.currentPlayer = currentPlayer;
        this.studentColor = studentColor;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getFirst() {
        return student;
    }

    public int getSecond() {
        return island;
    }

    public String getThird(){
        return currentPlayer;
    }

    public Col getStudentColor() {
        return studentColor;
    }
}
