package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Messages.ActionMessages.PlayCharacter;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

import java.util.ArrayList;

/**
 * Contains four parsing methods, one for all the input type of the characters (can be seen in the LightCharacterType class)
 * and the function to build the PlayCharacter message. Each method is correctly selected by overload.
 */
public class CharacterActivationParser
{
    private CharacterName name;
    private ArrayList<Integer> chosenIsland = new ArrayList<>();
    private ArrayList<Integer> chosenStudent = new ArrayList<>();
    private String CurrentPlayer = "";
    private Col chosenColor = null;

    /**
     * Parser for the NONE characterType
     * @param nickname the current player's name
     * @param name name of the character card
     */
    public CharacterActivationParser(String nickname, CharacterName name)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
    }

    /**
     * Parser for the INTEGER_1 characterType
     * @param nickname the player's name
     * @param name name of the character
     * @param island a list containing, in our case, the desired island ID (could contain of course more indexes if necessary)
     */
    public CharacterActivationParser(String nickname, CharacterName name, ArrayList<Integer> island)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        if(name == CharacterName.PRINCESS)
        {
            ArrayList<Integer> studentPositionOnCard = island;
            this.chosenStudent.addAll(studentPositionOnCard);
        }
        else
        {
            this.chosenIsland.addAll(island);
        }
    }

    /**
     * Parser for the INTEGER_2 characterType
     * @param nickname the player's name
     * @param name name the character
     * @param island first list of integers, normally associated with island IDs
     * @param student second list of integers, normally associated with student indexes in a certain collection
     */
    public CharacterActivationParser(String nickname, CharacterName name, ArrayList<Integer> island, ArrayList<Integer> student)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenIsland.addAll(island);
        this.chosenStudent.addAll(student);
    }

    /**
     * Parser for the COLOR characterType
     * @param nickname the player's name
     * @param name the name of the character
     * @param chosenColor the color chosen by the player
     */
    public CharacterActivationParser(String nickname, CharacterName name, Col chosenColor)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenColor = chosenColor;
    }

    /**
     * Builds the PlayCharacter message with the correct values
     * @return the PlayCharacter message
     */
    public PlayCharacter buildMessage()
    {
        return new PlayCharacter(name, chosenStudent, chosenIsland, CurrentPlayer, chosenColor);
    }
}
