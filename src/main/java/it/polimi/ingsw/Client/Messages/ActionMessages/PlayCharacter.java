package it.polimi.ingsw.Client.Messages.ActionMessages;

import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.util.ArrayList;

/**
 * Message type concerning the player's character choice
 */
public class PlayCharacter extends StandardActionMessage
{
    private CharacterName name;
    private ArrayList<Integer> student;
    private ArrayList<Integer> island;
    private String currentPlayer;
    private Col studentColor;

    /**
     * Class Constructor, this message is used to activate a character during the action phase
     * @param name the character we want to activate
     * @param student an arraylist of positions
     * @param island an arraylist of positions
     * @param currentPlayer the player that wants to activate the card
     * @param studentColor the color of the students the card has the effect on
     */
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
