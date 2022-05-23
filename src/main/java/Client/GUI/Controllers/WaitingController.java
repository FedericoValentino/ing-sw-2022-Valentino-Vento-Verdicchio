package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WaitingController extends Controller{
    private ActionEvent actionEvent;

    public void setActionEvent(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public Runnable waitingAndShow() throws IOException, InterruptedException {

        //mi metto in pausa finché non arriva il wizard choice
        System.out.println("Waiting SCREEN");
        while(true)
        {
            System.out.println("Waiting SCREEN-->while");
            if(this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==0 ||this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==1)
            {
                System.out.println("Sto aspettando 2000 nel waiting chiamato da waiting controller");
                Thread currThread=Thread.currentThread();
                currThread.sleep(2000);
            }
            else if(this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==2)
            {
                System.out.println("Entrato in waiting and show e nel id=2");

                String path="/GUI/Controllers/WizardChoice.fxml";
                FXMLLoader loader =loadNewScreen(path,actionEvent);
                WizardController controller = loader.getController();
                controller.setGuiMainStarter(this.guiMainStarter);
               // controller.setOpacityStart();

                System.out.println("Wizard Choice");

                guiMainStarter.getClientGUI().setSetuPHandlerAnswerID(0);
            }

        }
    }
}
