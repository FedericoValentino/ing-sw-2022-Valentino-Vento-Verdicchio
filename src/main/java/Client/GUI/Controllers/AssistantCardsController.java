package Client.GUI.Controllers;

import Client.LightView.LightPlayer;
import Client.LightView.LightTeam;
import Observer.ObserverLightView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.cards.AssistantCard;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class AssistantCardsController extends Controller implements ObserverLightView

{
    @FXML
    public AnchorPane AssistantsAnchorPane;
    public HBox Box;
    public ArrayList<LightTeam> teams;
    public ArrayList<LightPlayer> players = new ArrayList<>();
    public ArrayList<AssistantCard> played = new ArrayList<>();
    public String currentPlayer;

    public void setup(String currentPlayer, ArrayList<LightTeam> teams, AnchorPane AssistantsPane) {
        this.teams = teams;
        this.AssistantsAnchorPane = AssistantsPane;
        this.currentPlayer = currentPlayer;
        for (LightTeam team : teams) {
            for (LightPlayer player : team.getPlayers()) {
                player.addObserverLight(this);
                players.add(player);
            }
        }
        Button lastPlayed = (Button) AssistantsPane.getChildren().stream().filter(node -> node.getId().equals("LastPlayedButton")).collect(Collectors.toList()).get(0);
        lastPlayed.setOnMouseClicked((event) -> {

        });

        for(LightPlayer player: players)
            update(player);
    }






    @Override
    public void update(Object o)
    {
        LightPlayer player = (LightPlayer) o;
        if(player.getLastPlayedCard() != null)
        {
            if(played.size() == players.size())
                played.clear();
            played.add(player.getLastPlayedCard());
        }

        if(player.getNome().equals(currentPlayer))
        {
            HBox cards = (HBox) AssistantsAnchorPane.getChildren().stream().filter(node -> node.getId().equals("Box")).collect(Collectors.toList()).get(0);
            for(AssistantCard card: player.getAssistantDeck().getDeck())
            {
                Pane cardPane = new Pane();
                cardPane.getChildren().clear();
                cardPane.getChildren().add(new ImageView())
            }
        }
    }

    public String getAssistantPath(AssistantCard card)
    {

    }
}
