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
    //future implementation of a semaphor
    private boolean hasData;

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

        while(gm.getMaxPlayers() > 4 || gm.getMaxPlayers() < 2)
        {
            /*controllo da fare?*/
        }
        gm.setExpertGame(difficultyChoice.getValue());
        guiMainStarter.getClientGUI().getServerConnection().getOut().writeObject(gm);
        guiMainStarter.getClientGUI().getServerConnection().getOut().flush();
        guiMainStarter.getClientGUI().getServerConnection().getOut().reset();

        //se corretta la connessione carico una schermata di caricamento
        showNextPane(actionEvent);
    }
    /**This method change the screen. The new screen is choose due too the value of getSetuPHandlerAnswerID()
     * It set the current view as waiting.fxml and then run the waitingAndShow method in waitingController
     * There is also a call to setActionEvent to have a reference to the stage in the waiting controller
     * **/
    public void showNextPane(ActionEvent actionEvent) throws InterruptedException, IOException {
        Thread currThread=Thread.currentThread();
        currThread.sleep(1000);
        System.out.println("Sto aspettando");

        String path="/GUI/Controllers/Waiting.fxml";
        FXMLLoader loader =loadNewScreen(path,actionEvent);

        WaitingController controller = loader.getController();
        controller.setGuiMainStarter(this.guiMainStarter);
        controller.setActionEvent(actionEvent);
        System.out.println("Passo a waiting and show");

        System.out.println("stage: "+guiMainStarter.getMainStage());

        currThread=Thread.currentThread();
        System.out.println("Cutrr thre1"+ currThread);
       /* hasData = false;
        while(!hasData)
            currThread.sleep(1000);

        hasData = false*/
        Platform.runLater(controller.waitingAndShow());
        System.out.println("Cutrr tre2"+ currThread);
        currThread=Thread.currentThread();
        currThread.sleep(1000);

    }
/*
    void notifyView(Object o){
                data = o;
        hasData = true
        thread.notify()
    }*/
}
