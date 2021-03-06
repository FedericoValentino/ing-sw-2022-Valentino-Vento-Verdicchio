package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Abstract class from which all the GUI controllers inherit the reference to the gui
 */
public abstract class Controller {

    /**
     * That's the reference to the gui *
     */
    protected static GuiMainStarter guiMainStarter;


    /**
     * This function takes the path of the screen that must be loaded and tries to load it into the FMLLoader object (called "loader");
     * then replaces the current scene with the loaded one.
     *
     * @param path is the path that must be load by the parameter loader
     * @return loader, the FXMLLoader object
     */
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

    public void setGuiMainStarter(GuiMainStarter gms)
    {
        guiMainStarter=gms;
    }
}
