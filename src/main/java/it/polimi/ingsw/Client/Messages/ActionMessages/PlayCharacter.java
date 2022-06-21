package it.polimi.ingsw.Client.Messages.ActionMessages;

import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.util.ArrayList;

public class PlayCharacter extends StandardActionMessage
{
    private CharacterName name;
    private ArrayList<Integer> student = new ArrayList<>();
    private ArrayList<Integer> island = new ArrayList<>();
    private String currentPlayer;
    private Col studentColor;

    public PlayCharacter(CharacterName name, ArrayList<Integer> student, ArrayList<Integer> island, String currentPlayer, Col studentColor) {
        this.name = name;
        this.student = student;
        this.island = island;
        this.currentPlayer = currentPlayer;
        this.studentColor = studentColor;
        super.type = ACTIONMESSAGETYPE.CHARACTER_PLAY;
    }

    public CharacterName getCharacterName() {
        return name;
    }

    public ArrayList<Integer> getFirst() {
        return student;
    }

    public ArrayList<Integer> getSecond() {
        return island;
    }

    public String getThird(){
        return currentPlayer;
    }

    public Col getStudentColor() {
        return studentColor;
    }

}
