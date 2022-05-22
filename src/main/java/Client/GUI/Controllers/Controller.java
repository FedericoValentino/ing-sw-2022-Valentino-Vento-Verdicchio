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
    public FXMLLoader loadNewScreen(String path, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        Stage stage=(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene= new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
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
