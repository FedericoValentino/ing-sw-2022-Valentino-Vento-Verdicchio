package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;


import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightBoards.LightSchool;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * GUI controller responsible for the graphical representation and the functions associated with the other players' schools
 */
public class OtherSchoolController extends Controller implements ObserverLightView {
    private AnchorPane APotherSchool;
    private ArrayList<LightTeam> teams;
    private String lookedPlayer;
    private LightView view;


    /**
     * Method onClick sets the attribute lookedPlayer and changes the school to be visualized
     * @param event
     */
    public void onClick(ActionEvent event)
    {
        this.lookedPlayer = ((Button)event.getSource()).getText();
        update(Utilities.getSchoolByName(view, lookedPlayer));
    }

    /**
     * Method setup adds an observer to every other player school and sets up the buttons to switch between them
     * @param view is our current game view
     * @param otherSchool is the AnchorPane used to set the panel in the right position
     */
    public void setup(LightView view, AnchorPane otherSchool)
    {
        this.view = view;
        this.APotherSchool = otherSchool;
        this.teams = view.getCurrentTeams();
        for(LightTeam t: this.teams)
        {
            for(LightPlayer p: t.getPlayers())
            {
                p.getSchool().addObserverLight(this);
            }
        }
        for(Node button : ((HBox)otherSchool.getChildren().get(1)).getChildren())
        {
            Button b = (Button) button;
            b.setOnAction(this::onClick);
        }
        this.lookedPlayer = ((Button)((HBox)otherSchool.getChildren().get(1)).getChildren().get(0)).getText();
        update(Utilities.getSchoolByName(view, lookedPlayer));
    }


    /**
     * Method "update" updates the school only if the school passed in the object parameter is the currently visualized player's school.
     * The update goes the same as the one in the "mineSchoolController" without the setup of buttons on the entrance panes
     * @param o is the school to be updated
     */
    @Override
    public void update(Object o)
    {
        Platform.runLater(()->{
            LightSchool school = (LightSchool) o;
            String TowerColorPath = GUIUtilities.getSchoolColorPath(school.getColor());
            if(lookedPlayer.equals(school.getOwner()))
            {

                for(int i = 0; i < 9; i++)
                {
                    int finalI = i;
                    Pane entrance_pos = (Pane)((AnchorPane) APotherSchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                    entrance_pos.setVisible(false);
                }
                for(int i = 0; i < school.getEntrance().size(); i++)
                {
                    int finalI = i;
                    Pane entrance_pos = (Pane)((AnchorPane) APotherSchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("entrance_"+ finalI)).collect(Collectors.toList()).get(0);
                    entrance_pos.getChildren().clear();
                    ImageView nImage=new ImageView(GUIUtilities.getRightColorPath(school.getEntrance().get(i)));
                    nImage.setFitHeight(27);
                    nImage.setFitWidth(27);
                    entrance_pos.getChildren().add(nImage);
                    entrance_pos.setVisible(true);
                }

                HBox diningRoom = (HBox)((AnchorPane) APotherSchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("DiningRoom")).collect(Collectors.toList()).get(0);
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

                HBox profTable = (HBox)((AnchorPane) APotherSchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ProfTable")).collect(Collectors.toList()).get(0);
                for(int i = 0; i < 5; i++)
                {
                    profTable.getChildren().get(i).setVisible(school.getProfessorTable()[i]);
                }

                GridPane towers = (GridPane)((AnchorPane) APotherSchool.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Towers")).collect(Collectors.toList()).get(0);
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
            }
        });


    }
}
