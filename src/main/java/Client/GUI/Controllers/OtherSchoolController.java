package Client.GUI.Controllers;


import Client.LightView.LightPlayer;
import Client.LightView.LightSchool;
import Client.LightView.LightTeam;
import Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.boards.token.Student;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OtherSchoolController extends Controller implements ObserverLightView {
    public AnchorPane APotherSChool;
    public ArrayList<LightTeam> teams;
    public String lookedPlayer;



    public void onClick(ActionEvent event)
    {
        this.lookedPlayer = ((Button)event.getSource()).getText();
        update(getSchoolByName(lookedPlayer));
    }

    public void setup(ArrayList<LightTeam> Teams, AnchorPane OtherSchool)
    {
        this.APotherSChool = OtherSchool;
        this.teams = Teams;
        for(LightTeam t: teams)
        {
            for(LightPlayer p: t.getPlayers())
            {
                p.getSchool().addObserverLight(this);
            }
        }
        for(Node button : ((HBox)OtherSchool.getChildren().get(1)).getChildren())
        {
            Button b = (Button) button;
            b.setOnAction(this::onClick);
        }
        this.lookedPlayer = ((Button)((HBox)OtherSchool.getChildren().get(1)).getChildren().get(0)).getText();
        update(getSchoolByName(lookedPlayer));
    }

    public LightSchool getSchoolByName(String username)
    {
        for(LightTeam t: teams)
        {
            for(LightPlayer p: t.getPlayers())
            {
                if(p.getNome().equals(username))
                {
                    return p.getSchool();
                }
            }
        }
        return null;
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
        Platform.runLater(()->{
            LightSchool school = (LightSchool) o;
            String TowerColorPath = getSchoolColorPath(school);
            if(lookedPlayer.equals(school.getOwner()))
            {
                //Updating Entrance
                for(int i = 0; i < 9; i++)
                {
                    int finalI = i;
                    Pane entrance_pos = (Pane)((AnchorPane)APotherSChool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                    entrance_pos.setVisible(false);
                }
                for(int i = 0; i < school.getEntrance().size(); i++)
                {
                    int finalI = i;
                    Pane entrance_pos = (Pane)((AnchorPane)APotherSChool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                    entrance_pos.getChildren().clear();
                    ImageView nImage=new ImageView(getRightColorPath(school.getEntrance().get(i)));
                    nImage.setFitHeight(27);
                    nImage.setFitWidth(27);
                    entrance_pos.getChildren().add(nImage);
                    entrance_pos.setVisible(true);
                }
                //Updating Dining
                HBox diningRoom = (HBox)((AnchorPane)APotherSChool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("DiningRoom")).collect(Collectors.toList()).get(0);
                for(int i = 0; i < 5; i++)
                {
                    for(int j = 9; j >= 0; j--)
                    {
                        ((VBox)diningRoom.getChildren().get(i)).getChildren().get(j).setVisible(false);
                    }
                }
                for(int i = 0; i < 5; i++)
                {
                    for(int j = 9; j > 9 - school.getDiningRoom()[i]; j--)
                    {
                        ((VBox)diningRoom.getChildren().get(i)).getChildren().get(j).setVisible(true);
                    }
                }
                //Updating Prof
                HBox profTable = (HBox)((AnchorPane)APotherSChool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ProfTable")).collect(Collectors.toList()).get(0);
                for(int i = 0; i < 5; i++)
                {
                    profTable.getChildren().get(i).setVisible(school.getProfessorTable()[i]);
                }
                //Updating Towers
                GridPane towers = (GridPane)((AnchorPane)APotherSChool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Towers")).collect(Collectors.toList()).get(0);
                int tempTowers = school.getTowerCount();
                for(int i = 0; i < 2 && tempTowers > 0; i++)
                {
                    for(int j = 0; j < 4 && tempTowers > 0; j++)
                    {
                        Pane cell = getCellFromGridPane(towers, j, i);
                        cell.setVisible(false);
                    }
                }
                for(int i = 0; i < 2 && tempTowers > 0; i++)
                {
                    for(int j = 0; j < 4 && tempTowers > 0; j++)
                    {
                        Pane cell = getCellFromGridPane(towers, j, i);
                        cell.getChildren().clear();
                        cell.getChildren().add(new ImageView(TowerColorPath));
                        cell.setVisible(true);
                        tempTowers--;
                    }
                }
            }
        });


    }
}
