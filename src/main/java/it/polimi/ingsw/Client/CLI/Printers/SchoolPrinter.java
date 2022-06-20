package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;

public class SchoolPrinter extends PrinterCLI
{
    public SchoolPrinter(LightView view)
    {
        super(view);
    }

    private String printEntrance(String nome)
    {
        String output = "";
        int entranceCount = 0;
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
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
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
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
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
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

    private String[] printSchool(String[] schools, LightPlayer player, String currentPlayer)
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

        for (LightTeam team : super.getView().getCurrentTeams()) {
            for (LightPlayer player : team.getPlayers()) {
                if (name.equals("-1"))
                {
                    totalSchools++;
                    schools = printSchool(schools, player, currentPlayer);
                    if (totalSchools == 2) {
                        for (int i = 0; i < 12; i++) {
                            AnsiConsole.out().println(schools[i]);
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
                        AnsiConsole.out().println(schools[i]);
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
                AnsiConsole.out().println(schools[i]);
                totalSchools = 0;
            }
        }

        if(playerNotFoundCounter == super.getView().getCurrentTeams().size() * super.getView().getCurrentTeams().get(0).getPlayers().size())
            AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);

        System.out.println();
    }


}
