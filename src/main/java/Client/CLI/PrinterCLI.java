package Client.CLI;

import Client.LightView;
import model.Player;
import model.Team;
import model.boards.Island;
import model.boards.token.CharacterName;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.cards.CharacterCard;
import model.cards.GrandmaHerbs;
import model.cards.Priest;
import model.cards.Princess;


import java.util.ArrayList;
import java.util.Arrays;

public class PrinterCLI
{
    //colore testo
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //colore background
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    private LightView view;


    public PrinterCLI(LightView lv)
    {
        this.view = lv;
    }

    public String addZero(int X)
    {
        if(X < 10)
            return "0" + X;
        else
            return String.valueOf(X);
    }

    public void cls()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String nameTrimmer(String name)
    {
        if(name.length() > 10)
            name = name.substring(0,10);
        else
        {
            for(int i=0; i< 10 - name.length(); i++)
                name += " ";
        }
        return name;
    }


    public String convertTo3Char(ColTow towerColor)
    {
        if(towerColor != null)
        {
            switch(towerColor)
            {
                case GREY:
                    return "GRY";

                case WHITE:
                    return "WHT";

                case BLACK:
                    return "BLK";

                default:
                    return "   ";
            }
        }
        return "   ";
    }

    public String printMN(int id)
    {
        if(view.getCurrentIslands().getIslands().get(id).getMotherNature())
            return "X";
        else
            return "O";
    }

    public String[] printIsland(String[] islands, int id)
    {
        int StudentNumber;
        Island island = view.getCurrentIslands().getIslands().get(id);
        islands[0] += "____________________  ";
        islands[1] += "|   Island n: " + addZero(island.getIslandId()) +"   |  ";
        islands[2] += "|       MN: " + printMN(island.getIslandId()) + "      |  ";
        StudentNumber = (int)view.getCurrentIslands().getIslands().get(island.getIslandId()).currentStudents.stream().filter(Student -> Student.getColor() == Col.GREEN).count();
        islands[3] += "| " + ANSI_GREEN + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |  ";
        StudentNumber = (int)view.getCurrentIslands().getIslands().get(island.getIslandId()).currentStudents.stream().filter(Student -> Student.getColor() == Col.RED).count();
        islands[4] += "| " + ANSI_RED + ":D " + ANSI_RESET + addZero(StudentNumber) + "    " + convertTo3Char(view.getCurrentIslands().getIslands().get(island.getIslandId()).getOwnership())+ "     |  " ;
        StudentNumber = (int)view.getCurrentIslands().getIslands().get(island.getIslandId()).currentStudents.stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
        islands[5] += "| " + ANSI_YELLOW + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |  ";
        StudentNumber = (int)view.getCurrentIslands().getIslands().get(island.getIslandId()).currentStudents.stream().filter(Student -> Student.getColor() == Col.PINK).count();
        islands[6] += "| " + ANSI_PURPLE + ":D " + ANSI_RESET + addZero(StudentNumber) + "   TOW " + addZero(view.getCurrentIslands().getIslands().get(island.getIslandId()).getTowerNumber()) + "   |  ";
        StudentNumber = (int)view.getCurrentIslands().getIslands().get(island.getIslandId()).currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count();
        islands[7] += "| " + ANSI_BLUE + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |  ";
        islands[8] += "|__________________|  ";

        return islands;
    }

    public void showIsland(int id)
    {
        String[] islands = new String[9];
        Arrays.fill(islands, "");

        if(id >= 0)
        {
            islands = printIsland(islands, id);
            for(int k = 0; k < 9; k++)
            {
                System.out.println(islands[k]);
            }
        }
        else
        {

            int totalIslandsPrinted = 1;
            for(int i = 0; i < 2; i++)
            {
                Arrays.fill(islands, "");

                for(int j = i * 6; j < view.getCurrentIslands().getIslands().size(); j++)
                {
                    islands = printIsland(islands, j);
                    totalIslandsPrinted++;
                    if(totalIslandsPrinted > view.getCurrentIslands().getIslands().size()/2)
                    {
                        break;
                    }
                }

                for(int k = 0; k < 9; k++)
                {
                    System.out.println(islands[k]);
                }
                totalIslandsPrinted = 1;
            }
        }
    }

