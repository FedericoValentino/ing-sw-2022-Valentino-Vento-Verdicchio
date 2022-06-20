package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightIsland;
import it.polimi.ingsw.Client.LightView.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardPrinters extends PrinterCLI
{
    public BoardPrinters(LightView view)
    {
        super(view);
    }

    private String printMN(int id)
    {
        if(super.getView().getCurrentIslands().getIslands().get(id).isMotherNature())
            return "X";
        else
            return "O";
    }

    private String[] printIsland(String[] islands, int id)
    {
        int StudentNumber;
        LightIsland island = super.getView().getCurrentIslands().getIslands().get(id);
        islands[0] += "____________________  ";
        islands[1] += "|   Island n: " + addZero(island.getIslandId()) +"   |  ";
        islands[2] += "|       MN: " + printMN(island.getIslandId()) + "      |  ";
        StudentNumber = (int)super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.GREEN).count();
        islands[3] += "| " + ANSI_GREEN + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |  ";
        StudentNumber = (int)super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.RED).count();
        islands[4] += "| " + ANSI_RED + ":D " + ANSI_RESET + addZero(StudentNumber) + "    " + convertTo3Char(super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getOwnership())+ "     |  " ;
        StudentNumber = (int)super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
        islands[5] += "| " + ANSI_YELLOW + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |  ";
        StudentNumber = (int)super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.PINK).count();
        islands[6] += "| " + ANSI_PURPLE + ":D " + ANSI_RESET + addZero(StudentNumber) + "   TOW " + addZero(super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getTowerNumber()) + "   |  ";
        StudentNumber = (int)super.getView().getCurrentIslands().getIslands().get(island.getIslandId()).getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.BLUE).count();
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
                AnsiConsole.out().println(islands[k]);
            }
        }
        else
        {

            int totalIslandsPrinted = 1;
            for(int i = 0; i < 2; i++)
            {
                Arrays.fill(islands, "");

                for(int j = i * 6; j < super.getView().getCurrentIslands().getIslands().size(); j++)
                {
                    islands = printIsland(islands, j);
                    totalIslandsPrinted++;
                    if(totalIslandsPrinted > super.getView().getCurrentIslands().getIslands().size()/2)
                    {
                        break;
                    }
                }

                for(int k = 0; k < 9; k++)
                {
                    AnsiConsole.out().println(islands[k]);
                }
                totalIslandsPrinted = 1;
            }
        }
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

        for(int i = 0; i < super.getView().getCurrentClouds().length; i++)
        {
            clouds[0] += " Cloud " + i + "\t ";
            clouds[1] += " _________ \t ";
            clouds[2] += "/         \\\t ";
            clouds[3] += "|" + printStudentOnCloud(super.getView().getCurrentClouds()[i].getStudents()) +" |\t ";
            clouds[4] += "\\_________/\t ";
        }

        for (int k = 0; k < 5; k++)
            AnsiConsole.out().println(clouds[k]);
    }

}
