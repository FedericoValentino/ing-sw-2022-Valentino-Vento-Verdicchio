package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.LightIslands;
import Client.LightView.LightPlayer;
import Client.LightView.LightTeam;
import Client.LightView.LightView;
import Observer.ObserverLightView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Player;
import model.Team;

import java.io.IOException;
import java.util.ArrayList;


public class MainBoardController extends Controller {
    @FXML public AnchorPane mainAnchorPane;

    @FXML public AnchorPane otherSchoolAnchorPane;
    @FXML public AnchorPane characterAnchorPane;

    @FXML public AnchorPane islandAnchorPane;
    @FXML public AnchorPane cloudsAnchorPane;
    @FXML public AnchorPane assistantCardAnchorPane;

    @FXML public AnchorPane buttonAreaAnchorPane;
    @FXML public AnchorPane mineSchoolAnchorPane;

    private LightView lightView;


    public void initialSetupIsland(LightIslands lightIslands) throws IOException {
        String path= "/Client/GUI/Controllers/Islands.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        islandAnchorPane.getChildren().clear();
        islandAnchorPane.getChildren().add(loader.load());
        IslandsController contr= loader.getController();
        contr.setup(islandAnchorPane, lightIslands);
    }
    public void initialSetupAssistantCard(ArrayList<LightTeam> teams) {
        String path= "/Client/GUI/Controllers/Assistants.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        AssistantCardsController assistantController= loader.getController();
        String player=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        assistantController.setup(player, teams, assistantCardAnchorPane);

    }
    public void initialSetupCharacterCard() {
    }
    public void initialSetupClouds() {
    }

    public void initialSetupMineSchool(ArrayList<LightTeam> lightTeams) throws IOException {
        String path= "/Client/GUI/Controllers/MineSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        mineSchoolAnchorPane.getChildren().clear();
        mineSchoolAnchorPane.getChildren().add(0,loader.load());
        mineSchoolAnchorPane.getChildren().add(new Text("Your School"));
        MineSchoolController controller = loader.getController();
        String player=GuiMainStarter.getClientGUI().getServerConnection().getNickname();

        controller.setup(lightTeams, player, mineSchoolAnchorPane);
    }


    public void initialSetupOtherSchool(ArrayList<LightTeam> lightTeams) throws IOException {

        String path= "/Client/GUI/Controllers/OtherSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));


        HBox otherPlayers = new HBox();
        otherPlayers.setSpacing(6);

        int c=0;
        String tempOwner=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        for(LightTeam team: lightTeams)
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(!player.getNome().equals(tempOwner))
                {
                    Button playerSwitch = new Button(player.getNome());
                    otherPlayers.getChildren().add(playerSwitch);
                    c++;
                    playerSwitch.setId("pSchool"+c);
                }
            }
        }
        otherSchoolAnchorPane.getChildren().clear();
        otherSchoolAnchorPane.getChildren().add(0, loader.load());
        otherSchoolAnchorPane.getChildren().add(1, otherPlayers);


        OtherSchoolController controller = loader.getController();
        controller.setup(lightTeams, otherSchoolAnchorPane);
    }

}
