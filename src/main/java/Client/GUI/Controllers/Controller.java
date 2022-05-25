package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.ServerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    /**that's the link to the gui **/
    protected GuiMainStarter guiMainStarter;


    /*Funzione che fa il load di una nuova schermata associandoci il controller specifico*/
    public FXMLLoader loadNewScreen(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);

        return loader;
    }

    public void setGuiMainStarter(GuiMainStarter gms)
    {
        this.guiMainStarter=gms;
    }
    public GuiMainStarter getGuiMainStarter()
    {
        return guiMainStarter;
    }
}
