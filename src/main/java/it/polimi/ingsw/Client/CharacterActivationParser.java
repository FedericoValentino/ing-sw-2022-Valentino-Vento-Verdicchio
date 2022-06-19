package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Messages.ActionMessages.PlayCharacter;
import it.polimi.ingsw.Client.Messages.ActionMessages.PlayCharacterEffect;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

import java.util.ArrayList;

public class CharacterActivationParser
{
    private CharacterName name;
    private ArrayList<Integer> chosenIsland = new ArrayList<>();
    private ArrayList<Integer> chosenStudent = new ArrayList<>();
    private String CurrentPlayer = "";
    private Col chosenColor = null;


    public CharacterActivationParser(String nickname, CharacterName name)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
    }
    public CharacterActivationParser(String nickname, CharacterName name, ArrayList<Integer> island)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        if(name == CharacterName.PRINCESS)
        {
            //Instead of making a different method for princess I reuse this one since princess only takes a position and sends that student to the dining
            ArrayList<Integer> studentPositionOnCard = island;
            this.chosenStudent.addAll(studentPositionOnCard);
        }
        else
        {
            this.chosenIsland.addAll(island);
        }
    }
    public CharacterActivationParser(String nickname, CharacterName name, ArrayList<Integer> island, ArrayList<Integer> student)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenIsland.addAll(island);
        this.chosenStudent.addAll(student);
    }
    public CharacterActivationParser(String nickname, CharacterName name, Col chosenColor)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenColor = chosenColor;
    }

    public PlayCharacter buildMessage()
    {
        return new PlayCharacter(name, chosenStudent, chosenIsland, CurrentPlayer, chosenColor);
    }
}
