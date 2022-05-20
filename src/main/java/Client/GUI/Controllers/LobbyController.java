package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class LobbyController extends Controller{


    @FXML public ChoiceBox <Integer>playersChoice;
    @FXML public ChoiceBox <Boolean>difficultyChoice;
    @FXML public Button SendChoice;


    public void initialize()
    {
        playersChoice.getItems().addAll(2,3,4);
        playersChoice.setValue(2);

        difficultyChoice.getItems().addAll(true,false);
        //playersChoice.setValue(true);

    }

    public void onClickTryConnection(ActionEvent actionEvent) {
    }

    public void onClickBack(ActionEvent actionEvent) {
    }
}
