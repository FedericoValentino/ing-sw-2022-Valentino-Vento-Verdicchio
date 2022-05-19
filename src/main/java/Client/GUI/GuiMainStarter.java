package Client.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        //stage.setFullScreen(true);
        this.mainStage.setTitle("Eryantis");
        this.mainStage.setMinHeight(900);
        this.mainStage.setMinWidth(1600);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Controllers/MainBoard.fxml"));

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

