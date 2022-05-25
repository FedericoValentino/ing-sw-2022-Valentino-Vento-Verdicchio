package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WaitingController extends Controller{

    private ActionEvent actionEvent;

    public void setActionEvent(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    /**this method change the screen, loading the wizardChoice if the this.guiMainStarter.getClientGUI().getSetuPHandlerAnswerID() is 0 or 1**/
/*    public Runnable waitingAndShow() throws IOException, InterruptedException {

        //mi metto in pausa finché non arriva il wizard choice
        System.out.println("Waiting SCREEN");
        Thread currThread=Thread.currentThread();
        System.out.println("Cutrr thre"+ currThread);

            System.out.println("Waiting SCREEN-->while");
            while(this.guiMainStarter.getClientGUI().getSetupHandlerAnswerID()==0 ||this.guiMainStarter.getClientGUI().getSetupHandlerAnswerID()==1)
            {
                System.out.println("ciao non funzionerò maiiii" +this.guiMainStarter.getClientGUI().getSetupHandlerAnswerID());
                System.out.println("Sto aspettando 2000 nel waiting chiamato da waiting controller");
                currThread=Thread.currentThread();
                currThread.sleep(2000);
            }

                System.out.println("Entrato in waiting and show e nel id=2");

                String path="/GUI/Controllers/Wizard.fxml";
                FXMLLoader loader =loadNewScreen(path,this.actionEvent);
                WizardController controller = loader.getController();
                controller.setGuiMainStarter(this.guiMainStarter);
               // controller.setOpacityStart();

                System.out.println("Wizard Choice");

                guiMainStarter.getClientGUI().setSetupHandlerAnswerID(0);



    return null;}*/
}
