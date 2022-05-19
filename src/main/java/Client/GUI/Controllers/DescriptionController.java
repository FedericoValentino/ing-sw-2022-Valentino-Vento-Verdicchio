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

public class DescriptionController {
    @FXML public Button buttonBack;

    public void onClickBackToIntro(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Controllers/Intro.fxml"));

        Stage stage=(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene= new Scene((Parent) loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
