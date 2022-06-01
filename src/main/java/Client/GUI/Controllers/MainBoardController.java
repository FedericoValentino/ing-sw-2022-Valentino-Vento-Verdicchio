package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.LightIslands;
import Client.LightView.LightPlayer;
import Client.LightView.LightTeam;
import Client.LightView.LightView;
import Observer.ObserverLightView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import model.Player;
import model.Team;

import java.io.IOException;


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


    public void initialSetupIsland(LightIslands lightIslands) {
        String path= "/Client/GUI/Controllers/Islands.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        IslandsController contr= loader.getController();
        contr.setup(islandAnchorPane, lightIslands);
    }
    public void initialSetupAssistantCard() {
        String path= "/Client/GUI/Controllers/Assistants.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        OtherSchoolController assistantController= loader.getController();
        //assistantController.setup(assistantCardAnchorPane);

    }
    public void initialSetupCharactherCard() {
    }
    public void initialSetupClouds() {
    }

    public void initialSetupMineSchool() {
    }


    public void initialSetupOtherSchool(LightView lightView) throws IOException {

        String path= "/Client/GUI/Controllers/OtherSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        otherSchoolAnchorPane.getChildren().clear();
        HBox otherPlayers = new HBox();
        otherPlayers.setSpacing(6);

        int c=0;
        String tempOwner=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        System.out.println("temp owmer "+ tempOwner);
        //da vedere se usare il parametro oppure la reference con la client


        for(LightTeam team: GuiMainStarter.getClientGUI().getLightView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers()) {
                if(!player.getNome().equals(tempOwner))
                {
                    System.out.println("plat "+player.getNome());
                    Button tab = new Button(player.getNome());
                    otherPlayers.getChildren().add(tab);
                    c++;
                    tab.setId("pSchool"+c);
                    // qui dovrò fare una cosa tipo  tab.getChidlren e aggiungerci la tab precaricata se riesco
                    //poi chiamo il setup attuale di quel singolo tab in TabController passandogli qualcosa per identificarlo immagino


                }
            }
        }
        otherSchoolAnchorPane.getChildren().add(otherPlayers);
        otherSchoolAnchorPane.getChildren().add(loader.load());
    }

}
