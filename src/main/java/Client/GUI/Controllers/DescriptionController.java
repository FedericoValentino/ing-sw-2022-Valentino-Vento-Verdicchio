package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DescriptionController extends Controller{
    @FXML public Button buttonBack;

    public void onClickBackToIntro(ActionEvent actionEvent) throws IOException {
        String path="/GUI/Controllers/Intro.fxml";
        loadNewScreen(path,actionEvent);
    }
}
