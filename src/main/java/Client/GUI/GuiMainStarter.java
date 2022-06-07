package Client.GUI;
import Client.GUI.Controllers.IntroController;
import Client.LightView.LightView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.*;

public class GuiMainStarter extends Application {
    private static Stage mainStage;
    private static ClientGUI clientGUI;
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public static void main(){
        launch();
    }

    /**It's a method to get my reference to CLientGUI, otherwise I'll lose it**/
    public static void setClientGUI(ClientGUI clientGUI)
    {
        GuiMainStarter.clientGUI=clientGUI;
    }
    public static ClientGUI getClientGUI()
    {
        return clientGUI;
    }
    public ExecutorService getExecutor()
    {
        return executor;
    }
    public static Stage getMainStage()
    {
        return mainStage;
    }


    /** Start is the method that run when it's called GuiMainStarter.launch() in main
     * This method set all the initial stage and load the intro.fxml file that's the first scene we use
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
        IntroController controller = loader.getController();
        controller.setGuiMainStarter(this);
    }

}

