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

    @FXML public TextField nickname;
    @FXML public TextField IP;
    @FXML public TextField Port;
    @FXML public Button TryConn;


/**It's call every time logiController.fxml is load as the new scene
 * In this method I set the initial value of the team choice that we can select**/
    public void initialize()
    {
        TryConn.setOnMouseClicked(this::onClickTryConnection);
    }
/**This method send all the attributes for the construction of the connection with the server **/
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
