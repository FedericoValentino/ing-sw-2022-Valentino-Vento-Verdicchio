package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WaitingController extends Controller{
    private ActionEvent actionEvent;

    public void initialize() throws InterruptedException, IOException {
        while(true)
        {
            if(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==2)
            {
                String path="/GUI/Controllers/WizardChoice.fxml";
                FXMLLoader loader =loadNewScreen(path,actionEvent);
                WizardController controller = loader.getController();
                controller.setGuiMainStarter(guiMainStarter);
                System.out.println("Wizard Choice");

                guiMainStarter.getClientGUI().resetSetuPHandlerAnswerID();
            }
            else
            {
                System.out.println("Sto aspettando 500");
                Thread currThread=Thread.currentThread();
                currThread.sleep(500);
            }
        }
    }

    public void setActionEvent(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }
}
