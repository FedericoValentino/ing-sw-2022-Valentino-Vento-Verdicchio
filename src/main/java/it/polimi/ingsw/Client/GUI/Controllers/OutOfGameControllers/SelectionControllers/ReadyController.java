package it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.SelectionControllers;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.ReadyStatus;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Handles the "getReady" process after the first setup of the game
 */
public class ReadyController extends Controller {
    @FXML private Pane tickPane;


    /**
     * This method replace the cross of the ReadyController contained in tickPane with a tick (when the button is pressed)
     * and send a ReadyStatus message to the server
     */
    public void onClickReadyButton()
    {
        String path="Client/GUI/Images/tick.png";
        ImageView nI=new ImageView(path);

        nI.setFitHeight(30);
        nI.setFitWidth(30);
        tickPane.getChildren().clear();
        tickPane.getChildren().add(nI);

        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ReadyStatus()));
    }

}
