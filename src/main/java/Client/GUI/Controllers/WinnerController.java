package Client.GUI.Controllers;


import Client.GUI.GuiMainStarter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class WinnerController extends Controller
{
    public Text title1;
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

    public void setup(String text)
    {
        BackToMenu.setOnMouseClicked(this::OnClickMenu);
        title1.setText(text);
    }
}