    private String printStudent(ArrayList<Student> students, int spaces)
    {
        String output = "";
        for(Student s: students)
        {
            for(int i = 0; i < spaces; i++)
            {
                output += " ";
            }
            switch(s.getColor())
            {
                case GREEN:
                    output += ANSI_GREEN + "X";
                    break;
                case RED:
                    output += ANSI_RED + "X";
                    break;
                case YELLOW:
                    output += ANSI_YELLOW + "X";
                    break;
                case PINK:
                    output += ANSI_PURPLE + "X";
                    break;
                case BLUE:
                    output += ANSI_BLUE + "X";
                    break;
            }
        }
        return output + ANSI_RESET;
    }


    private String printEntrance(String nome)
    {
        String output = "";
        int entranceCount = 0;
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    entranceCount = player.getSchool().getEntrance().size();
                    output = printStudent(player.getSchool().getEntrance(), 2);
                }
            }
        }
        for(int i = 0; i < 9 - entranceCount; i++)
        {
            output += ANSI_RESET + "  O";
        }
        return output + ANSI_RESET;
    }

    private String printDinnerTable(String nome, int dinnerPosition)
    {
        String output = "";
        String color = "";
        switch (dinnerPosition)
        {
            case 0:
                color += ANSI_GREEN;
                break;
            case 1:
                color += ANSI_RED;
                break;
            case 2:
                color += ANSI_YELLOW;
                break;
            case 3:
                color += ANSI_PURPLE;
                break;
            case 4:
                color += ANSI_BLUE;
                break;
        }
        output += color;
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    for(int i = 0; i < 10; i++)
                    {
                        if(i < player.getSchool().getDiningRoom()[dinnerPosition])
                        {
                            output += " X";
                        }
                        else if(i == 2 || i == 5 || i == 8)
                        {
                            output += ANSI_WHITE + " C" + color;
                        }
                        else
                        {
                            output += ANSI_RESET + " O" + color;
                        }
                    }
                }
            }
        }
        return  output + ANSI_RESET;
    }

    private String printHasProf(String nome, int tablePosition)
    {
        String output = "";
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    if(player.getSchool().getProfessorTable()[tablePosition])
                        output = ANSI_GREEN_BACKGROUND + "P" + ANSI_RESET;
                    else
                        output = ANSI_RED_BACKGROUND + "O" + ANSI_RESET;
                }
            }
        }
        return output;
    }

    public String[] printSchool(String[] schools, Player player, String currentPlayer)
    {
        String name = player.getNome();
        String nameTrimmed = nameTrimmer(name);
        String currentPlayerTrimmed = nameTrimmer(currentPlayer);

        if(!currentPlayer.equals(name))
            schools[0] += "This is " + ANSI_CYAN + "[" + nameTrimmed + "]" + ANSI_RESET + "'s school                       ";
        else
        {
            schools[0] += ANSI_GREEN + "[" + currentPlayerTrimmed + "]" + ANSI_RESET + ", this is " + ANSI_GREEN + "Your school               " + ANSI_RESET;
        }
        schools[1] += "____________________________________________\t";
        schools[2] += "|     0  1  2  3  4  5  6  7  8            |\t";
        schools[3] += "| E:" + printEntrance(name) + ANSI_RESET + "            |\t";
        schools[4] += "|__________________________________________|\t";
        schools[5] += "|                                          |\t";
        schools[6] += ("| G:" + printDinnerTable(name, 0) + "  || " + printHasProf(name, 0) + "             |\t");
        schools[7]+= ("| R:" + printDinnerTable(name, 1) + "  || " + printHasProf(name, 1) + "             |\t");
        schools[8] += ("| Y:" + printDinnerTable(name, 2) + "  || " + printHasProf(name, 2) + "    TOW " + addZero(player.getSchool().getTowerCount()) + "   |\t");
        schools[9] += ("| P:" + printDinnerTable(name, 3) + "  || " + printHasProf(name, 3) + "             |\t");
        schools[10] += ("| B:" + printDinnerTable(name, 4) + "  || " + printHasProf(name, 4) + "             |\t");
        schools[11] += ("|__________________________________________|\t");

        return schools;
    }


    public void showSchool(String name, String currentPlayer) {
        String[] schools = new String[12];
        Arrays.fill(schools, "");

        int totalSchools = 0;
        int playerNotFoundCounter = 0;

        for (Team team : view.getCurrentTeams()) {
            for (Player player : team.getPlayers()) {
                if (name.equals("-1"))
                {
                    totalSchools++;
                    schools = printSchool(schools, player, currentPlayer);
                    if (totalSchools == 2) {
                        for (int i = 0; i < 12; i++) {
                            System.out.println(schools[i]);
                            totalSchools = 0;
                        }
                        System.out.println();
                        Arrays.fill(schools, "");
                    }
                }
                else if (player.getNome().equals(name))
                {
                    totalSchools++;
                    schools = printSchool(schools, player, currentPlayer);
                    for (int i = 0; i < 12; i++) {
                        System.out.println(schools[i]);
                        totalSchools = 0;
                    }
                }
                else if(!player.getNome().equals((name)))
                    {
                        playerNotFoundCounter += 1;
                    }
            }
        }
        System.out.println();
        if(totalSchools > 0)
        {
            for (int i = 0; i < 12; i++) {
                System.out.println(schools[i]);
                totalSchools = 0;
            }
        }

        if(playerNotFoundCounter == view.getCurrentTeams().size() * view.getCurrentTeams().get(0).getPlayers().size())
            System.out.println( ANSI_RED_BACKGROUND + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);

        System.out.println();
    }

    private String printStudentOnCloud(ArrayList<Student> students)
    {
        String output = "";
        int cloudCount = students.size();
        output = printStudent(students, 1);
        for(int i = 0; i < 4 - cloudCount; i++)
        {
            output += ANSI_RESET + " O";
        }
        return output;
    }

    public void showCloud()
    {
        String[] clouds = new String[5];
        Arrays.fill(clouds, "");

        for(int i = 0; i < view.getCurrentClouds().length; i++)
        {
            clouds[0] += " Cloud " + i + "\t ";
            clouds[1] += " _________ \t ";
            clouds[2] += "/         \\\t ";
            clouds[3] += "|" + printStudentOnCloud(view.getCurrentClouds()[i].getStudents()) +" |\t ";
            clouds[4] += "\\_________/\t ";
        }

        for (int k = 0; k < 5; k++)
            System.out.println(clouds[k]);
    }

    public void showAssistantDeck(String nickname)
    {
        String[] deck = new String[7];
        Arrays.fill(deck, "");

        Player p = null;
        for (Team team : view.getCurrentTeams())
        {
            for (Player player : team.getPlayers())
            {
                if(player.getNome().equals(nickname))
                {
                    p = player;
                }
            }
        }
        System.out.println(ANSI_GREEN + "[" + p.getAssistantDeck().getWizard().name() + "]" + ANSI_RESET + "'s deck");
        for(int i = 0; i < p.getAssistantDeck().getDeck().size(); i++)
        {
            deck[0] += "__________ ";
            deck[1] += "|   " + addZero(i) + "   | ";
            deck[2] += "|        | ";
            deck[3] += "|Value:" + addZero(p.getAssistantDeck().getDeck().get(i).getValue()) + "| ";
            deck[4] += "|        | ";
            deck[5] += "|Moves:" + addZero(p.getAssistantDeck().getDeck().get(i).getMovement()) + "| ";
            deck[6] += "|________| ";
        }

        for(String output : deck)
        {
            System.out.println(output);
        }
    }

    public void showPlayedCards()
    {
        String[] currentlyPlayed = new String[8];
        String[] lastPlayed = new String[8];
        Arrays.fill(currentlyPlayed, "");
        Arrays.fill(lastPlayed, "");
        int currentCardCounter = 0;
        int lastCardCounter = 0;


        for(Team t: view.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                String name = p.getNome();
                String nameTrimmed = nameTrimmer(name);
                if(p.getCurrentAssistantCard() == null)
                {
                    currentCardCounter += 1;
                }
                else
                {
                    currentlyPlayed[0] += nameTrimmed + "      ";
                    currentlyPlayed[1] += "__________     ";
                    currentlyPlayed[2] += "|   " + ANSI_GREEN + "CP" + ANSI_RESET + "   |     ";
                    currentlyPlayed[3] += "|        |     ";
                    currentlyPlayed[4] += "|Value:" + addZero(p.getCurrentAssistantCard().getValue()) + "|     ";
                    currentlyPlayed[5] += "|        |     ";
                    currentlyPlayed[6] += "|Moves:" + addZero(p.getCurrentAssistantCard().getMovement()) + "|     ";
                    currentlyPlayed[7] += "|________|     ";
                }
                if(p.getLastPlayedCard() == null)
                {
                    lastCardCounter += 1;
                }
                else
                {
                    lastPlayed[0] += nameTrimmed + "      ";
                    lastPlayed[1] += "__________     ";
                    lastPlayed[2] += "|   " + ANSI_RED + "LP" + ANSI_RESET + "   |     ";
                    lastPlayed[3] += "|        |     ";
                    lastPlayed[4] += "|Value:" + addZero(p.getLastPlayedCard().getValue()) + "|     ";
                    lastPlayed[5] += "|        |     ";
                    lastPlayed[6] += "|Moves:" + addZero(p.getLastPlayedCard().getMovement()) + "|     ";
                    lastPlayed[7] += "|________|     ";
                }
            }
        }

        System.out.println( ANSI_GREEN + "Currently played" + ANSI_RESET + " assistant cards");

        if(currentCardCounter == view.getCurrentTeams().size() * view.getCurrentTeams().get(0).getPlayers().size())
            System.out.println("Ops! Nothing to show yet!");
        else
        {
            for (int i = 0; i < 8; i++) {
                System.out.println(currentlyPlayed[i]);
            }
        }
        System.out.println();

        System.out.println(ANSI_RED+ "Last played" + ANSI_RESET + " assistant cards");

        if(lastCardCounter == view.getCurrentTeams().size() * view.getCurrentTeams().get(0).getPlayers().size())
            System.out.println("Ops! Nothing to show yet!");
        else
        {
            for (int i = 0; i < 8; i++) {
                System.out.println(lastPlayed[i]);
            }
            if (lastCardCounter == view.getCurrentTeams().size() * view.getCurrentTeams().get(0).getPlayers().size())
                System.out.println("Ops! Nothing to show yet!");
        }

        System.out.println();
    }


    private String[] printCharacter(CharacterCard card, String[] character, int index)
    {

        String[] description = new String[7];
        description = card.description();

        if(card instanceof Princess)
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getCharacterName() + ANSI_YELLOW + "        |" ;
            character[2] += "|       " + ANSI_RESET +"ID: " + addZero(index) + ANSI_YELLOW + "     |";
            character[3] += "|                  |";
            character[4] += "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |";
            character[5] += "|                  |";
            character[6] += "|  " + ANSI_RESET + printStudent(((Princess) card).getStudents(), 2);
            for(int i = 0; i < 4 - ((Princess) card).getStudents().size(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |";
            character[7] += "|__________________|\n" + ANSI_RESET;
        }
        else if(card instanceof Priest)
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getCharacterName() + ANSI_YELLOW + "          |";
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |";
            character[3] += "|                  |";
            character[4] += "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |";
            character[5] += "|                  |";
            character[6] += "|  " + ANSI_RESET + printStudent(((Priest) card).getStudents(), 2);
            for(int i = 0; i < 4 - ((Priest) card).getStudents().size(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |";
            character[7] += "|__________________|\n" + ANSI_RESET;
        }
        else if (card instanceof GrandmaHerbs)
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getCharacterName() + ANSI_YELLOW + "   |";
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |";
            character[3] += "|                  |";
            character[4] += "| " + ANSI_RESET + "Current Cost: "+ addZero(card.getCurrentCost()) + ANSI_YELLOW + " |";
            character[5] += "|                  |";
            character[6] += "|  " + ANSI_RESET;
            for(int i = 0; i < ((GrandmaHerbs) card).getNoEntry(); i++)
            {
                character[6] += ANSI_RED_BACKGROUND + "  !" + ANSI_RESET;
            }
            for(int i = 0; i < 4 - ((GrandmaHerbs) card).getNoEntry(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |";
            character[7] += "|__________________|\n" + ANSI_RESET;
        }
        else
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getCharacterName() + ANSI_YELLOW;
            for(int i = 0; i < "TRUFFLE_HUNTER".length() - card.getCharacterName().toString().length(); i++)
            {
                character[1] += " ";
            }
            character[1] += "  |";
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |";
            character[3] += "|                  |";
            character[4] += "| " + ANSI_RESET + "Current Cost: "+ addZero(card.getCurrentCost()) + ANSI_YELLOW + " |";
            character[5] += "|                  |";
            character[6] += "|                  |";
            character[7] += "|__________________|\n" + ANSI_RESET;
        }
        return character;
    }

    public void showCharacters()
    {
        String[] inactiveCharacter = new String[8];
        Arrays.fill(inactiveCharacter, "");
        System.out.println( ANSI_GREEN + "Inactive Cards:" + ANSI_RESET);
        for(int i = 0; i < view.getCurrentCharacterDeck().getDeck().size(); i++)
        {
            inactiveCharacter = printCharacter(view.getCurrentCharacterDeck().getCard(i), inactiveCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                System.out.println(inactiveCharacter[j]);
            }
            Arrays.fill(inactiveCharacter, "");
        }

        Arrays.fill(inactiveCharacter, "");
        System.out.println(ANSI_RED + "Active Card" + ANSI_RESET);
        for(int i = 0; i < view.getCurrentActiveCharacterCard().size(); i++)
        {
            inactiveCharacter = printCharacter(view.getCurrentActiveCharacterCard().get(i), inactiveCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                System.out.println(inactiveCharacter[j]);
            }
        }
    }

    public void showPlayer(String name)
    {
        Player player = getPlayerByName(name);
        Team team = getPlayerTeam(name);
        System.out.println(ANSI_CYAN +  name + ANSI_RESET + "'s information:");
        System.out.println(team.getColor() + "Team");
        if(team.getControlledIslands() != 0) {
            if (team.getControlledIslands() == 1)
                System.out.println(ANSI_CYAN + name + ANSI_RESET + " and their team control " + team.getControlledIslands() + ANSI_GREEN + " island" + ANSI_RESET + ", (" + ANSI_YELLOW + controlledIslands(team) + ANSI_RESET + ")");
            else
                System.out.println(ANSI_CYAN + name + ANSI_RESET + " and their team control " + team.getControlledIslands() + ANSI_GREEN + " islands" + ANSI_RESET + ", (" + ANSI_YELLOW + controlledIslands(team) + ANSI_RESET + ")");
        }
        else
            System.out.println(ANSI_CYAN + name + ANSI_RESET + " and their team control no " + ANSI_GREEN + "islands" + ANSI_RESET + " yet");

        if(team.getControlledProfessors() != null)
            System.out.println(ANSI_CYAN + name + ANSI_RESET + " and their team control these " + ANSI_RED + "professors" + ANSI_RESET + ":" + team.getControlledProfessors());
        else
            System.out.println(ANSI_CYAN + name + ANSI_RESET + " and" + " their team control no " + ANSI_RED + "professors" + ANSI_RESET + " at the moment");

        if(player.isTowerOwner())
        {
            if(player.getSchool().getTowerCount() < 3) {
                if (player.getSchool().getTowerCount() == 1)
                    System.out.println(ANSI_CYAN + name + ANSI_RESET + "and their team have" + player.getSchool().getTowerCount() + ANSI_BLUE + "tower" + ANSI_RESET + " left. They are getting close!");
                else
                    System.out.println(ANSI_CYAN + name + ANSI_RESET + "and their team have" + player.getSchool().getTowerCount() + ANSI_BLUE + "towers" + ANSI_RESET + " left. They are getting close!");
            }
            else{
                if(player.getSchool().getTowerCount() == 1)
                    System.out.println(ANSI_CYAN + ANSI_RESET + name + "and their team have" + player.getSchool().getTowerCount() + ANSI_BLUE + "tower" + ANSI_RESET + " left");
                else
                    System.out.println(ANSI_CYAN + ANSI_RESET + name + "and their team have" + player.getSchool().getTowerCount() + ANSI_BLUE + "towers" + ANSI_RESET + " left");
            }

        }
        if(player.getMaxMotherMovement() == 0)
            System.out.println(ANSI_CYAN + name + ANSI_RESET + " hasn't played an assistant card yet!");
        else
            System.out.println(ANSI_CYAN + name + ANSI_RESET + " can move " + ANSI_GREEN + "Mother Nature" + ANSI_RESET + " up to " + player.getMaxMotherMovement() + " spaces");
        //Team color, Coins, isole controllate, professori controllati, torri rimanenti(solo se towerHolder), MaxMotherMovement
    }


    public void showPlayers()
    {
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                showPlayer(player.getNome());
            }
        }
    }

    public void showHelp()
    {
        System.out.println("Welcome to Eryantis! The game automatically displays on your screen the " + ANSI_GREEN + "Game Board" + ANSI_RESET + ", which comprises:");
        System.out.println("> The " + ANSI_CYAN + "Clouds" + ANSI_RESET + " and the students they contain");
        System.out.println("> The " + ANSI_YELLOW + "Island" + ANSI_RESET + " tiles, on which you can see the island id, the contained students, the number of towers, the team that eventually controls the island and whether mother nature is currently present");
        System.out.println("  If Mother nature is on the Island, you will se an X next to the MN field; you will see an O in the other case");
        System.out.println("> The players' " + ANSI_RED + "Schools" + ANSI_RESET + ", on which you can see the students in the entrance and in the dining room, the controlled professors and the remaining towers");
        System.out.println();
        System.out.println("COMMANDS");
        System.out.println("To give a command simply type the command you want to give and then press the [Enter] key on your keyboard.");
        System.out.println("Here follows a list of the accepted commands.");
        System.out.println();
        System.out.println(ANSI_GREEN + "Action" + ANSI_RESET + " commands");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " move");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " student");
        System.out.println(ANSI_PURPLE + "     >>" + ANSI_RESET + " toisland " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t moves a student from your entrance to the desired island");
        System.out.println(ANSI_PURPLE + "     >>" + ANSI_RESET + " todining\t moves a student from your entrance to your dining room");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " mothernature " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t where X is an integer number, moves mother nature of the desired number of spaces");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " draw assistantcard " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t lets you play the assistant card at the X index in your deck, where X is an integer number");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " refill " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t lets you refill the selected cloud (must be empty) of students: X is an integer and identifies the cloud to refill");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " refillfrom " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t use the cloud specified by the X integer to refill your entrance of students");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " play " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t choose the character card specified by the index (the X integer) and pay its cost");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " activate\t\t activate the effect of the character card you have previously played");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + " endturn\t\t write this to end your turn");
        System.out.println();
        System.out.println(ANSI_CYAN + "Info" + ANSI_RESET + " commands");
        System.out.println(ANSI_YELLOW + ">" + ANSI_RESET + "show");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " island " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t displays the selected island");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " islands\t\t displays all islands");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " school " + ANSI_CYAN + "[playerName]" + ANSI_RESET + "\t displays the specified player's school");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " schools\t\t displays all schools");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " clouds\t\t displays all clouds");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " assistants\t\t displays your assistant deck");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " playedcards\t\t displays the assistant cards currently active and the last assistant cards played");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " characters\t\t shows active and inactive character cards");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " player " + ANSI_CYAN + "[playerName]" + ANSI_RESET + "\t displays the desired player's game info");
        System.out.println(ANSI_RED + "  >>" + ANSI_RESET + " players\t\t shows all players' game info");
    }


    private ArrayList<String> controlledIslands(Team team)
    {
        ArrayList<String> islands = new ArrayList<>();
        for(Island island : view.getCurrentIslands().getIslands())
        {
            if(island.getOwnership().equals(team.getColor()))
                islands.add(addZero(island.getIslandId()));
        }
        return islands;
    }

    private Player getPlayerByName(String name)
    {
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(name))
                {
                    return player;
                }
            }
        }
        System.out.println( ANSI_RED_BACKGROUND + "sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        return null;
    }

    private Team getPlayerTeam(String name)
    {
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(name))
                {
                    return team;
                }
            }
        }
        System.out.println( ANSI_RED_BACKGROUND + "sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        return null;
    }
}
