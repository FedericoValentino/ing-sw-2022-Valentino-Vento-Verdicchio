package Client.CLI;

import Client.LightView;
import model.Player;
import model.Team;
import model.boards.Island;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;

public class PrinterCLI
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
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
        String color = "";
        switch (tablePosition)
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
        for(Team team: view.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    if(player.getSchool().getProfessorTable()[tablePosition])
                        output = color + "P";
                    else
                        output = "O";
                }
            }
        }
        return output + ANSI_RESET;
    }

    public void showSchool(String name)
    {
        if(!name.equals("-1"))
        {
            Player p = null;
            for (Team team : view.getCurrentTeams()) {
                for (Player player : team.getPlayers()) {
                    if (player.getNome().equals(name)) {
                        p = player;
                    }
                }
            }
            System.out.println(name);
            System.out.println("____________________________________________");
            System.out.println("|     0  1  2  3  4  5  6  7  8            |");
            System.out.println("| E:" + printEntrance(name) + ANSI_RESET + "            |");
            System.out.println("|__________________________________________|");
            System.out.println("|                                          |");
            System.out.println("| G:" + printDinnerTable(name, 0) + "  || " + printHasProf(name, 0) + "             |");
            System.out.println("| R:" + printDinnerTable(name, 1) + "  || " + printHasProf(name, 1) + "             |");
            System.out.println("| Y:" + printDinnerTable(name, 2) + "  || " + printHasProf(name, 2) + "    TOW " + addZero(p.getSchool().getTowerCount()) + "   |");
            System.out.println("| P:" + printDinnerTable(name, 3) + "  || " + printHasProf(name, 3) + "             |");
            System.out.println("| B:" + printDinnerTable(name, 4) + "  || " + printHasProf(name, 4) + "             |");
            System.out.println("|__________________________________________|");
        }
    }

    public void showCloud(int id)
    {

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
