package it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea;


import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.IntroController;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class WinnerController extends Controller
{
    @FXML private Text title1;
    @FXML private Button CloseGame;


    public void OnClickQuit(MouseEvent event)
    {
       Platform.exit();
       System.exit(0);
    }

    public void setup(String text)
    {
        CloseGame.setOnMouseClicked(this::OnClickQuit);
        title1.setText(text);
    }
}