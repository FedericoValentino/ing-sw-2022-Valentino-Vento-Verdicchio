package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.io.IOException;

public class LoginController extends Controller{

    @FXML private TextField nickname;
    @FXML private TextField IP;
    //@FXML private TextField Port;
    @FXML private Button TryConn;


    /**
     * This method it's call every time LoginController.fxml is load as the current scene.
     * This method sets the initial value of the team choice selected by the first player.
     * **/
    public void initialize()
    {
        TryConn.setOnMouseClicked(this::onClickTryConnection);
    }

    /**This method is called when the TryConnection button is pressed.
     * Firstly send all the attributes for the constructions of the connection with the server.
     * Secondly it uses the loadNewScreen method, inherited from the abstract class Controller, to replace
     * the current scene with the Waiting scene.
     * The location of the new screen it's stored in the variable called path.
     * Then it also calls the setGuiMainStarter method, inherited from the Controller class, to preserve the reference
     * to the class guiMainStarter.
     */
    public void onClickTryConnection(MouseEvent actionEvent)
    {

        GuiMainStarter.getClientGUI().setServerConnection(nickname.getText(), IP.getText());

        String path= "/Client/GUI/Controllers/Waiting.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);
    }
}
