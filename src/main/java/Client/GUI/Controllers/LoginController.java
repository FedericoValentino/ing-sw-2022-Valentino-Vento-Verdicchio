package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.boards.token.ColTow;


import java.io.IOException;

public class LoginController extends Controller{

    @FXML public TextField nickname;
    @FXML public TextField IP;
    @FXML public TextField Port;
    @FXML public Button TryConn;

    private int team;

/**It's call every time logiController.fxml is load as the new scene
 * In this method I set the initial value of the team choice that we can select**/
    public void initialize()
    {

    }
/**This method send all the attributes for the construction of the connection with the server **/
    public void onClickTryConnection(ActionEvent actionEvent) throws IOException{

        GuiMainStarter.getClientGUI().setServerConnection(nickname.getText(), IP.getText());
        /*da cercare di implementare il caso in cui non ci siano server attivi per quella connesione lì
        * perché non voglio che stia fermo li a caso nel login senza capire che deve cambiare parametri*/

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




    /**
 * This method change the screen. The new screen is choose due too the value of getSetuPHandlerAnswerID()
 * If it's 0 we wait, if it's 1 we load the lobby.fxml, if 2 we load Wizard.fxml and else Waiting.fxml
 * **/

    /*
    public void showNextPane(ActionEvent actionEvent) throws IOException, InterruptedException {
        //do il tempo al thread di vedere se nel clientGUI è arrivato un messaggio
        if(this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==0)
        {
            Thread currThread=Thread.currentThread();
            currThread.sleep(500);
            System.out.println("Sto aspettando 500");
        }
        //adesso faccio una serie di if per vedere se è il primo, uno nel mezzo o l'ultimo player a joinare
        if(this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==1)
        {
            String path="/GUI/Controllers/Lobby.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEvent);
            LobbyController controller = loader.getController();
            controller.setGuiMainStarter(this.guiMainStarter);

            System.out.println("FineShowPane");

        }
        else if(this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==2)
        {

            //wizard
            String path="/GUI/Controllers/Wizard.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEvent);
                WizardController controller = loader.getController();
            controller.setGuiMainStarter(this.guiMainStarter);
            controller.setOpacityStart();

            System.out.println("Wizard Choice");

            //guiMainStarter.getClientGUI().resetSetuPHandlerAnswerID();
        }
        else
        {
            this.guiMainStarter.getClientGUI().setSetuPHandlerAnswerID(5);
            String path="/GUI/Controllers/Waiting.fxml";
            FXMLLoader loader =loadNewScreen(path,actionEvent);

            WaitingController controller = loader.getController();
            controller.setGuiMainStarter(this.guiMainStarter);
            controller.setActionEvent(actionEvent);
            System.out.println("Passo a waiting and show");
            controller.waitingAndShow();
        }
    }

*/
}
