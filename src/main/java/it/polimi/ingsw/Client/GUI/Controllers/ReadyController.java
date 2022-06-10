package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.ReadyStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ReadyStatus()));
    }

}
