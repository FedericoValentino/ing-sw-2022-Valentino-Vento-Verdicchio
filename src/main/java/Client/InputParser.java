package Client;

import Client.Messages.ActionMessages.*;
import model.boards.token.Col;

import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class InputParser
{
    private Scanner parser = new Scanner(System.in).useDelimiter("\\n");
    private String playerName;

    public InputParser(String name)
    {
        this.playerName = name;
    }

    public Scanner getParser()
    {
        return parser;
    }

    public StandardActionMessage DrawParser(String[] words)
    {
        if(words[1].equals("assistantcard"))
        {
            return new DrawAssistantCard(valueOf(words[2]));
        }
        return null;
    }

    public StandardActionMessage MoveParser(String[] words)
    {
        if(words[1].equals("student"))
        {
            int STUD_POS = valueOf(words[2]);
            if(words[3].equals("toisland"))
            {
                int ISLAND_POS = valueOf(words[4]);
                return new MoveStudent(STUD_POS, true, ISLAND_POS);
            }
            if(words[3].equals("toDining"))
            {
                return new MoveStudent(STUD_POS, false, 0);
            }
        }
        return null;
    }

    public StandardActionMessage CharacterParser(String[] words)
    {
        return new PlayCharacter(valueOf(words[1]));
    }

    public StandardActionMessage CharacterActivationParser(String[] words)
    {
        return new PlayCharacterEffect(valueOf(words[1]), valueOf(words[2]), valueOf(words[3]), playerName, Col.valueOf(words[4]));
    }

    public void printHelp()
    {

    }

    public StandardActionMessage parseString(String input)
    {
        String[] words = input.split("[\\s']");
        for(String word : words)
        {
            System.out.println(word);
        }
        switch (words[0])
        {
            case "refill":
                return new DrawFromPouch(valueOf(words[1]));

            case "draw":
                return DrawParser(words);

            case "activate":
                return CharacterActivationParser(words);

            case "move":
                return MoveParser(words);

            case "play":
                return CharacterParser(words);

            case "help":
                printHelp();
                return null;
            default:
                return null;

        }
    }


}
