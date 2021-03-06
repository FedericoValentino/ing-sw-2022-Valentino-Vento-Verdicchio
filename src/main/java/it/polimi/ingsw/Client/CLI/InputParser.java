package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Printers.*;
import it.polimi.ingsw.Client.CharacterActivationParser;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.Disconnect;
import it.polimi.ingsw.Client.Messages.SetupMessages.ReadyStatus;
import it.polimi.ingsw.Client.ServerConnection;
import it.polimi.ingsw.Client.Messages.ActionMessages.*;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import org.fusesource.jansi.AnsiConsole;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
//
/**
 * Class InputParser hosts all the methods necessary to parse the input obtained from the CLI in order to then either show
 * parts from the view or to send messages to the server
 */
public class InputParser
{
    private Scanner parser = new Scanner(System.in).useDelimiter("\\n");
    private ServerConnection socket;
    private BoardPrinters boardPrinters;
    private SchoolPrinter schoolPrinter;
    private InfoPrinters infoPrinters;
    private CardPrinters cardPrinters;

    public InputParser(ServerConnection socket, LightView lv)
    {
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
            try
            {
                int index = Integer.parseInt(words[2]);
                socket.sendMessage(new SerializedMessage(new DrawAssistantCard(index)));
            }
            catch(NumberFormatException e)
            {
                AnsiConsole.out().println("Error in parsing the string, maybe you didn't type the right index?");
            }
        }
    }

    /**
     * Method MoveParser parses the words required to send a STUD_MOVE or a MN_MOVE message
     * @param words is the current user input being parsed
     */
    public void MoveParser(String[] words)
    {
        try
        {
            if(words[1].equals("student"))
            {
                int STUD_POS = Integer.parseInt(words[2]);
                if(words[3].equals("toisland"))
                {
                    int ISLAND_POS = Integer.parseInt(words[4]);
                    socket.sendMessage(new SerializedMessage(new MoveStudent(STUD_POS, true, ISLAND_POS)));
                }
                if(words[3].equals("todining"))
                {
                    socket.sendMessage(new SerializedMessage(new MoveStudent(STUD_POS, false, 0)));
                }
            }
            if(words[1].equals("mothernature"))
            {
                int MN_POS = Integer.parseInt(words[2]);
                socket.sendMessage(new SerializedMessage(new MoveMN(MN_POS)));
            }
        }
        catch(NumberFormatException e)
        {
            AnsiConsole.out().println("Error in parsing the string, maybe you didn't type the right index?");
        }
    }

    /**
     * Method MinstrelParser Parses the input for the CharacterCard Minstrel
     * @param player is the current player
     * @param inputArray1 is the list of students in the entrance
     * @param inputArray2 is the list of colors ordinals in the dining room
     */
    public void MinstrelParser(LightPlayer player, ArrayList<Integer> inputArray1, ArrayList<Integer> inputArray2)
    {
        int input = 0;
        PrinterCLI.printStudent(player.getSchool().getEntrance(), 2);
        int[] diningRoom = player.getSchool().getDiningRoom().clone();

        AnsiConsole.out().println("You may choose up to 2 students, type -1 to stop selecting students");

        while(input != -1 && inputArray1.size() < 2)
        {
            AnsiConsole.out().println("Choose a student");
            input = integerParser();
            if(input >= 0 && input < player.getSchool().getEntrance().size()) {
                inputArray1.add(input);
            }
            else if(input < -1)
                AnsiConsole.out().println("Not a valid index");
        }

        for(int i = 0; i < inputArray1.size(); i++)
        {
            AnsiConsole.out().println("Choose a color:");
            ArrayList <Col> colors = infoPrinters.printAvailableColors(diningRoom);
            try
            {
                Col colorInput = studentColorParser();
                if(colors.contains(colorInput))
                {
                    diningRoom[colorInput.ordinal()]--;
                    inputArray2.add(colorInput.ordinal());
                }
                else
                {
                    AnsiConsole.out().println("The color you entered isn't available, try again");
                }
            }
            catch(IllegalArgumentException e)
            {
                AnsiConsole.out().println("You didn't put a valid Color, try again");
                i--;
            }
        }
    }
