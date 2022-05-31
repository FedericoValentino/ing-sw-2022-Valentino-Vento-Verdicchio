package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.ServerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class Controller {

    /**that's the link to the gui **/
    protected static GuiMainStarter guiMainStarter;


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

    protected void showError(String title, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(GuiMainStarter.getMainStage());

        alert.setHeaderText(title);
        alert.setContentText(header);

        alert.showAndWait();
    }



    public void setGuiMainStarter(GuiMainStarter gms)
    {
        guiMainStarter=gms;
    }
    public GuiMainStarter getGuiMainStarter()
    {
        return guiMainStarter;
    }
}
