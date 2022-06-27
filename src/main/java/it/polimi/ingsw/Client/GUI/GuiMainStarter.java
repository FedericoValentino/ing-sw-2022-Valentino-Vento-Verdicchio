package it.polimi.ingsw.Client.GUI;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.IntroController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GuiMainStarter extends Application {
    private static Stage mainStage;
    private static ClientGUI clientGUI;


    public static void main(){
        launch();
    }

    /**It's a method to get my reference to CLientGUI, otherwise I'll lose it and I wont be able to replace it with the
     * next one.
     * **/
    public static void setClientGUI(ClientGUI clientGUI)
    {
        GuiMainStarter.clientGUI=clientGUI;
    }
    public static ClientGUI getClientGUI()
    {
        return clientGUI;
    }
    public static Stage getMainStage()
    {
        return mainStage;
    }


    /** This method it's called by launch(); in GuiMainStarter.main().
     * In particular sets all the initial stage and load the intro.fxml file that's the first scene to use.
     * **/
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
        mainStage=stage;
        //mainStage.setFullScreen(true);
        mainStage.setAlwaysOnTop(true);
        mainStage.setTitle("Eryantis");
        mainStage.setMinHeight(900);
        mainStage.setMinWidth(1600);
        mainStage.getIcons().add(new Image("/Client/GUI/Images/eriantysScatola.png"));
        mainStage.setScene(new Scene(loader.load()));
        mainStage.show();
        mainStage.setAlwaysOnTop(false);
        mainStage.setOnCloseRequest((Event) ->
        {
            System.exit(0);
            Platform.exit();
        });
        IntroController controller = loader.getController();
        controller.setGuiMainStarter(this);
    }

}

