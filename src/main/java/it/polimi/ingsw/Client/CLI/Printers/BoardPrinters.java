package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightBoards.LightIsland;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class BoardPrinters hosts all the methods necessary to print to screen the various game boards contained in the view
 */
public class BoardPrinters extends PrinterCLI
{

    /** Class constructor; through the parent class constructor, it grants access to the view
     * @param view the LightView coming from the PrinterCLI class
     */
    public BoardPrinters(LightView view)
    {
        super(view);
    }

    /** If Mother Nature is present on the selected island, it prints an X, if not, it returns an O
     * @param id the island position
     * @return a string that the function "showIsland" will print to indicate whether Mother Nature is present on the island
     */
    private String printMN(int id)
    {
        if(super.getView().getCurrentIslands().getIslands().get(id).isMotherNature())
            return "X";
        else
            return "O";
    }


    /** Used by the function "showIslands", it constructs and returns the outline and content of the graphical representation
     * of the desired island
     * @param islands the array of strings to manipulate
     * @param id the chosen island
     */
    private void printIsland(String[] islands, int id)
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
    }


    /** Using the auxiliary functions discussed above, it prints the island or the islands, based on the island id it receives
     * @param id the selected island
     */
    public void showIsland(int id)
    {
        String[] islands = new String[9];
        Arrays.fill(islands, "");

        if(id >= 0)
        {
            printIsland(islands, id);
            for(int k = 0; k < 9; k++)
            {
                AnsiConsole.out().println(islands[k]);
            }
        }
        else
        {
            int totalIslands = super.getView().getCurrentIslands().getIslands().size();
            int half = totalIslands / 2;
            if(totalIslands%2 != 0)
            {
                half++;
            }
            for(int i = 0; i < 2; i++)
            {
                Arrays.fill(islands, "");

                for(int j = 0; j < half && j + i * 6 < totalIslands; j++)
                {
                    printIsland(islands, j + i * 6);
                }

                for(int k = 0; k < 9; k++)
                {
                    AnsiConsole.out().println(islands[k]);
                }
            }
        }
    }


    /** Used by the function "showCloud", it returns a string containing the graphical representation of the students on the cloud.
     * A colored X is used to mark the presence of a student (of the same color), while an O is used to represent a vacant space
     * @param students the list containing the students on the cloud
     * @return a string representing the students on the cloud
     */
    private String printStudentOnCloud(ArrayList<Student> students)
    {
        StringBuilder output = new StringBuilder();
        int cloudCount = students.size();
        output.append(printStudent(students, 1));
        for(int i = 0; i < 4 - cloudCount; i++)
        {
            output.append(ANSI_RESET + " O");
        }
        return output.toString();
    }


    /** Upon player's command, and automatically upon every move by any players, it prints the clouds
     */
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
