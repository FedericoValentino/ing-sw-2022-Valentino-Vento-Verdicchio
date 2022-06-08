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

    /**This function is called when the TryConnection button is pressed.
     * Firstly this method create a new GameMode object and sets the number of player and the boolean of the expertGame
     * chose by the first player.
     * Then it sends the gameMode to the server using the combination of writeObject, flush and reset functions.
     * Finally, it uses the changeScene method of the ClientGui class to replace the current scene with the Waiting scene.
     * The location of the new screen it's stored in the variable called path.
     */
    public void onClickTryConnection() throws IOException {
        GameMode gm = new GameMode();
        gm.setMaxPlayers(playersChoice.getValue());
        gm.setExpertGame(difficultyChoice.getValue());

        GuiMainStarter.getClientGUI().getServerConnection().getOut().writeObject(gm);
        GuiMainStarter.getClientGUI().getServerConnection().getOut().flush();
        GuiMainStarter.getClientGUI().getServerConnection().getOut().reset();

        String path= "/Client/GUI/Controllers/Waiting.fxml";
        FXMLLoader load = new FXMLLoader(getClass().getResource(path));
        GuiMainStarter.getClientGUI().changeScene(load);
    }
}
