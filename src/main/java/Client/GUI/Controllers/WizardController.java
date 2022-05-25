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

    /**It's call every time logiController.fxml is load as the new scene
     * In this method I set the initial value of the all the radio button
     * **/
    public void initialize()
    {
        rb1.setSelected(true);
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
    }

    /**In this method I set the opacity as 0.3 and then if a wizard is avaiable i set it to 1**/
    public void updateOpacity()
    {
        available= GuiMainStarter.getClientGUI().getAvailable();

        for(Wizard w: available)
        {
            System.out.println("Wizard av "+w);
        }
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
            System.out.println("wiz ava "+w);
            for(Pane p: paneWizard)
            {
                if(w.toString().toLowerCase().equals(p.toString()))
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
       Wizard wizardTemp=null;
        int wizTemp=0;
        if(group.getSelectedToggle()==rb1){wizardTemp=Wizard.DRUID;}
        else if(group.getSelectedToggle()==rb2)
        {wizardTemp=Wizard.WITCH;;}
        else if(group.getSelectedToggle()==rb3)
        {wizardTemp=Wizard.LORD;}
        else if(group.getSelectedToggle()==rb4)
        {wizardTemp=Wizard.SENSEI;}

        updateOpacity();

        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(
                new SerializedMessage(new WizardChoice(wizardTemp)));

    }
}