//
    /**
     * Method JesterParser parses the input for the CharacterCard Jester
     * @param player is the current player
     * @param inputArray1 is the list of students indexes on the card
     * @param inputArray2 is the list of students indexes in the entrance
     */
    public void JesterParser(LightCharacterCard card, LightPlayer player, ArrayList<Integer> inputArray1, ArrayList<Integer> inputArray2)
    {
        int inputStudentOnCardIndex = 0;
        int inputStudentInEntrance;
        PrinterCLI.printStudent(card.getStudentList(), 2);
        PrinterCLI.printStudent(player.getSchool().getEntrance(), 2);
        AnsiConsole.out().println("You may choose up to 3 students from the card, type -1 to stop selecting students");

        while(inputStudentOnCardIndex != -1 && inputArray2.size() < 3)
        {
            AnsiConsole.out().println("Chose a student");
            inputStudentOnCardIndex = integerParser();
            if (inputStudentOnCardIndex >= 0 && inputStudentOnCardIndex < 6 && !inputArray2.contains(inputStudentOnCardIndex))
            {
                inputArray2.add(inputStudentOnCardIndex);
            }
            else if (inputStudentOnCardIndex < -1)
                AnsiConsole.out().println("Not a valid index");
        }

        for(int i = 0; i < inputArray2.size(); i++)
        {
            AnsiConsole.out().println("Choose the students from the entrance");
            inputStudentInEntrance = integerParser();
            if (inputStudentInEntrance >= 0 && inputStudentInEntrance < player.getSchool().getEntrance().size())
            {
                inputArray1.add(inputStudentInEntrance);
            }
            else
            {
                AnsiConsole.out().println("Your index was invalid, type again");
                i--;
            }
        }
    }

    /**
     * Method CharacterParser parses the words required to send a CHARACTER_PLAY message
     * @param words is the current user input being parsed
     */
    public void CharacterParser(String[] words)
    {
        LightCharacterCard card = cardPrinters.getView().getCurrentCharacterDeck().getCard(Integer.parseInt(words[1]));
        CharacterActivationParser activation;
        String nickname = socket.getNickname();
        CharacterName cardName = card.getName();
        int input;
        LightPlayer player = Utilities.findPlayerByName(cardPrinters.getView(), nickname);

        switch(card.getType())
        {
            case NONE:
                activation = new CharacterActivationParser(nickname, cardName);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;
            case INTEGER_1:
                ArrayList<Integer> inputArray = new ArrayList<>();
                if(cardName.equals(CharacterName.PRINCESS))
                {
                    PrinterCLI.printStudent(card.getStudentList(), 2);
                    AnsiConsole.out().println("Choose a student to move to dining room");
                    input = integerParser();

                    while (input < 0 || input > 4)
                    {
                        AnsiConsole.out().println("Wrong input insert the index again");
                        input = integerParser();
                    }
                        inputArray.add(input);
                }
                else
                {
                    AnsiConsole.out().println("Choose the island");
                    input = integerParser();
                    if(input >= 0 && input < cardPrinters.getView().getCurrentIslands().getIslands().size())
                    {
                        inputArray.add(input);
                    }
                }
                if(cardName.equals(CharacterName.GRANDMA_HERBS) && (cardPrinters.getView().getCurrentIslands().getIslands().get(input).isNoEntry() || card.getNoEntry() == 0))
                {
                    AnsiConsole.out().println("Island already occupied by noEntry or no more noEntry remaining on the card");
                }
                else if(cardName.equals(CharacterName.PRINCESS) && Utilities.findPlayerByName(cardPrinters.getView(), nickname).getSchool().getDiningRoom()[card.getStudentList().get(input).getColor().ordinal()] == 10)
                {
                    AnsiConsole.out().println("That table row is full, choose another student");
                }
                else
                {
                    activation = new CharacterActivationParser(nickname, cardName, inputArray);
                    socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                }
                break;

            case INTEGER_2:
                ArrayList<Integer> inputArray1 = new ArrayList<>();
                ArrayList<Integer> inputArray2 = new ArrayList<>();
                if(cardName.equals(CharacterName.MINSTREL))
                {
                    MinstrelParser(player, inputArray2, inputArray1);
                }
                else if(cardName.equals(CharacterName.JESTER))
                {
                    JesterParser(card, player, inputArray1, inputArray2);
                }
                else
                {
                    PrinterCLI.printStudent(card.getStudentList(), 2);
                    AnsiConsole.out().println("Choose the student to move");
                    int input1 = integerParser();
                    AnsiConsole.out().println("Choose the island");
                    input = integerParser();
                    inputArray1.add(input1);
                    inputArray2.add(input);
                }

                activation = new CharacterActivationParser(nickname, cardName, inputArray1, inputArray2);
                socket.sendMessage(new SerializedMessage(activation.buildMessage()));
                break;

            case COLOR:

                AnsiConsole.out().println("Choose a student color");
                Col color = studentColorParser();
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
            case "island":
                if(words.length == 3)
                {
                    try
                    {
                        boardPrinters.showIsland(Integer.parseInt(words[2]));
                    }
                    catch(NumberFormatException e)
                    {
                        AnsiConsole.out().println("Invalid Index");
                    }
                }
                break;
            case "islands":
                boardPrinters.showIsland(-1);
                break;
            case "school":
                if(words.length == 3)
                    schoolPrinter.showSchool(words[2], socket.getNickname());
                break;
            case "schools":
                schoolPrinter.showSchool("-1", socket.getNickname());
                break;
            case "clouds":
                boardPrinters.showCloud();
                break;
            case "assistants":
                cardPrinters.showAssistantDeck(socket.getNickname());
                break;
            case "playedcards":
                cardPrinters.showPlayedCards();
                break;
            case "characters":
                cardPrinters.showCharacters();
                break;
            case "player":
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
     * @param input is the string to parse
     */
    public void parseString(String input)
    {
        String[] words = input.split("[\\s']");
        switch (words[0])
        {
            case "refill":
                try
                {
                    int index = Integer.parseInt(words[1]);
                    socket.sendMessage(new SerializedMessage(new DrawFromPouch(index)));
                    resetScreen();
                }
                catch(NumberFormatException e)
                {
                    AnsiConsole.out().println("Invalid Index");
                }


                break;
            case "refillfrom":
                try
                {
                    int index = Integer.parseInt(words[1]);
                    socket.sendMessage(new SerializedMessage(new ChooseCloud(index)));
                    resetScreen();
                }
                catch(NumberFormatException e)
                {
                    AnsiConsole.out().println("Invalid Index");
                }
                break;
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
                break;
            case "endturn":
                socket.sendMessage(new SerializedMessage(new EndTurn()));
                resetScreen();
                break;
            case "help":
                infoPrinters.showHelp();
                break;
            case "show":
                showView(words);
                break;
            case "ready":
                socket.sendMessage(new SerializedMessage(new ReadyStatus()));
                resetScreen();
                break;
            case "exit":
                socket.sendMessage(new SerializedMessage(new Disconnect()));
                System.exit(0);
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
        infoPrinters.printEconomy(socket.getNickname());
        System.out.println();
        infoPrinters.printTurn();
    }

    public Integer integerParser()
    {
        Integer input = null;
        while(input == null)
        {
            try
            {
                input = Integer.parseInt(parser.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("Index not recognized, maybe another character slipped in there");
            }
        }
        return input;
    }

    public Col studentColorParser()
    {
        Col input = null;
        while(input == null)
        {
            try
            {
                input = Col.valueOf(parser.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("Color not recognized, maybe another character slipped in there");
            }
        }
        return input;
    }
    private void resetScreen()
    {
        PrinterCLI.cls();
    }
}
