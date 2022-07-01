package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;
//
import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightBoards.LightCloud;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.ChooseCloud;
import it.polimi.ingsw.Client.Messages.ActionMessages.DrawFromPouch;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

/**
 * GUI controller responsible for the graphics and functions associated to the CLouds
 */
public class CloudController extends Controller implements ObserverLightView
{
    private LightView view;

    @FXML private GridPane clouds;
    @FXML private GridPane cloud0;
    @FXML private GridPane cloud1;
    @FXML private GridPane cloud2;
    @FXML private GridPane cloud3;


    /**
     * Method CloudSelection is called when a click is registered on one of the many game clouds. It checks the gamePhase
     * and then sends the server the appropriate message.
     * @param event the mouseEvent
     */
    public void CloudSelection(MouseEvent event)
    {
        if(view.getCurrentTurnState().getGamePhase() == GamePhase.PLANNING)
        {
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new DrawFromPouch(Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", "")))));
        }
        else if(view.getCurrentTurnState().getGamePhase() == GamePhase.ACTION)
        {
            int cloudIndex = Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", ""));
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ChooseCloud(cloudIndex)));
        }
    }

    /**
     * Method Setup is called when initializing the cloud pane. It gets the right amount of clouds to show up on the screen
     * and starts observing each one of them
     * @param cloudsArray is the lightView representation of the clouds in the game model
     * @param view is the data structure containing all the information about the current turn.
     */
    public void setup(LightCloud[] cloudsArray, LightView view)
    {
        this.view = view;
        int temp = cloudsArray.length;
        for(int i = 0; i < 2 && temp > 0; i++)
        {
            for(int j = 0; j < 2 && temp > 0; j++)
            {
                StackPane pane = (StackPane) GUIUtilities.getCellFromGridPane(clouds, j, i);
                pane.setVisible(true);
                pane.setOnMouseClicked(this::CloudSelection);
                temp--;
            }
        }
        for(LightCloud c: cloudsArray)
        {
            c.addObserverLight(this);
        }
    }


    /**
     * Method "fill" fills the clouds with students
     * @param toFill is the GridPane containing the clouds to be filled
     * @param filler is the reference cloud containing the information on the students that need to be displayed
     */
    public void fill(GridPane toFill, LightCloud filler)
    {
        int temp = filler.getStudents().size();
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                Pane cell = (Pane) GUIUtilities.getCellFromGridPane(toFill, j, i);
                cell.setVisible(false);
            }
        }
        for(int i = 0; i < 2 && temp > 0; i++)
        {
            for(int j = 0; j < 2 && temp > 0; j++)
            {
                Pane cell = (Pane) GUIUtilities.getCellFromGridPane(toFill, j, i);
                cell.setVisible(true);
                cell.getChildren().clear();
                cell.getChildren().add(new ImageView(GUIUtilities.getRightColorPath(filler.getStudents().get(temp-1))));
                temp--;
            }
        }
    }

    /**
     * Method update is called by each cloud whenever any of them is updated with new information
     * @param o is the cloud, passed as an object
     */
    @Override
    public void update(Object o)
    {
        Platform.runLater(()->{
            LightCloud cloud = (LightCloud) o;
            switch(cloud.getCloudID())
            {
                case 0:
                    fill(cloud0, cloud);
                    break;
                case 1:
                    fill(cloud1, cloud);
                    break;
                case 2:
                    fill(cloud2, cloud);
                    break;
                case 3:
                    fill(cloud3, cloud);
                    break;
                default:
                    break;
            }
        });


    }
}
