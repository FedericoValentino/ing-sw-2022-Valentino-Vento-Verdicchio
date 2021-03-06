package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;

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
import javafx.scene.paint.Color;

import java.util.stream.Collectors;

/**
 * GUI controller responsible for the graphical representation and the functions associated with the Client owner's school
 */
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

    /**
     * Method entranceClick is called whenever a click is registered on a student token in the entrance. It makes the
     * studentChoice table visible to the user and then gets the id of the student in the entrance
     * @param event
     */
    public void entranceClick(MouseEvent event)
    {
        //it saves the event to preserve the reference to the pane and to remove the following effect from the student later
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

    /**
     * This method send the movestudent message to the server, with parameters that will move the student with ID studentChoice into the diningRoom
     * @param event
     */
    public void sendDining(MouseEvent event)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new MoveStudent(studentEntrancePos, false, 0)));
        //removes the choice pane of the students and removes the effect
        studentChoice.setVisible(false);
        ((Pane) eventStudent.getSource()).setEffect(null);
    }

    /**
     * This method send the movestudent message to the server, with parameters that will move the student with ID studentChoice into the island with id contained in the choice box
     * @param event
     */
    public void sendIsland(MouseEvent event)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new MoveStudent(studentEntrancePos, true, Integer.parseInt(islands.getValue()))));
        //tolgo il pane di scelta e disabilito l'effetto rosso dello student
        studentChoice.setVisible(false);
        ((Pane) eventStudent.getSource()).setEffect(null);
    }


    /**
     * Method setup sets up the school at the start of the game by looking at the school entrance and tower number
     * @param view is the current view of the game
     * @param PlayerName is the user's name
     * @param School is the anchorPane for the school placement in the GUI
     */
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


    /**
     * Method update is called whenever our user's school is updated. It firsts updates the islands choicebox contained in the studentchoice pane.
     * Then it removes every student from the entrance to elaborate new ids and set up the onClick functions. Then is looks at the dining room and sets
     * the right amount of tokens to be visible. Then it looks at the professor table, doing the same job and at the end updates the towers hiding the ones that have been placed on islands
     * @param o is our school
     */
    @Override
    public void update(Object o)
    {
        Platform.runLater(()->{
            LightSchool school = (LightSchool) o;
            String TowerColorPath = GUIUtilities.getSchoolColorPath(school.getColor());

            islands.getItems().clear();
            for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
            {
                islands.getItems().add(Integer.toString(i));
            }

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

            HBox profTable = (HBox)((AnchorPane)MySchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ProfTable")).collect(Collectors.toList()).get(0);
            for(int i = 0; i < 5; i++)
            {
                profTable.getChildren().get(i).setVisible(school.getProfessorTable()[i]);
            }

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
