package Client.GUI.Controllers;

import Client.GUI.ClientGUI;
import Client.GUI.GuiMainStarter;
import Client.Messages.SetupMessages.GameMode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class LobbyController extends Controller{


    @FXML public ChoiceBox <Integer>playersChoice;
    @FXML public ChoiceBox <Boolean>difficultyChoice;
    @FXML public Button SendChoice;


    /**It's call every time logiController.fxml is load as the new scene
     * In this method I set the initial value of the player choice and the difficulty that we can select**/
    public void initialize()
    {
        playersChoice.getItems().addAll(2,3,4);
        playersChoice.setValue(2);
        difficultyChoice.getItems().addAll(true,false);
        difficultyChoice.setValue(true);
    }
    /**This method send all the attributes for the settings of the game, from only the fist player that become the host**/
    public void onClickTryConnection(ActionEvent actionEvent) throws IOException, InterruptedException {
        GameMode gm = new GameMode();
        gm.setMaxPlayers(playersChoice.getValue());
        gm.setExpertGame(difficultyChoice.getValue());

        guiMainStarter.getClientGUI().getServerConnection().getOut().writeObject(gm);
        guiMainStarter.getClientGUI().getServerConnection().getOut().flush();
        guiMainStarter.getClientGUI().getServerConnection().getOut().reset();

        String path= "/Client/GUI/Controllers/Waiting.fxml";
        FXMLLoader load = new FXMLLoader(getClass().getResource(path));
        GuiMainStarter.getClientGUI().changeScene(load);
    }
}
