package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Utility class for frequently used and general purpose functions in the GUI
 */
public final class GUIUtilities
{
    /**
     * Chooses the correct student's path according to the type of Student received
     * @param student is the student passed
     * @return the path to the right color for the current student
     */
    public static String getRightColorPath(Student student)
    {
        switch(student.getColor())
        {
            case GREEN:
                return "/Client/GUI/Images/Student/student_green.png";
            case YELLOW:
                return "/Client/GUI/Images/Student/student_yellow.png";
            case RED:
                return "/Client/GUI/Images/Student/student_red.png";
            case BLUE:
                return "/Client/GUI/Images/Student/student_blue.png";
            case PINK:
                return "/Client/GUI/Images/Student/student_pink.png";
            default:
                return "";
        }
    }

    /**
     * Chooses the correct path according to the color that received as input
     * @param color is the tower Color passed
     * @return the path as a String
     */
    public static String getSchoolColorPath(ColTow color)
    {
        switch(color)
        {
            case WHITE:
                return "/Client/GUI/Images/Tower/white_tower.png";
            case BLACK:
                return "/Client/GUI/Images/Tower/black_tower.png";
            case GREY:
                return "/Client/GUI/Images/Tower/grey_tower.png";
            default:
                return "";
        }
    }


    /**
     * Gets the correct cell from a GridPAne, given in input desired coordinates in the form of a row and column indexes.
     * Necessary because of the apparent lack of a standard java function that does the same thing
     * @param matrix is the GridPane from where we want to extract a particular cell
     * @param column is the column where there is the node that it has to return
     * @param row is the row where there is the node that it has to return
     * @return the cell as a Node element
     */
    public static Node getCellFromGridPane(GridPane matrix, int column, int row)
    {
        for(Node N : matrix.getChildren())
        {
            int rowN = GridPane.getRowIndex(N);
            int columnN = GridPane.getColumnIndex(N);
            if(rowN == row && columnN == column)
            {
                return N;
            }
        }
        return null;
    }
}
