package Client.Messages.ActionMessages;

import model.boards.token.CharacterName;
import model.boards.token.Col;

public class PlayCharacterEffect implements StandardActionMessage
{
    private CharacterName name;
    private int student;
    private int island;
    private String currentPlayer;
    private Col studentColor;

    public PlayCharacterEffect(CharacterName name, int student, int island, String currentPlayer, Col studentColor) {
        this.name = name;
        this.student = student;
        this.island = island;
        this.currentPlayer = currentPlayer;
        this.studentColor = studentColor;
    }

    public CharacterName getCharacterName() {
        return name;
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
