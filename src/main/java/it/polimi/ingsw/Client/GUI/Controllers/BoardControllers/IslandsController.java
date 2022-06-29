package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.LightView.LightBoards.LightIsland;
import it.polimi.ingsw.Observer.ObserverLightView;
import it.polimi.ingsw.Client.LightView.LightBoards.LightIslands;
import it.polimi.ingsw.Client.LightView.LightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class IslandsController extends Controller implements ObserverLightView {

    @FXML private AnchorPane cloudsAnchorPane;
    @FXML private AnchorPane mainIslandBoard;

    public ArrayList<AnchorPane> islands = new ArrayList<>();
    public ArrayList<IslandController> islandControllers = new ArrayList<>();
    public final int radius = 250;
    private LightView view;

    public void setup(AnchorPane anchorPane, LightView view) throws IOException {
        this.view = view;
        //Cloud Controller setup
        String cloudPanePath = "/Client/GUI/Controllers/Clouds.fxml";
        FXMLLoader cloudLoader = new FXMLLoader(getClass().getResource(cloudPanePath));
        cloudsAnchorPane.getChildren().clear();
        cloudsAnchorPane.getChildren().add(cloudLoader.load());
        CloudController cloudController = cloudLoader.getController();
        cloudController.setGuiMainStarter(guiMainStarter);
        cloudController.setup(view.getCurrentClouds(), view.getCurrentTurnState());

        //Islands  initial setup
        view.getCurrentIslands().addObserverLight(this);
        update(view.getCurrentIslands());


    }

    @Override
    public void update(Object o){
        Platform.runLater(() ->
        {
            LightIslands lightIslands = (LightIslands) o;
            synchronized (lightIslands.getLock())
            {
                //Islands  initial setup
                double deltaTheta = 2 * Math.PI / lightIslands.getIslands().size();
                double startingAngle = 0;
                while (mainIslandBoard.getChildren().removeIf(node -> node.getId().startsWith("is"))) {

                }
                int id = 0;
                for (LightIsland island : lightIslands.getIslands()) {
                    String islandPath = "/Client/GUI/Controllers/Island.fxml";
                    FXMLLoader islandLoader = new FXMLLoader(getClass().getResource(islandPath));
                    islands.clear();
                    islandControllers.clear();
                    AnchorPane islandContainer = new AnchorPane();
                    islandContainer.setId("is" + id);
                    islandContainer.getChildren().clear();
                    try {
                        islandContainer.getChildren().add(islandLoader.load());
                    } catch (IOException e) {

                    }
                    islands.add(islandContainer);
                    IslandController islandController = islandLoader.getController();
                    islandController.setup(island, id, view.getCurrentMotherNature(), lightIslands.getIslands().size(), startingAngle);
                    islandContainer.setLayoutX(350 + 75 * Math.cos(startingAngle) + (radius * Math.cos(startingAngle)));
                    islandContainer.setLayoutY(225 - 25 * Math.sin(startingAngle) - (radius * Math.sin(startingAngle)));
                    startingAngle -= deltaTheta;
                    mainIslandBoard.getChildren().add(islandContainer);
                    id++;
                }
            }
        });

    }
}
