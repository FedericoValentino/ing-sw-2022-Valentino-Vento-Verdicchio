package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;
////
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightBoards.LightSchool;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.MoveStudent;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import it.polimi.ingsw.model.boards.token.Student;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MineSchoolController implements ObserverLightView
{
    @FXML private Pane studentChoice;
    @FXML private ChoiceBox<String> islands;
    @FXML private Button toDining;
    @FXML private Button toIsland;

    private AnchorPane MySchool;
    private int studentEntrancePos;
    private LightView view;

    MouseEvent eventStudent;
    public void entranceClick(MouseEvent event)
    {
        //mi salvo l'evento per poterlo rimuovere dopo invece che dover cercare tra tutti i pane della school
        eventStudent=event;
        ((Pane) event.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
        if(!studentChoice.isVisible())
        {
            studentChoice.setVisible(true);
            studentEntrancePos = Integer.parseInt(((Node)event.getSource()).getId().replace("entrance_", ""));
        }
        else
        {
            if(studentEntrancePos == Integer.parseInt(((Node)event.getSource()).getId().replace("entrance_", "")))
            {
                ((Pane) event.getSource()).setEffect(null);
                studentChoice.setVisible(false);
            }
            studentEntrancePos = Integer.parseInt(((Node)event.getSource()).getId().replace("entrance_", ""));
        }
    }

    public void sendDining(MouseEvent event)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new MoveStudent(studentEntrancePos, false, 0)));
        //tolgo il pane di scelta e disabilito l'effetto rosso dello student
        studentChoice.setVisible(false);
        ((Pane) eventStudent.getSource()).setEffect(null);
    }

    public void sendIsland(MouseEvent event)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new MoveStudent(studentEntrancePos, true, Integer.parseInt(islands.getValue()))));
        //tolgo il pane di scelta e disabilito l'effetto rosso dello student
        studentChoice.setVisible(false);
        ((Pane) eventStudent.getSource()).setEffect(null);
    }

    public void setup(LightView view,String PlayerName, AnchorPane School)
    {
        this.view = view;
        this.MySchool = School;
        for(LightTeam t: view.getCurrentTeams())
        {
            for(LightPlayer p : t.getPlayers())
            {
                if(p.getName().equals(PlayerName))
                {
                    p.getSchool().addObserverLight(this);
                    for(int i = 0; i < p.getSchool().getEntrance().size(); i++)
                    {
                        int finalI = i;
                        Pane entrance_pos = (Pane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                        entrance_pos.setOnMouseClicked(this::entranceClick);
                    }
                    update(p.getSchool());
                }
            }
        }
        for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
        {
            islands.getItems().add(Integer.toString(i));
        }
        toDining.setOnMouseClicked(this::sendDining);
        toIsland.setOnMouseClicked(this::sendIsland);
    }


    @Override
    public void update(Object o)
    {
        Platform.runLater(()->{
            LightSchool school = (LightSchool) o;
            String TowerColorPath = GUIUtilities.getSchoolColorPath(school.getColor());
            //Updating ChoiceBox
            islands.getItems().clear();
            for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
            {
                islands.getItems().add(Integer.toString(i));
            }
            //Updating Entrance
            for(int i = 0; i < 9; i++)
            {
                int finalI = i;
                Pane entrance_pos = (Pane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                entrance_pos.setVisible(false);
            }
            for(int i = 0; i < school.getEntrance().size(); i++)
            {
                int finalI = i;
                Pane entrance_pos = (Pane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                entrance_pos.getChildren().clear();
                ImageView nImage=new ImageView(GUIUtilities.getRightColorPath(school.getEntrance().get(i)));
                nImage.setFitHeight(27);
                nImage.setFitWidth(27);
                entrance_pos.getChildren().add(nImage);
                entrance_pos.setVisible(true);
            }
            //Updating Dining
            HBox diningRoom = (HBox)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("DiningRoom")).collect(Collectors.toList()).get(0);
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
            HBox profTable = (HBox)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ProfTable")).collect(Collectors.toList()).get(0);
            for(int i = 0; i < 5; i++)
            {
                profTable.getChildren().get(i).setVisible(school.getProfessorTable()[i]);
            }
            //Updating Towers
            GridPane towers = (GridPane)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Towers")).collect(Collectors.toList()).get(0);
            int tempTowers = school.getTowerCount();
            for(int i = 0; i < 2; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    Pane cell = (Pane) GUIUtilities.getCellFromGridPane(towers, j, i);
                    cell.setVisible(false);
                }
            }
            for(int i = 0; i < 2 && tempTowers > 0; i++)
            {
                for(int j = 0; j < 4 && tempTowers > 0; j++)
                {
                    Pane cell = (Pane) GUIUtilities.getCellFromGridPane(towers, j, i);
                    cell.getChildren().clear();
                    cell.getChildren().add(new ImageView(TowerColorPath));
                    cell.setVisible(true);
                    tempTowers--;
                }
            }
        });
    }
}
