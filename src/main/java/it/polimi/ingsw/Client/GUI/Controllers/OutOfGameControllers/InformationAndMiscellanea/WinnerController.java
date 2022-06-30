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


    /**This method close the game window
     * @param event click on disconnect button
     */
    public void OnClickQuit(MouseEvent event)
    {
       Platform.exit();
       System.exit(0);
    }


    /**This method initialize the text of "match ended" and link to the close game button the specific method to handle
     * the click on it
     * @param text
     */
    public void setup(String text)
    {
        CloseGame.setOnMouseClicked(this::OnClickQuit);
        title1.setText(text);
    }
}