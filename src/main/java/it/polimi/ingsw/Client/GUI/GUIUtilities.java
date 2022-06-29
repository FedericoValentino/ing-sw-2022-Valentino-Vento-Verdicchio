package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public final class GUIUtilities
{
    /**Choose the correct student's path according to the type of Student inserted
     * @param s is the student passed
     * @return the path to the right color for the current student
     */
    public static String getRightColorPath(Student s)
    {
        switch(s.getColor())
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
     * Choose the correct path according to the color that receive as input
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
     * @param matrix is the GridPane from where we wanto to extract a particular cell
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
