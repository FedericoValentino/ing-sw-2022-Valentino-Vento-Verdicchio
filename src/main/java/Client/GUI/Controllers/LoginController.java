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

    public void onClickTryConnection(ActionEvent actionEvent) throws IOException, InterruptedException {

        guiMainStarter.getClientGUI().setServerConnection(nickname.toString(), 1, IP.getText());

       showNextPane(actionEvent);

    }

    public void showNextPane(ActionEvent actionEvent) throws IOException, InterruptedException {
        while(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==0)
        {
            Thread currThread=Thread.currentThread();
            currThread.sleep(500);
            System.out.println("Sto aspettando 500");
        }
        if(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==1)
        {
            String path="/GUI/Controllers/Lobby.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEvent);
            LobbyController controller = loader.getController();
            controller.setGuiMainStarter(guiMainStarter);
            System.out.println("FineShowPane");

            guiMainStarter.getClientGUI().resetSetuPHandlerAnswerID();
        }
        else if(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==2)
        {
            String path="/GUI/Controllers/WizardChoice.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEvent);
            WizardController controller = loader.getController();
            controller.setGuiMainStarter(this.getGuiMainStarter());
            System.out.println("Wizard Choice");

            guiMainStarter.getClientGUI().resetSetuPHandlerAnswerID();
        }
    }


}
