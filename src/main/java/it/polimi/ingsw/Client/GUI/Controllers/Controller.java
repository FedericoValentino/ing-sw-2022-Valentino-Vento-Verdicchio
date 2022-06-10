package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class Controller {

    /**that's the link to the gui **/
    protected static GuiMainStarter guiMainStarter;


    /**
     * This function take the path of the screen that must be load and try to load it into the FMLLoader object (called "loader") and then
     * replace the current scene with the loaded one.
     *
     * @param path is the path that must be load by the parameter loader
     * @return loader, the FXMLLoader object
     */
    /*Funzione che fa il load di una nuova schermata associandoci il controller specifico*/
    public FXMLLoader loadNewScreen(String path){

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


    //da capire se viene usata o meno che in caso la togliamo
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
