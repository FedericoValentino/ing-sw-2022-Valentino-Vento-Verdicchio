package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;
//
import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightBoards.LightCloud;
import it.polimi.ingsw.Client.LightView.LightTurnState;
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


public class CloudController extends Controller implements ObserverLightView
{
    private LightTurnState turn;

    @FXML private GridPane clouds;
    @FXML private GridPane cloud0;
    @FXML private GridPane cloud1;
    @FXML private GridPane cloud2;
    @FXML private GridPane cloud3;


    /**This method is called on the click of the cloud and if we are in the planning phase it sends to the server the
     * draw from pouch message and if we are in the action phase it sends a message with the cloud selected to the server
     * @param event is the click on the cloud
     */
    public void CloudSelection(MouseEvent event)
    {
        if(turn.getGamePhase() == GamePhase.PLANNING)
        {
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new DrawFromPouch(Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", "")))));
        }
        else if(turn.getGamePhase() == GamePhase.ACTION)
        {
            int cloudIndex = Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", ""));
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ChooseCloud(cloudIndex)));
        }
    }

    /**This method show the clouds and add the observer to the cloudsArray
     * @param cloudsArray is used to show the right number of clouds
     * @param turnState is used to save its value into turn attribute
     */
    public void setup(LightCloud[] cloudsArray, LightTurnState turnState)
    {
        turn = turnState;
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


    /**This method empty all the cloud and then refill it with the students ( by calling also the getRightColorPath method)
     * @param fillable is the gridpane of the cloud
     * @param filler is the reference to the cloud that contains the color of the students to place in it
     */
    public void fill(GridPane fillable, LightCloud filler)
    {
        int temp = filler.getStudents().size();
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                Pane cell = (Pane) GUIUtilities.getCellFromGridPane(fillable, j, i);
                cell.setVisible(false);
            }
        }
        for(int i = 0; i < 2 && temp > 0; i++)
        {
            for(int j = 0; j < 2 && temp > 0; j++)
            {
                Pane cell = (Pane) GUIUtilities.getCellFromGridPane(fillable, j, i);
                cell.setVisible(true);
                cell.getChildren().clear();
                cell.getChildren().add(new ImageView(GUIUtilities.getRightColorPath(filler.getStudents().get(temp-1))));
                temp--;
            }
        }
    }

    /**This method update the cloud and according to the number of the cloud to fill it calls the fill method
     * @param o is the object that is changed
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
