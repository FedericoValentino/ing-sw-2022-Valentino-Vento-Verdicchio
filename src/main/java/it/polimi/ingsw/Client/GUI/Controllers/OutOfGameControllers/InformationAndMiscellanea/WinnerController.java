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

/**
 * Handles the case of a team winning or of a game ending in a tie
 */
public class WinnerController extends Controller
{
    @FXML private Text title1;
    @FXML private Button CloseGame;


    /**
     * This method closes the game window
     * @param event click on disconnect button
     */
    public void OnClickQuit(MouseEvent event)
    {
       Platform.exit();
       System.exit(0);
    }


    /**
     * This method initializes the text of "match ended" and links the "closeGameButton" to the specific method needed to handle
     * the click
     * @param text has the text to show on the screen
     */
    public void setup(String text)
    {
        CloseGame.setOnMouseClicked(this::OnClickQuit);
        title1.setText(text);
    }
}