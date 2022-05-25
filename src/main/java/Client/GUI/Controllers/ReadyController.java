package Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ReadyController extends Controller{
    @FXML public Pane tickPane;
    @FXML public ImageView imageTick;

    public void onClickReadyButton(ActionEvent actionEvent)
    {
        String path="Client/GUI/Images/tick.png";
        ImageView nI=new ImageView(path);
        //imageTick.setImage(nI.getImage());

        nI.setFitHeight(30);
        nI.setFitWidth(30);
        tickPane.getChildren().clear();
        tickPane.getChildren().add(nI);

        //invio ready da aggiungere
    }

}
