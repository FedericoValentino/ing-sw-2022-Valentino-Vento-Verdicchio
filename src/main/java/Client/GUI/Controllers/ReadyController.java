package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.ReadyStatus;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ReadyController extends Controller{
    @FXML private Pane tickPane;

    public void onClickReadyButton()
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
