package it.polimi.ingsw.Client.CLI.Printers;
//
import it.polimi.ingsw.Client.LightView.*;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;
import org.fusesource.jansi.AnsiConsole;


import java.util.ArrayList;

public abstract class PrinterCLI
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

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";



    private LightView view;


    public PrinterCLI(LightView lv)
    {
        this.view = lv;
    }

    protected String addZero(int X)
    {
        if(X < 10)
            return "0" + X;
        else
            return String.valueOf(X);
    }

    public static void cls()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected String nameTrimmer(String name)
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


    protected String convertTo3Char(ColTow towerColor)
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


    public static String printStudent(ArrayList<Student> students, int spaces)
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

    public static LightPlayer getPlayerByName(String name, LightView view)
    {
        for(LightTeam team: view.getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(name))
                {
                    return player;
                }
            }
        }
        AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        return null;
    }

    protected LightTeam getPlayerTeam(String name)
    {
        for(LightTeam team: view.getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(name))
                {
                    return team;
                }
            }
        }
        AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        return null;
    }



    public LightView getView()
    {
        return view;
    }

    public void setView(LightView view)
    {
        this.view = view;
    }
}
