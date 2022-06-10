package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeam;
import it.polimi.ingsw.Client.Messages.ActionMessages.DrawAssistantCard;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import it.polimi.ingsw.model.cards.AssistantCard;


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

    public Button LastPlayedButton;

    private MainBoardController mainController;


    private void assistantsOnClick(MouseEvent event)
    {
        if(LastPlayedButton.getText().equals("Active\nAssistants"))
        {
            for (int i = 0; i < teams.size(); i++)
                for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {
                    LightPlayer player = teams.get(i).getPlayers().get(j);
                    if (player.getCurrentAssistantCard() != null) {
                        String path = getAssistantPath(player.getCurrentAssistantCard());
                        mainController.showPlayedAssistants(path, j, i, player, false);
                    }
                }
            LastPlayedButton.setText("Hide");
        }
        else {
            mainController.showPlayedAssistants("", -1, -1, null, true);
            LastPlayedButton.setText("Active\nAssistants");
        }
    }

    public void setup(String currentPlayer, ArrayList<LightTeam> teams, AnchorPane AssistantsPane, MainBoardController controller) {
        this.teams = teams;
        this.AssistantsAnchorPane = AssistantsPane;
        this.currentPlayer = currentPlayer;
        this.mainController = controller;
        for (LightTeam team : teams) {
            for (LightPlayer player : team.getPlayers()) {
                player.addObserverLight(this);
                players.add(player);
            }
        }
        Button lastPlayed = (Button) ((AnchorPane) AssistantsPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("LastPlayedButton")).collect(Collectors.toList()).get(0);
        lastPlayed.setText("Active\nAssistants");
        lastPlayed.setOnMouseClicked(this:: assistantsOnClick);
        for(LightPlayer player: players)
            update(player);
    }


    @Override
    public void update(Object o)
    {
        Platform.runLater(()-> {
            LightPlayer player = (LightPlayer) o;

            if(player.getNome().equals(currentPlayer))
            {
                HBox cards = (HBox) ((AnchorPane) AssistantsAnchorPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Box")).collect(Collectors.toList()).get(0);
                cards.getChildren().clear();
                int cardIndex = 0;
                for(AssistantCard card: player.getAssistantDeck().getDeck())
                {
                    int sugmaindex = cardIndex;
                    Pane cardPane = new Pane();
                    cardPane.getChildren().clear();
                    ImageView cardImage = new ImageView(getAssistantPath(card));
                    cardImage.setFitHeight(100);
                    cardImage.setFitWidth(86);
                    cardPane.getChildren().add(cardImage);
                    cards.getChildren().add(cardPane);
                    cardPane.setOnMouseClicked(MouseEvent -> {
                        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new DrawAssistantCard(sugmaindex)));
                    });
                    cardIndex++;
                }
            }
        });
    }


    public String getAssistantPath(AssistantCard card)
    {
        switch(card.getValue())
        {
            case 1:
                return "/Client/GUI/Images/Assistants/Assistente(1).png";
            case 2:
                return "/Client/GUI/Images/Assistants/Assistente(2).png";
            case 3:
                return "/Client/GUI/Images/Assistants/Assistente(3).png";
            case 4:
                return "/Client/GUI/Images/Assistants/Assistente(4).png";
            case 5:
                return "/Client/GUI/Images/Assistants/Assistente(5).png";
            case 6:
                return "/Client/GUI/Images/Assistants/Assistente(6).png";
            case 7:
                return "/Client/GUI/Images/Assistants/Assistente(7).png";
            case 8:
                return "/Client/GUI/Images/Assistants/Assistente(8).png";
            case 9:
                return "/Client/GUI/Images/Assistants/Assistente(9).png";
            case 10:
                return "/Client/GUI/Images/Assistants/Assistente(10).png";
            default:
                return "";
        }
    }


}
