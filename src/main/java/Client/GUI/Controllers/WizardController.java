package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.WizardChoice;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.boards.token.Wizard;

import java.util.ArrayList;

public class WizardController extends Controller{

    @FXML private RadioButton rb1;
    @FXML private RadioButton rb2;
    @FXML private RadioButton rb3;
    @FXML private RadioButton rb4;

    @FXML private Pane druid;
    @FXML private Pane witch;
    @FXML private Pane lord;
    @FXML private Pane sensei;

    @FXML private Text alreadyChoose1;
    @FXML private Text alreadyChoose2;
    @FXML private Text alreadyChoose3;
    @FXML private Text alreadyChoose4;

    private ArrayList<Pane> paneWizard=new ArrayList<Pane>();
    private ArrayList<Text> textAvaiable=new ArrayList<>();
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

    public void onClickRb1() {
        rb1.setSelected(true);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(false);
        System.out.println(group.getSelectedToggle());
    }
    public void onClickRb2() {
        rb1.setSelected(false);
        rb2.setSelected(true);
        rb3.setSelected(false);
        rb4.setSelected(false);
    }
    public void onClickRb3() {
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(true);
        rb4.setSelected(false);
    }
    public void onClickRb4() {
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(true);
    }


    public void onClickSendChoice() {
        System.out.println("Entrato in onclickSendChoice del wizard");
        Wizard wizardTemp=null;

        if(group.getSelectedToggle()==rb1){wizardTemp=Wizard.DRUID;}
        else if(group.getSelectedToggle()==rb2)
        {wizardTemp=Wizard.WITCH;}
        else if(group.getSelectedToggle()==rb3)
        {wizardTemp=Wizard.LORD;}
        else if(group.getSelectedToggle()==rb4)
        {wizardTemp=Wizard.SENSEI;}

        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(
                new SerializedMessage(new WizardChoice(wizardTemp)));

    }

    /**In this method I set the opacity as 0.3 and then if a wizard is avaiable i set it to 1**/
    public void updateOpacity(ArrayList<Wizard> available)
    {
        paneWizard.add(druid);
        paneWizard.add(sensei);
        paneWizard.add(lord);
        paneWizard.add(witch);

        for(Pane p: paneWizard)
        {
            p.setOpacity(0.3);
        }


        //cerco se ci sono maghi avaiable li metto a opacità 1
        textAvaiable.add(alreadyChoose1);
        textAvaiable.get(0).setVisible(true);
        textAvaiable.add(alreadyChoose2);
        textAvaiable.get(1).setVisible(true);
        textAvaiable.add(alreadyChoose3);
        textAvaiable.get(2).setVisible(true);
        textAvaiable.add(alreadyChoose4);
        textAvaiable.get(3).setVisible(true);

        //vado a calcolare quale mago non è già stato scelto e vado a togliere il print di mago già scelto e setto l'opacità
        // delle carte a 1
        for(Wizard w : available)
        {

            for(Pane p: paneWizard)
            {
                if(w.toString().toLowerCase().equals(p.getId()))
                {
                    p.setOpacity(1);
                    textAvaiable.get(w.ordinal()).setVisible(false);

                }
            }
        }
    }

}
