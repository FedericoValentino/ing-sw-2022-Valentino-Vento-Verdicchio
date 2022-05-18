package Client.CLI;

import Client.LightView;
import model.Player;
import model.Team;
import model.boards.Cloud;
import model.boards.Island;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;

import java.util.ArrayList;

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
            return "0" + Integer.toString(X);
        else
            return Integer.toString(X);
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
        for(int k=0; k<9; k++)
            islands[k] = "";

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
                for(int k=0; k<9; k++)
                    islands[k] = "";

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
                    for(Student s: player.getSchool().getEntrance())
                    {
                        switch(s.getColor())
                        {
                            case GREEN:
                                output += ANSI_GREEN + "  X";
                                break;
                            case RED:
                                output += ANSI_RED + "  X";
                                break;
                            case YELLOW:
                                output += ANSI_YELLOW + "  X";
                                break;
                            case PINK:
                                output += ANSI_PURPLE + "  X";
                                break;
                            case BLUE:
                                output += ANSI_BLUE + "  X";
                                break;
                        }
                    }
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
        if(name.length() > 10)
            name = name.substring(0,10);
        if(currentPlayer.length() > 10)
            currentPlayer = currentPlayer.substring(0,10);

        if(!currentPlayer.equals(name))
            schools[0] += "This is " + ANSI_CYAN + name + ANSI_RESET + "'s school\t\t\t\t\t\t\t";
        else
        {
            schools[0] += ANSI_GREEN + currentPlayer + ANSI_RESET + ", this is " + ANSI_GREEN + "YOUR school\t\t\t\t\t\t\t" + ANSI_RESET;
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

        for (int k = 0; k < 12; k++)
            schools[k] = "";

        int totalSchools = 0;

        for (Team team : view.getCurrentTeams()) {
            for (Player player : team.getPlayers()) {
                if (name.equals("-1")) {
                    totalSchools++;
                    schools = printSchool(schools, player, currentPlayer);
                    if (totalSchools == 2) {
                        for (int i = 0; i < 12; i++) {
                            System.out.println(schools[i]);
                            totalSchools = 0;
                        }
                        for (int k = 0; k < 12; k++)
                            schools[k] = "";
                    }
                } else if (player.getNome().equals(name)) {
                    totalSchools++;
                    schools = printSchool(schools, player, currentPlayer);
                    for (int i = 0; i < 12; i++) {
                        System.out.println(schools[i]);
                        totalSchools = 0;
                    }
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
        if (totalSchools == 0)
            System.out.println("Sorry, Player not found");

        System.out.println();
    }

    private String printStudentOnCloud(ArrayList<Student> students)
    {
        String output = "";
        int cloudCount = students.size();
        for(Student s: students)
        {
            switch(s.getColor())
            {
                case GREEN:
                    output += ANSI_GREEN + " X";
                    break;
                case RED:
                    output += ANSI_RED + " X";
                    break;
                case YELLOW:
                    output += ANSI_YELLOW + " X";
                    break;
                case PINK:
                    output += ANSI_PURPLE + " X";
                    break;
                case BLUE:
                    output += ANSI_BLUE + " X";
                    break;
            }
        }
        for(int i = 0; i < 4 - cloudCount; i++)
        {
            output += ANSI_RESET + " O";
        }
        return output;
    }

    public void showCloud()
    {
        String[] clouds = new String[5];
        for (int k = 0; k < 5; k++)
            clouds[k] = "";

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

    public void showAssistant(int id)
    {

    }

    public void showCharacter(int id)
    {

    }

    public void showPlayer(int id)
    {

    }
}
