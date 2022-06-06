package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.LightPlayer;
import Client.LightView.LightTeam;
import Client.Messages.ActionMessages.DrawAssistantCard;
import Client.Messages.SerializedMessage;
import Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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


    private void onClick(MouseEvent event)
    {
        GridPane playedCards = (GridPane) ((AnchorPane) AssistantsAnchorPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("LastPlayedPane")).collect(Collectors.toList()).get(0);
        Button button = (Button) event.getSource();
        if(button.getText().equals("Active\nAssistants")) {
            playedCards.setVisible(true);
            playedCards.toFront();
            button.setText("Hide");
        }
        else
        {
            button.setText("Active\nAssistants");
            playedCards.setVisible(false);
            playedCards.toBack();
        }
    }

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
        Button lastPlayed = (Button) ((AnchorPane) AssistantsPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("LastPlayedButton")).collect(Collectors.toList()).get(0);
        lastPlayed.setOnMouseClicked(this::onClick);
        for(LightPlayer player: players)
            update(player);
    }


    @Override
    public void update(Object o)
    {
        Platform.runLater(()-> {
            LightPlayer player = (LightPlayer) o;
            boolean endTurn = played.size() == players.size();
            if(player.getLastPlayedCard() != null)
            {
                if(endTurn)
                    played.clear();
                played.add(player.getLastPlayedCard());
            }

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
                        cardPane.setVisible(false);
                        cardPane.setMouseTransparent(true);
                    });
                    cardIndex++;
                }
            }

            int tempPlayedCards = played.size();
            GridPane playedCards = (GridPane) ((AnchorPane) AssistantsAnchorPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("LastPlayedPane")).collect(Collectors.toList()).get(0);

            if(endTurn)
                playedCards.getChildren().clear();

            for(int i = 0; i < 2 && tempPlayedCards > 0 ; i++)
                for(int j = 0; j < 2; j++)
                {
                    Pane cell = getCellFromGridPane(playedCards, j, i);
                    cell.getChildren().clear();
                    ImageView cardImage = new ImageView(getAssistantPath(played.get(tempPlayedCards - 1)));
                    cardImage.setFitHeight(160);
                    cardImage.setFitWidth(100);
                    cell.getChildren().add(cardImage);
                    cell.getChildren().add(new Text(player.getNome()));
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

    public Pane getCellFromGridPane(GridPane matrix, int column, int row)
    {
        for(Node N : matrix.getChildren())
        {
            int rowN = GridPane.getRowIndex(N);
            int columnN = GridPane.getColumnIndex(N);
            if(rowN == row && columnN == column)
            {
                return (Pane)N;
            }
        }
        return null;
    }


}
