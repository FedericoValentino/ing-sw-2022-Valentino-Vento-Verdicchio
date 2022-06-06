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
    private LightView view;

    public void setup(AnchorPane anchorPane, LightView view) throws IOException {
        this.view = view;
        //Cloud Controller Setup
        String cloudPanePath = "/Client/GUI/Controllers/Clouds.fxml";
        FXMLLoader cloudLoader = new FXMLLoader(getClass().getResource(cloudPanePath));
        cloudsAnchorPane.getChildren().clear();
        cloudsAnchorPane.getChildren().add(cloudLoader.load());
        CloudController cloudController = cloudLoader.getController();
        cloudController.setGuiMainStarter(guiMainStarter);
        cloudController.setup(view.getCurrentClouds(), view.getCurrentTurnState());

        //Islands  initial setup
        update(view.getCurrentIslands());

    }

    @Override
    public void update(Object o){
        LightIslands lightIslands = (LightIslands) o;

        //Islands  initial setup
        double deltaTheta = 2*Math.PI/lightIslands.getIslands().size();
        double startingAngle = 0;
        for(int i = 1; i <= lightIslands.getIslands().size(); i++)
        {
            int finalI = i;
            mainIslandBoard.getChildren().removeIf(node -> node.getId().equals("is"+ finalI));
        }
        int id = 0;
        for(LightIsland island : lightIslands.getIslands())
        {
            String islandPath = "/Client/GUI/Controllers/Island.fxml";
            FXMLLoader islandLoader = new FXMLLoader(getClass().getResource(islandPath));
            islands.removeAll(islands);
            islandControllers.removeAll((islandControllers));
            AnchorPane islandContainer = new AnchorPane();
            islandContainer.getChildren().clear();
            try
            {
                islandContainer.getChildren().add(islandLoader.load());
            }
            catch(IOException e)
            {

            }
            islands.add(islandContainer);
            IslandController islandController = islandLoader.getController();
            islandController.setup(island, id, view.getCurrentMotherNature(), lightIslands.getIslands().size());
            islandContainer.setLayoutX(350 + 75 * Math.cos(startingAngle) + (radius * Math.cos(startingAngle)));
            islandContainer.setLayoutY(275 - (radius * Math.sin(startingAngle)));
            startingAngle += deltaTheta;
            mainIslandBoard.getChildren().add(islandContainer);
            id++;
        }

    }
}
