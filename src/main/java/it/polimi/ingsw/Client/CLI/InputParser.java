package it.polimi.ingsw.Client.CLI;
//
import it.polimi.ingsw.Client.CLI.Printers.*;
import it.polimi.ingsw.Client.CharacterActivationParser;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.ReadyStatus;
import it.polimi.ingsw.Client.ServerConnection;
import it.polimi.ingsw.Client.Messages.ActionMessages.*;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
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
    private BoardPrinters boardPrinters;
    private SchoolPrinter schoolPrinter;
    private InfoPrinters infoPrinters;
    private CardPrinters cardPrinters;
    private Boolean printView = false;
    private CharacterActivationParser activation;

    public InputParser(ServerConnection socket, LightView lv)
    {
        this.playerName = socket.getNickname();
        this.socket = socket;
        this.boardPrinters = new BoardPrinters(lv);
        this.schoolPrinter = new SchoolPrinter(lv);
        this.infoPrinters = new InfoPrinters(lv);
        this.cardPrinters = new CardPrinters(lv);
    }

    public Scanner getParser()
    {
        return parser;
    }

    /**
     * Method DrawParser parses the words required to send a DRAW_CHOICE message
     * @param words is the current user input being parsed
     */
    public void DrawParser(String[] words)
    {
        if(words[1].equals("assistantcard"))
        {
            socket.sendMessage(new SerializedMessage(new DrawAssistantCard(valueOf(words[2]))));
        }
    }

    /**
     * Method MoveParser parses the words required to send a STUD_MOVE or a MN_MOVE message
     * @param words is the current user input being parsed
     */
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

    /**
     * Method CharacterParser parses the words required to send a CHARACTER_PLAY message
     * @param words is the current user input being parsed
     */
    public void CharacterParser(String[] words)
    {
        LightCharacterCard card = cardPrinters.getView().getCurrentCharacterDeck().getCard(Integer.parseInt(words[1]));

        String nickname = socket.getNickname();
        CharacterName cardName = card.getName();
        int input = 0;
        LightPlayer player = Utilities.findPlayerByName(cardPrinters.getView(), nickname);

        switch(card.getType())
        {
            case NONE:
                activation = new CharacterActivationParser(nickname, cardName);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;
            case INTEGER_1:
                ArrayList<Integer> inputArray = new ArrayList<>();
                if(card.getName().equals(CharacterName.PRINCESS))
                {
                    PrinterCLI.printStudent(card.getStudentList(), 2);
                    AnsiConsole.out().println("Choose a student to move to dining room");
                }
                else
                {
                    AnsiConsole.out().println("Choose the island");
                }
                input = Integer.parseInt(parser.nextLine());
                inputArray.add(input);
                activation = new CharacterActivationParser(nickname, cardName, inputArray);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;

            case INTEGER_2:
                ArrayList<Integer> inputArray1 = new ArrayList<>();
                ArrayList<Integer> inputArray2 = new ArrayList<>();

                if(card.getName().equals(CharacterName.MINSTREL))
                {
                    PrinterCLI.printStudent(player.getSchool().getEntrance(), 2);
                    AnsiConsole.out().println("You may choose up to 2 students, type -1 to stop selecting students");
                    while(input != -1)
                        AnsiConsole.out().println("Choose a student");
                    input = Integer.parseInt(parser.nextLine());
                    if(input >= 0 && input < player.getSchool().getEntrance().size())
                        inputArray1.add(input);
                    else if(input < -1)
                        AnsiConsole.out().println("Not a valid index");

                    for(int i = 0; i < inputArray1.size(); i++)
                    {
                        AnsiConsole.out().println("Choose a color:");
                        try
                        {
                            inputArray2.add(Col.valueOf(parser.nextLine()).ordinal());
                        }
                        catch(IllegalArgumentException e)
                        {
                            AnsiConsole.out().println("You didn't put a valid Color, try again");
                            i--;
                        }
                    }
                }
                else if(card.getName().equals(CharacterName.JESTER))
                {
                    PrinterCLI.printStudent(card.getStudentList(), 2);
                    PrinterCLI.printStudent(player.getSchool().getEntrance(), 2);
                    AnsiConsole.out().println("You may choose up to 3 students, type -1 to stop selecting students");
                    while(input != -1)
                        AnsiConsole.out().println("Chose a student");
                    input = Integer.parseInt(parser.nextLine());
                    if(input >= 0 && input < 6)
                        inputArray1.add(input);
                    else if(input < -1)
                        AnsiConsole.out().println("Not a valid index");

                    for(int i = 0; i < inputArray1.size(); i++)
                    {
                        AnsiConsole.out().println("Choose the students from the entrance");
                        try
                        {
                            inputArray2.add(Integer.parseInt(parser.nextLine()));
                        }
                        catch(NumberFormatException e)
                        {
                            AnsiConsole.out().println("You didn't put a valid index, try again");
                            i--;
                        }
                    }
                }
                else
                {
                    PrinterCLI.printStudent(card.getStudentList(), 2);
                    AnsiConsole.out().println("Choose the student to move");
                    int input1 = Integer.parseInt(parser.nextLine());
                    AnsiConsole.out().println("Choose the island");
                    input = Integer.parseInt(parser.nextLine());
                    inputArray1.add(input1);
                    inputArray2.add(input);
                }

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


    /**
     * Method showView prints to screen the user requested feature
     * @param words is the current user input being parsed
     */
    public void showView(String[] words)
    {
        switch(words[1])
        {
            case "island": //mostra isole
                if(words.length == 3)
                {
                    boardPrinters.showIsland(valueOf(words[2]));
                }
                break;
            case "islands":
                boardPrinters.showIsland(-1);
                break;
            case "school": //mostra scuole
                if(words.length == 3)
                    schoolPrinter.showSchool(words[2], socket.getNickname());
                break;
            case "schools":
                schoolPrinter.showSchool("-1", socket.getNickname());
                break;
            case "clouds":  //mostra nuvole
                boardPrinters.showCloud();
                break;
            case "assistants": //mostra carte assistente
                cardPrinters.showAssistantDeck(socket.getNickname());
                break;
            case "playedcards":
                cardPrinters.showPlayedCards();
                break;
            case "characters": //mostra personaggi attivi e non
                cardPrinters.showCharacters();
                break;
            case "player": //mostra status players
                if(words.length == 3)
                {
                    infoPrinters.showPlayer(words[2]);
                }
                break;
            case "players":
                infoPrinters.showPlayers();
        }
    }

    /**
     * Method parseString parses the first words from the input and either sends a message to the server or redirects the
     * parsing to the right parser
     * @param input
     */
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
                infoPrinters.showHelp();
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

    /**
     * Method newMove gets input from the user and starts the parsing process
     */
    public void newMove()
    {
        String input = parser.nextLine();
        parseString(input.toLowerCase(Locale.ROOT));
    }

    /**
     * Method printGame prints the main board information, clouds, schools and islands
     */
    public void printGame()
    {
        boardPrinters.showCloud();
        System.out.println();
        boardPrinters.showIsland(-1);
        System.out.println();
        schoolPrinter.showSchool("-1", socket.getNickname());
        System.out.println();
        infoPrinters.printTurn();
    }

    private void resetScreen()
    {
        PrinterCLI.cls();
    }
}
