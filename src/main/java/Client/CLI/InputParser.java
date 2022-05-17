package Client.CLI;

import Client.LightView;
import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.ServerConnection;
import model.boards.token.Col;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class InputParser
{
    private Scanner parser = new Scanner(System.in).useDelimiter("\\n");
    private String playerName;
    private ServerConnection socket;
    private LightView lightView;

    public InputParser(ServerConnection socket, LightView lv)
    {
        this.playerName = socket.getNickname();
        this.socket = socket;
        this.lightView = lv;
    }

    public Scanner getParser()
    {
        return parser;
    }

    public void DrawParser(String[] words)
    {
        if(words[1].equals("assistantcard"))
        {
            socket.sendMessage(new SerializedMessage(new DrawAssistantCard(valueOf(words[2]))));
        }
    }

    public void MoveParser(String[] words)
    {
        if(words[1].equals("student"))
        {
            int STUD_POS = valueOf(words[2]);
            if(words[3].equals("toisland"))
            {
                int ISLAND_POS = valueOf(words[4]);
                socket.sendMessage(new SerializedMessage(new MoveStudent(STUD_POS, true, ISLAND_POS)));
            }
            if(words[3].equals("todining"))
            {
                socket.sendMessage(new SerializedMessage(new MoveStudent(STUD_POS, false, 0)));
            }
        }
        if(words[1].equals("mothernature"))
        {
            int MN_POS = valueOf(words[2]);
            socket.sendMessage(new SerializedMessage(new MoveMN(MN_POS)));
        }
    }

    public void CharacterParser(String[] words)
    {
        socket.sendMessage(new SerializedMessage(new PlayCharacter(valueOf(words[1]))));
    }

    public void CharacterActivationParser(String[] words)
    {
        socket.sendMessage(new SerializedMessage(new PlayCharacterEffect(valueOf(words[1]), valueOf(words[2]), valueOf(words[3]), playerName, Col.valueOf(words[4]))));
    }

    public void showView(String[] words)
    {
        switch(words[1])
        {
            case "island": //mostra isole
                if(words.length == 3)
                {
                    lightView.showIsland(valueOf(words[2]));
                }
                else
                {
                    lightView.showIsland(-1);
                }
                break;
            case "school": //mostra scuole
                if(words.length == 3)
                {
                    lightView.showSchool(words[2]);
                }
                else
                {
                    lightView.showSchool("-1");
                }
                break;
            case "cloud":  //mostra nuvole
                if(words.length == 3)
                {
                    lightView.showCloud(valueOf(words[2]));
                }
                else
                {
                    lightView.showCloud(-1);
                }
                break;
            case "assistant": //mostra carte assistente
                if(words.length == 3)
                {
                    lightView.showAssistant(valueOf(words[2]));
                }
                else
                {
                    lightView.showAssistant(-1);
                }
                break;
            case "character": //mostra personaggi attivi e non
                if(words.length == 3)
                {
                    lightView.showCharacter(valueOf(words[2]));
                }
                else
                {
                    lightView.showCharacter(-1);
                }
                break;
            case "player": //mostra status players
                if(words.length == 3)
                {
                    lightView.showPlayer(valueOf(words[2]));
                }
                else
                {
                    lightView.showPlayer(-1);
                }
                break;
        }
    }

    public void printHelp()
    {

    }

    public void parseString(String input)
    {
        String[] words = input.split("[\\s']");
        for(String word : words)
        {
            System.out.println(word);
        }
        switch (words[0])
        {
            case "refill":
                socket.sendMessage(new SerializedMessage(new DrawFromPouch(valueOf(words[1]))));
                break;
            case "refillfrom":
                socket.sendMessage(new SerializedMessage(new ChooseCloud(valueOf(words[1]))));
            case "draw":
                DrawParser(words);
                break;
            case "activate":
                CharacterActivationParser(words);
                break;
            case "move":
                MoveParser(words);
                break;
            case "play":
                CharacterParser(words);
                break;
            case "endturn":
                socket.sendMessage(new SerializedMessage(new EndTurn()));
            case "help":
                printHelp();
                break;
            case "show":
                showView(words);
                break;
            default:
                System.out.println("Unrecognized input");
                break;
        }
    }

    public void newMove()
    {
        String input = parser.next();
        parseString(input.toLowerCase(Locale.ROOT));
    }

}
