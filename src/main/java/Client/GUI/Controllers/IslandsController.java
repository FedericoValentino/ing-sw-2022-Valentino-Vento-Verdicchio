package Client.GUI.Controllers;

import Client.LightView.LightCloud;
import Client.LightView.LightIslands;
import Client.LightView.LightSchool;
import Client.LightView.LightTurnState;
import Observer.ObserverLightView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.stream.Collectors;

public class IslandsController extends Controller implements ObserverLightView {

    public AnchorPane cloudsAnchorPane;
    public AnchorPane mainIslandBoard;

    public void setup(AnchorPane anchorPane, LightIslands lightIslands, LightCloud[] clouds, LightTurnState turn) throws IOException {
        //Cloud Controller Setup
        String cloudPanePath = "/Client/GUI/Controllers/Clouds.fxml";
        FXMLLoader cloudLoader = new FXMLLoader(getClass().getResource(cloudPanePath));
        cloudsAnchorPane.getChildren().clear();
        cloudsAnchorPane.getChildren().add(cloudLoader.load());
        CloudController cloudController = cloudLoader.getController();
        cloudController.setGuiMainStarter(guiMainStarter);
        cloudController.setup(clouds, turn);






        /*
        lightIslands.addObserverLight(this);
        //Con il mio anchor pane  poi vado a settare la
        this.mainIslandBoard=anchorPane;

        for(int i=0; i<lightIslands.getIslands().size();i++)
        {
            String path="/Client/GUI/Controllers/Island.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            int finalI = i+1;
            AnchorPane isola=(AnchorPane) ((AnchorPane) mainIslandBoard.getChildren().get(0)).getChildren().stream().filter(
                            node -> node.getId().equals("is"+ finalI))
                    .collect(Collectors.toList()).get(0);

            isola.getChildren().clear();
            isola.getChildren().add(loader.getRoot());
            IslandController islandController=loader.getController();
            islandController.setGuiMainStarter(guiMainStarter);
            islandController.randomImage();
        }
        update(lightIslands);
        */
    }

    @Override
    public void update(Object o) {
        LightIslands lightIslands = (LightIslands) o;

    }
}
