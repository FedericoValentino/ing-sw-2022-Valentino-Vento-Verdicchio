package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CharacterActivationParser;
import it.polimi.ingsw.Client.LightView.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.ReadyStatus;
import it.polimi.ingsw.Client.ServerConnection;
import it.polimi.ingsw.Client.Messages.ActionMessages.*;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
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
        LightCharacterCard card = printer.getView().getCurrentCharacterDeck().getCard(Integer.parseInt(words[1]));
        CharacterActivationParser activation;
        String nickname = socket.getNickname();
        CharacterName cardName = card.getName();
        int input;
        switch(card.getType())
        {
            case NONE:

                activation = new CharacterActivationParser(nickname, cardName);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;
            case INTEGER_1:

                if(card.getName().equals(CharacterName.PRINCESS))
                {
                    printer.printStudent(card.getStudentList(), 2);
                    AnsiConsole.out().println("Choose a student to move to dining room");
                    input = Integer.parseInt(parser.nextLine());
                }
                else
                {
                    AnsiConsole.out().println("Choose the island");
                    input = Integer.parseInt(parser.nextLine());
                }
                ArrayList<Integer> inputArray = new ArrayList<>();
                inputArray.add(input);
                activation = new CharacterActivationParser(nickname, cardName, inputArray);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;

            case INTEGER_2:

                printer.printStudent(card.getStudentList(), 2);
                AnsiConsole.out().println("Choose the student to move");
                int input1 = Integer.parseInt(parser.nextLine());
                AnsiConsole.out().println("Choose the island");
                input = Integer.parseInt(parser.nextLine());
                ArrayList<Integer> inputArray1 = new ArrayList<>();
                inputArray1.add(input1);
                ArrayList<Integer> inputArray2 = new ArrayList<>();
                inputArray2.add(input);
                activation = new CharacterActivationParser(nickname, cardName, inputArray1, inputArray2);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;

            case COLOR:

                AnsiConsole.out().println("Choose a student color");
                Col color = Col.valueOf(parser.nextLine());
                activation = new CharacterActivationParser(nickname, cardName, color);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;
        }
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
                break;
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
                resetScreen();
                break;
            default:
                System.out.println("Unrecognized input");
                break;
        }
    }

    public void newMove()
    {
        String input = parser.nextLine();
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
        printer.printTurn();
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
    }
}
