package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Messages.ActionMessages.PlayCharacter;
import it.polimi.ingsw.Client.Messages.ActionMessages.PlayCharacterEffect;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;

public class CharacterActivationParser
{
    private CharacterName name;
    private int chosenIsland = 0;
    private int chosenStudent = 0;
    private String CurrentPlayer = "";
    private Col chosenColor = null;


    public CharacterActivationParser(String nickname, CharacterName name)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
    }
    public CharacterActivationParser(String nickname, CharacterName name, int island)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        if(name == CharacterName.PRINCESS)
        {
            //Instead of making a different method for princess I reuse this one since princess only takes a position and sends that student to the dining
            int studentPositionOnCard = island;
            this.chosenStudent = studentPositionOnCard;
        }
        else
        {
            this.chosenIsland = island;
        }
    }
    public CharacterActivationParser(String nickname, CharacterName name, int island, int student)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenIsland = island;
        this.chosenStudent = student;
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