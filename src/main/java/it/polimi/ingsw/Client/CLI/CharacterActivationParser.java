package it.polimi.ingsw.Client.CLI;

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
            this.chosenStudent = island;
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
    public CharacterActivationParser(String nickname, CharacterName name, int island, Col chosenColor)
    {
        this.CurrentPlayer = nickname;
        this.name = name;
        this.chosenIsland = island;
        this.chosenColor = chosenColor;
    }

    public PlayCharacterEffect buildMessage()
    {
        return new PlayCharacterEffect(name, chosenStudent, chosenIsland, CurrentPlayer, chosenColor);
    }
}
