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

public class IntroController extends Controller{
    @FXML public Button Start;
    @FXML public Button Close;
    @FXML public Button Story;

    public void oncClickExitButton(ActionEvent actionEvent)
    {
        Stage stage=(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onClickStart(ActionEvent actionEvent) throws IOException {
        String path="/GUI/Controllers/Login.fxml";
        loadNewScreen(path,actionEvent);
    }

    public void onClickStory(ActionEvent actionEvent) throws IOException {
        String path="/GUI/Controllers/Description.fxml";
        loadNewScreen(path,actionEvent);
    }
}
