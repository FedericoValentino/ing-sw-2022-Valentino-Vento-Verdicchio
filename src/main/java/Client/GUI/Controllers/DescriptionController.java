package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DescriptionController extends Controller{

    @FXML public Button buttonBack;

    public void onClickBackToIntro(ActionEvent actionEvent) throws IOException {
        String path= "/Client/GUI/Controllers/Intro.fxml";
        loadNewScreen(path);
    }
}
