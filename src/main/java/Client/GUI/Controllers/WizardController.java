package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.WizardChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.boards.token.Wizard;

import java.util.ArrayList;

public class WizardController extends Controller{

    @FXML public RadioButton rb1;
    @FXML public RadioButton rb2;
    @FXML public RadioButton rb3;
    @FXML public RadioButton rb4;
    @FXML public Button sendChoice;

    @FXML public Text Druid;
    @FXML public Text Witch;
    @FXML public Text Lord;
    @FXML public Text Sensei;

    @FXML public Pane druid;
    @FXML public Pane witch;
    @FXML public Pane lord;
    @FXML public Pane sensei;

    private ArrayList<Wizard> available=new ArrayList<Wizard>();
    private ArrayList<Pane> paneWizard=new ArrayList<Pane>();
    ToggleGroup group = new ToggleGroup();

    public void initialize()
    {
        rb1.setSelected(true);
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
    }
    public void setOpacityStart()
    {
        available= this.guiMainStarter.getClientGUI().getAvailable();
        paneWizard.add(druid);
        paneWizard.add(sensei);
        paneWizard.add(lord);
        paneWizard.add(witch);

        for(Pane p: paneWizard)
        {
            p.setOpacity(0.3);
        }

        //cerco se ci sono maghi avaiable li metto a opacità 1
        for(Wizard w : available)
        {
            for(Pane p: paneWizard)
            {
                if(w.toString().toLowerCase()==p.toString())
                {
                    p.setOpacity(1);
                }
            }

        }
    }

    public void onClickRb1(ActionEvent actionEvent) {
        rb1.setSelected(true);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(false);
        System.out.println(group.getSelectedToggle());
    }
    public void onClickRb2(ActionEvent actionEvent) {
        rb1.setSelected(false);
        rb2.setSelected(true);
        rb3.setSelected(false);
        rb4.setSelected(false);
    }
    public void onClickRb3(ActionEvent actionEvent) {
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(true);
        rb4.setSelected(false);
    }
    public void onClickRb4(ActionEvent actionEvent) {
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(true);
    }


    public void onClickSendChoice(ActionEvent actionEvent) throws InterruptedException {
        System.out.println("Entrato in onclickSendChoice del wizard");
        String wizardTemp = null;
        if(group.getSelectedToggle()==rb1){wizardTemp=druid.toString().toUpperCase();}
        else if(group.getSelectedToggle()==rb2)
        {wizardTemp=witch.toString().toUpperCase();}
        else if(group.getSelectedToggle()==rb3)
        {wizardTemp=lord.toString().toUpperCase();}
        else if(group.getSelectedToggle()==rb4)
        {wizardTemp=sensei.toString().toUpperCase();}

        if(!available.contains(wizardTemp))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Error");
            alert.setHeaderText("Error!");
            alert.setContentText("You have chosen one of the wizard that's already choose");
        }
        else
        {
            guiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new WizardChoice(Wizard.valueOf(wizardTemp))));

            while(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==0)
            {
                System.out.println("Sto aspettando 1000");
                Thread currThread=Thread.currentThread();
                currThread.sleep(1000);
            }
            if(guiMainStarter.getClientGUI().getSetuPHandlerAnswerID()==1)
            {

            }
        }
    }
}