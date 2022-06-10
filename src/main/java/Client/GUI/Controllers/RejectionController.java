package Client.GUI.Controllers;


import Client.GUI.GuiMainStarter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class RejectionController extends Controller
{
    public Button BackToMenu;


    public void OnClickMenu(MouseEvent event)
    {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
            GuiMainStarter.getClientGUI().changeScene(loader);
            IntroController intro = loader.getController();
            intro.setGuiMainStarter(guiMainStarter);
        });
    }

    public void setup()
    {
        BackToMenu.setOnMouseClicked(this::OnClickMenu);
    }
}
