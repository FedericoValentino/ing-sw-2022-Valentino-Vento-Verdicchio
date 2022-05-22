package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


import java.io.IOException;

public class LoginController extends Controller{

    @FXML public TextField nickname;
    @FXML public TextField IP;
    @FXML public TextField Port;
    @FXML public Button TryConn;
    @FXML public ChoiceBox <String>teamChoice;


    public void initialize()
    {
        teamChoice.getItems().addAll("Grey","White","Black");
        teamChoice.setValue("Black");
    }

    public void onClickTryConnection(ActionEvent actionEvent) throws IOException {
        //se non è il primo prendo i parametri e mando la connection
        //se è il primo devo salvare i messaggi e caricare la seconda schermata

        guiMainStarter.getClientGUI().setServerConnection(nickname.toString(), 1, IP.getText());
        guiMainStarter.getClientGUI().connection();

        showNextPane(actionEvent);
    }

    public void showNextPane(ActionEvent actionEventc) throws IOException {
        if(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==0)
        {
            String path="/GUI/Controllers/Lobby.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEventc);
            LobbyController controller = loader.getController();
            controller.setGuiMainStarter(guiMainStarter);
            System.out.println("FineShowPane");
        }
    }


}
