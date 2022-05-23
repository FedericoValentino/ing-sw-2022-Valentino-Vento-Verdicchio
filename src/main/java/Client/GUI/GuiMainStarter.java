package Client.GUI;
import Client.GUI.Controllers.IntroController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.concurrent.*;

public class GuiMainStarter extends Application {
    private Stage mainStage;
    protected static ClientGUI ClientGUI;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(){
        launch();
    }

/**It's a method to get my reference to CLientGUI, otherwise I'll lose it**/
    public static void setClientGUI(ClientGUI clientGUI)
    {
        GuiMainStarter.ClientGUI=clientGUI;
    }
    public ClientGUI getClientGUI()
    {
        return ClientGUI;
    }
    public ExecutorService getExecutor()
    {
        return executor;
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.mainStage=stage;
        //this.mainStage.setFullScreen(true);
        this.mainStage.setTitle("Eryantis");
        this.mainStage.setMinHeight(900);
        this.mainStage.setMinWidth(1600);
        //this.mainStage.setMaximized(true);
        this.mainStage.getIcons().add(new Image("/GUI/Images/eriantysScatola.png"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Controllers/Intro.fxml"));

        this.mainStage.setScene(new Scene(loader.load()));
        this.mainStage.show();

        IntroController controller = loader.getController();
        controller.setGuiMainStarter(this);
    }




    public void callWaitService(Callable<Boolean> condition, Runnable viewToLoad, int delay){
        ScheduledExecutorService waitService = Executors.newSingleThreadScheduledExecutor();
        waitService.scheduleAtFixedRate(() -> {
            try {
                if(condition.call()){
                    Platform.runLater(viewToLoad);
                    waitService.shutdown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, delay, delay, TimeUnit.MILLISECONDS);
    }

}

