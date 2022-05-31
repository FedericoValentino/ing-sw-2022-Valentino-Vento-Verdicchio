package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.LightPlayer;
import Client.LightView.LightTeam;
import Client.LightView.LightView;
import Observer.ObserverLightView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import model.Player;
import model.Team;

import java.io.IOException;


public class MainBoardController extends Controller implements ObserverLightView {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public AnchorPane otherSchoolAnchorPane;
    @FXML public AnchorPane characterAnchorPane;

    @FXML public AnchorPane islandAnchorPane;
    @FXML public AnchorPane cloudsAnchorPane;
    @FXML public AnchorPane assistantCardAnchorPane;

    @FXML public AnchorPane buttonAreaAnchorPane;
    @FXML public AnchorPane mineSchoolAnchorPane;

    private LightView lightView;


    public void initialSetupIsland() {
    }
    public void initialSetupAssistantCard() {
    }
    public void initialSetupCharactherCard() {
    }
    public void initialSetupClouds() {
    }

    public void initialSetupMineSchool() {
    }


    public void initialSetupOtherSchool(LightView lightView) throws IOException {
        lightView.addObserverLight(this);
        String path= "/Client/GUI/Controllers/OtherSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        otherSchoolAnchorPane.getChildren().clear();
        TabPane tabOtherSchool=new TabPane();
        Tab tab;
        int c=0;
        String tempOwner=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        System.out.println("temp pwmer "+ tempOwner);
        //da vedere se usare il parametro oppure la reference con la client
        for(LightTeam team: GuiMainStarter.getClientGUI().getLightView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers()) {
                if(player.getNome()!=tempOwner)
                {
                    System.out.println("plat "+player.getNome());
                    tab=new Tab();
                    c++;
                    tab.setText(player.getNome());
                    tab.setId("pSchool"+c);
                    System.out.println("pschool numero + "+ c +" nome "+player.getNome());
                    // qui dovrò fare una cosa tipo  tab.getChidlren e aggiungerci la tab precaricata se riesco
                    //poi chiamo il setupattuale di quel singolo tab in TabController passandogli qualcosa per identificarlo immagino

                    tabOtherSchool.getTabs().add(tab);
                }
            }
        }
        otherSchoolAnchorPane.getChildren().add(loader.load());
        otherSchoolAnchorPane.getChildren().add(tabOtherSchool);


    }

    @Override
    public void update(Object o) {
        lightView = (LightView) o;
        //setupOtherSchool();
        //setup varii
    }
}
