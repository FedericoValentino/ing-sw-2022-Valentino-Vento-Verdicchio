package Client.GUI.Controllers;

import Client.LightView.*;
import Observer.ObserverLightView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class IslandsController extends Controller implements ObserverLightView {

    public AnchorPane cloudsAnchorPane;
    public AnchorPane mainIslandBoard;
    public ArrayList<AnchorPane> islands = new ArrayList<>();
    public ArrayList<IslandController> islandControllers = new ArrayList<>();
    public final int radius = 250;

    public void setup(AnchorPane anchorPane, LightIslands lightIslands, LightCloud[] clouds, LightTurnState turn) throws IOException {
        //Cloud Controller Setup
        String cloudPanePath = "/Client/GUI/Controllers/Clouds.fxml";
        FXMLLoader cloudLoader = new FXMLLoader(getClass().getResource(cloudPanePath));
        cloudsAnchorPane.getChildren().clear();
        cloudsAnchorPane.getChildren().add(cloudLoader.load());
        CloudController cloudController = cloudLoader.getController();
        cloudController.setGuiMainStarter(guiMainStarter);
        cloudController.setup(clouds, turn);

        //Islands setup
        double deltaTheta = 2*Math.PI/lightIslands.getIslands().size();
        double startingAngle = 0;
        for(int i = 0; i < lightIslands.getIslands().size(); i++)
        {
            int finalI = i;
            mainIslandBoard.getChildren().removeIf(node -> node.getId().equals("is"+ finalI));
        }
        for(LightIsland island : lightIslands.getIslands())
        {
            String islandPath = "/Client/GUI/Controllers/Island.fxml";
            FXMLLoader islandLoader = new FXMLLoader(getClass().getResource(islandPath));
            islands.removeAll(islands);
            islandControllers.removeAll((islandControllers));
            AnchorPane islandContainer = new AnchorPane();
            islandContainer.getChildren().clear();
            islandContainer.getChildren().add(islandLoader.load());
            islands.add(islandContainer);
            IslandController islandController = islandLoader.getController();
            islandController.setup(island);
            islandContainer.setLayoutX(350 + 100 * Math.cos(startingAngle) + (radius * Math.cos(startingAngle)));
            islandContainer.setLayoutY(275 - (radius * Math.sin(startingAngle)));
            startingAngle += deltaTheta;
            mainIslandBoard.getChildren().add(islandContainer);
        }

    }

    @Override
    public void update(Object o) {
        LightIslands lightIslands = (LightIslands) o;

    }
}
