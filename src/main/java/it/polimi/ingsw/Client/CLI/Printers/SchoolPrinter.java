package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;

/**
 * Class SchoolPrinters hosts all the methods necessary to print to screen the school game board
 */
public class SchoolPrinter extends PrinterCLI
{
    /**
     * Class constructor; through the parent class constructor, it grants access to the view
     * @param view the LightView coming from the PrinterCLI class
     */
    public SchoolPrinter(LightView view)
    {
        super(view);
    }


    /**
     * Given a player's name, returns the player's school entrance, using the function "printStudent"
     * @param nome the player's name
     * @return the string representing the entrance, i.e a list of students
     */
    private String printEntrance(String nome)
    {
        StringBuilder output = new StringBuilder();
        int entranceCount = 0;
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(nome))
                {
                    entranceCount = player.getSchool().getEntrance().size();
                    output.append(printStudent(player.getSchool().getEntrance(), 2));
                }
            }
        }
        for(int i = 0; i < 9 - entranceCount; i++)
        {
            output.append(ANSI_RESET + "  O");
        }
        return output.append(ANSI_RESET).toString();
    }


    /**
     * Given a player's name and the row of the dining room, it returns a list of students (colored X), blank spaces (O)
     *  and coin spaces (grey C), representing that row of the player's school dining table
     * @param nome the player's name
     * @param dinnerPosition identifies the row of the dining table
     * @return the representation of the row of the dining room
     */
    private String printDinnerTable(String nome, int dinnerPosition)
    {
        StringBuilder output = new StringBuilder();
        String color = "";
        switch (dinnerPosition)
        {
            case 0:
                color = ANSI_GREEN;
                break;
            case 1:
                color = ANSI_RED;
                break;
            case 2:
                color = ANSI_YELLOW;
                break;
            case 3:
                color = ANSI_PURPLE;
                break;
            case 4:
                color = ANSI_BLUE;
                break;
        }
        output.append(color);
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(nome))
                {
                    for(int i = 0; i < 10; i++)
                    {
                        if(i < player.getSchool().getDiningRoom()[dinnerPosition])
                        {
                            output.append(" X");
                        }
                        else if(i == 2 || i == 5 || i == 8)
                        {
                            output.append(ANSI_WHITE + " C").append(color);
                        }
                        else
                        {
                            output.append(ANSI_RESET + " O").append(color);
                        }
                    }
                }
            }
        }
        return  output.append(ANSI_RESET).toString();
    }


    /**
     * Return a string containing a colored P if the player controls the professor identified by the color of the dining table row,
     * identified by "tablePosition"; returns a white O if the professor is not present
     * @param nome the player's name
     * @param tablePosition identifies the correct dining table row
     * @return the string representing a professor or the absence of thereof
     */
    private String printHasProf(String nome, int tablePosition)
    {
        String output = "";
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(nome))
                {
                    if(player.getSchool().getProfessorTable()[tablePosition])
                        output = ANSI_GREEN_BACKGROUND + ANSI_BLACK + "P" + ANSI_RESET;
                    else
                        output = ANSI_RED_BACKGROUND + ANSI_BLACK + "O" + ANSI_RESET;
                }
            }
        }
        return output;
    }


    /**
     * Using the aforementioned auxiliary methods, it fills the given string array with the drawing of a full school
     * @param schools the string to manipulate
     * @param player the player owning rhe school
     * @param currentPlayer the current player, used to understand which school description the method should use
     */
    private void printSchool(String[] schools, LightPlayer player, String currentPlayer)
    {
        String name = player.getName();
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
    }


    /**
     * Shows the selected player's school, using the aforementioned auxiliary methods
     * @param name the player's name used to identify the correct school to display
     * @param currentPlayer necessary to the "printSchool" function above
     */
    public void showSchool(String name, String currentPlayer) {
        String[] schools = new String[12];
        Arrays.fill(schools, "");

        int totalSchools = 0;
        int playerNotFoundCounter = 0;

        for (LightTeam team : super.getView().getCurrentTeams()) {
            for (LightPlayer player : team.getPlayers()) {
                if (name.equals("-1"))
                {
                    totalSchools++;
                    printSchool(schools, player, currentPlayer);
                    if (totalSchools == 2) {
                        for (int i = 0; i < 12; i++) {
                            AnsiConsole.out().println(schools[i]);
                            totalSchools = 0;
                        }
                        System.out.println();
                        Arrays.fill(schools, "");
                    }
                }
                else if (player.getName().equals(name))
                {
                    totalSchools++;
                    printSchool(schools, player, currentPlayer);
                    for (int i = 0; i < 12; i++) {
                        AnsiConsole.out().println(schools[i]);
                        totalSchools = 0;
                    }
                }
                else if(!player.getName().equals((name)))
                {
                    playerNotFoundCounter += 1;
                }
            }
        }
        System.out.println();
        if(totalSchools > 0)
        {
            for (int i = 0; i < 12; i++) {
                AnsiConsole.out().println(schools[i]);
            }
        }

        if(playerNotFoundCounter == super.getView().getCurrentTeams().size() * super.getView().getCurrentTeams().get(0).getPlayers().size())
            AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);

        System.out.println();
    }


}
