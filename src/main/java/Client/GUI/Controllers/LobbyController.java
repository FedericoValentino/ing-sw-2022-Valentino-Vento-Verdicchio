package Client.GUI.Controllers;

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


    public void initialize()
    {
        playersChoice.getItems().addAll(2,3,4);
        playersChoice.setValue(2);

        difficultyChoice.getItems().addAll(true,false);
        difficultyChoice.setValue(true);

    }

    public void onClickTryConnection(ActionEvent actionEvent) throws IOException, InterruptedException {
        GameMode gm = new GameMode();
        gm.setMaxPlayers(playersChoice.getValue());

        while(gm.getMaxPlayers() > 4 || gm.getMaxPlayers() < 2)
        {
            /*controllo da fare?*/
        }
        gm.setExpertGame(difficultyChoice.getValue());
        guiMainStarter.getClientGUI().getServerConnection().getOut().writeObject(gm);
        guiMainStarter.getClientGUI().getServerConnection().getOut().flush();
        guiMainStarter.getClientGUI().getServerConnection().getOut().reset();

        //se corretta la connessione carico una schermata di caricamento

        //watinig
        String path="/GUI/Controllers/Waiting.fxml";
        FXMLLoader loader =loadNewScreen(path,actionEvent);

        System.out.println("Waiting SCREEN");
        WaitingController controller = loader.getController();
        controller.setGuiMainStarter(this.guiMainStarter);
        controller.setActionEvent(actionEvent);

        //guiMainStarter.getClientGUI().resetSetuPHandlerAnswerID();

        //mi metto in pausa finché non arriva il wizard choice
    }

}
