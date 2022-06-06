package Client.GUI.Controllers;


import Client.GUI.GuiMainStarter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RejectionController extends Controller
{
    public Button BackToMenu;


    public void reloadMenu(FXMLLoader loader)
    {

        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);
    }

    public void OnClickMenu(MouseEvent event)
    {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
            reloadMenu(loader);
            IntroController intro = loader.getController();
            intro.setGuiMainStarter(guiMainStarter);
        });
    }

    public void setup()
    {
        BackToMenu.setOnMouseClicked(this::OnClickMenu);
    }
}
