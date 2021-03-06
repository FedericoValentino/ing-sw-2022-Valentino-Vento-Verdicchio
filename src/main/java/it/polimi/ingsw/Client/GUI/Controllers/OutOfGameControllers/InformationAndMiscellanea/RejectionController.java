package it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea;


import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.IntroController;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Handles the case of rejection of the client's connection
 */
public class RejectionController extends Controller
{
    @FXML private Button backToMenu;


    /**
     * This method loads the intro fxml file, then creates the associated controller and then preserves the reference to the
     * guiMainStarter
     * @param event is the click on the return to menu button
     */
    public void OnClickMenu(MouseEvent event)
    {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
            GuiMainStarter.getClientGUI().changeScene(loader);
            IntroController intro = loader.getController();
            intro.setGuiMainStarter(guiMainStarter);
        });
    }

    /**
     * This method add to the backButton the onClickMenu method to come back to the intro scene
     */
    public void setup()
    {
        backToMenu.setOnMouseClicked(this::OnClickMenu);
    }
}
