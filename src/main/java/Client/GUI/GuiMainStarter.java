package Client.GUI;
import Client.GUI.Controllers.IntroController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GuiMainStarter extends Application {
    private Stage mainStage;

    public static void main(){
        launch();
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

        this.mainStage.setScene(new Scene( (AnchorPane)loader.load()));
        this.mainStage.show();


/*
        FXMLLoader fxmlLoader = new FXMLLoader(getResource("Gui/Controllers/MainBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
*/
    }
}

