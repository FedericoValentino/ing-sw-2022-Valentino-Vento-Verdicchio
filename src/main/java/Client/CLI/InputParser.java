package Client.CLI;

import Client.LightView;
import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.ReadyStatus;
import Client.ServerConnection;
import model.boards.token.CharacterName;
import model.boards.token.Col;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class InputParser
{
    private Scanner parser = new Scanner(System.in).useDelimiter("\\n");
    private String playerName;
    private ServerConnection socket;
    private PrinterCLI printer;
    private Boolean printView = false;

    public InputParser(ServerConnection socket, LightView lv)
    {
        this.playerName = socket.getNickname();
        this.socket = socket;
        this.printer = new PrinterCLI(lv);
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
        socket.sendMessage(new SerializedMessage(new PlayCharacter(CharacterName.valueOf(words[1]))));
    }

    public void CharacterActivationParser(String[] words)
    {
        socket.sendMessage(new SerializedMessage(new PlayCharacterEffect(CharacterName.valueOf(words[1]), valueOf(words[2]), valueOf(words[3]), playerName, Col.valueOf(words[4]))));
    }

    public void showView(String[] words)
    {
        switch(words[1])
        {
            case "island": //mostra isole
                if(words.length == 3)
                {
                    printer.showIsland(valueOf(words[2]));
                }
                break;
            case "islands":
                printer.showIsland(-1);
                break;
            case "school": //mostra scuole
                if(words.length == 3)
                    printer.showSchool(words[2], socket.getNickname());
                break;
            case "schools":
                printer.showSchool("-1", socket.getNickname());
                break;
            case "clouds":  //mostra nuvole
                printer.showCloud();
                break;
            case "assistants": //mostra carte assistente
                printer.showAssistantDeck(socket.getNickname());
                break;
            case "playedcards":
                printer.showPlayedCards();
                break;
            case "characters": //mostra personaggi attivi e non
                printer.showCharacters();
                break;
            case "player": //mostra status players
                if(words.length == 3)
                {
                    printer.showPlayer(words[2]);
                }
                break;
            case "players":
                printer.showPlayers();
        }
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
                resetScreen();
                break;
            case "refillfrom":
                socket.sendMessage(new SerializedMessage(new ChooseCloud(valueOf(words[1]))));
                resetScreen();
            case "draw":
                DrawParser(words);
                resetScreen();
                break;
            case "activate":
                CharacterActivationParser(words);
                resetScreen();
                break;
            case "move":
                MoveParser(words);
                resetScreen();
                break;
            case "play":
                CharacterParser(words);
                resetScreen();
                break;
            case "endturn":
                socket.sendMessage(new SerializedMessage(new EndTurn()));
                resetScreen();
            case "help":
                printer.showHelp();
                printView = false;
                break;
            case "show":
                showView(words);
                printView = false;
                break;
            case "ready":
                socket.sendMessage(new SerializedMessage(new ReadyStatus()));
                printer.cls();
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
    public void printGame()
    {
        printer.showCloud();
        System.out.println();
        printer.showIsland(-1);
        System.out.println();
        printer.showSchool("-1", socket.getNickname());
        System.out.println();
    }

    public void setPrintView(boolean t)
    {
        this.printView = t;
    }

    public Boolean getPrintView() {
        return printView;
    }

    private void resetScreen()
    {
        printer.cls();
        printGame();
    }
}
