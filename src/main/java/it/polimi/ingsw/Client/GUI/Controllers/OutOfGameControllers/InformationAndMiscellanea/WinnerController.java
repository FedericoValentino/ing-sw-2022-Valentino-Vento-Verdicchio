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
    @FXML private Button backToMenu;


    public void OnClickMenu(MouseEvent event)
    {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
            GuiMainStarter.getClientGUI().changeScene(loader);
            IntroController intro = loader.getController();
            intro.setGuiMainStarter(guiMainStarter);
        });
    }

    public void setup(String text)
    {
        backToMenu.setOnMouseClicked(this::OnClickMenu);
        title1.setText(text);
    }
}