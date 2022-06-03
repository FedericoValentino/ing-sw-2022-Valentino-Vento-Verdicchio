package Client.GUI.Controllers;

import Client.LightView.LightPlayer;
import Client.LightView.LightSchool;
import Client.LightView.LightTeam;
import Observer.ObserverLightView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.boards.token.Student;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MineSchoolController implements ObserverLightView
{
    private AnchorPane MySchool;
    private String PlayerName;

    public void setup(ArrayList<LightTeam> Teams,String PlayerName, AnchorPane School)
    {
        this.MySchool = School;
        this.PlayerName = PlayerName;
        for(LightTeam t: Teams)
        {
            for(LightPlayer p : t.getPlayers())
            {
                if(p.getNome().equals(PlayerName))
                {
                    p.getSchool().addObserverLight(this);
                    for(int i = 0; i < p.getSchool().getEntrance().size(); i++)
                    {
                        int finalI = i;
                        Pane entrance_pos = (Pane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                        entrance_pos.setOnMouseClicked((event) -> {
                            //lambda definition
                        });
                    }
                    update(p.getSchool());
                }
            }
        }
    }


    public String getRightColorPath(Student s)
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

    public String getSchoolColorPath(LightSchool school)
    {
        switch(school.getColor())
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

    public Pane getCellFromGridPane(GridPane matrix, int column, int row)
    {
        for(Node N : matrix.getChildren())
        {
            int rowN = GridPane.getRowIndex(N);
            int columnN = GridPane.getColumnIndex(N);
            if(rowN == row && columnN == column)
            {
                return (Pane)N;
            }
        }
        return null;
    }

    @Override
    public void update(Object o)
    {
        LightSchool school = (LightSchool) o;
        String TowerColorPath = getSchoolColorPath(school);
        //Updating Entrance
        for(int i = 0; i < school.getEntrance().size(); i++)
        {
            int finalI = i;
            Pane entrance_pos = (Pane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
            entrance_pos.getChildren().clear();
            entrance_pos.getChildren().add(new ImageView(getRightColorPath(school.getEntrance().get(i))));
        }
        //Updating Dining
        HBox diningRoom = (HBox)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("DiningRoom")).collect(Collectors.toList()).get(0);
        for(int i = 0; i < 5; i++)
        {
            for(int j = 9; j >= school.getDiningRoom()[i]; j--)
            {
                ((VBox)diningRoom.getChildren().get(i)).getChildren().get(j).setVisible(false);
            }
        }
        //Updating Prof
        HBox profTable = (HBox)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ProfTable")).collect(Collectors.toList()).get(0);
        for(int i = 0; i < 5; i++)
        {
            profTable.getChildren().get(i).setVisible(school.getProfessorTable()[i]);
        }
        //Updating Towers
        GridPane towers = (GridPane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Towers")).collect(Collectors.toList()).get(0);
        int tempTowers = school.getTowerCount();

        for(int i = 0; i < 2 && tempTowers > 0; i++)
        {
            for(int j = 0; j < 4 && tempTowers > 0; j++)
            {
                Pane cell = getCellFromGridPane(towers, j, i);
                cell.getChildren().clear();
                cell.getChildren().add(new ImageView(TowerColorPath));
                tempTowers--;
            }
        }


    }
}
