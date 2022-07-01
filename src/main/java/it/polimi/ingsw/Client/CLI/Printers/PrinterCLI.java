package it.polimi.ingsw.Client.CLI.Printers;
//
import it.polimi.ingsw.Client.LightView.*;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;
import org.fusesource.jansi.AnsiConsole;


import java.util.ArrayList;

/**
 * Abstract Class PrinterCLI hosts all the methods and attributes necessary for the other printer classes
 */
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


    /**
     * Class constructor: it instantiates the LightView
     * @param lv the LightView
     */
    public PrinterCLI(LightView lv)
    {
        this.view = lv;
    }


    /**
     * Necessary to display numbers from 0 to 99 with ease and order, if the input parameter, which is the number that must
     * be showed, is composed by a single cipher, it adds a 0 in front of it, to make spacing uniform
     * @param X the number to represent
     * @return 0X if X was less than 10, the string equivalent of X if it isn't
     */
    protected String addZero(int X)
    {
        if(X < 10)
            return "0" + X;
        else
            return String.valueOf(X);
    }


    /**
     *  Clear command for the console
     */
    public static void cls()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints the title of the game
     */
    public static void printTitle()
    {
        String[] array = new String[6];
        array[0] = "███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗";
        array[1] = "██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝";
        array[2] = "█████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗";
        array[3] = "██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║";
        array[4] = "███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║";
        array[5] = "╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝";
        for(int i = 0; i < 6; i++)
        {
            AnsiConsole.out().println(array[i]);
        }
    }


    /**
     * Necessary to resolve the inevitable problems of spacing when dealing with name of different lengths, it brings the
     * nickname in input to be 10 characters long, either adding blank spaces or trimming it
     * @param name the name to extend or trim
     * @return the extended or trimmed name
     */
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


    /**
     * To uniform the representation of the teams and towers colors, it returns a string containing the identifier of the
     * desired color, composed by three letters
     * @param towerColor the color to trim
     * @return the trimmed color
     */
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


    /**
     * Used by multiple printers to print students using the correct color and desired spacing between them
     * @param students a generic list of students
     * @param spaces the amount of spaces desired between each student
     * @return the string representing the list of students
     */
    public static String printStudent(ArrayList<Student> students, int spaces)
    {
        StringBuilder output = new StringBuilder();
        for(Student s: students)
        {
            for(int i = 0; i < spaces; i++)
            {
                output.append(" ");
            }
            switch(s.getColor())
            {
                case GREEN:
                    output.append(ANSI_GREEN + "X");
                    break;
                case RED:
                    output.append(ANSI_RED + "X");
                    break;
                case YELLOW:
                    output.append(ANSI_YELLOW + "X");
                    break;
                case PINK:
                    output.append(ANSI_PURPLE + "X");
                    break;
                case BLUE:
                    output.append(ANSI_BLUE + "X");
                    break;
            }
        }
        return output.append(ANSI_RESET).toString();
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
