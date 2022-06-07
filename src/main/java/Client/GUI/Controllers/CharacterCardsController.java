package Client.GUI.Controllers;

import Client.LightView.LightActiveDeck;
import Client.LightView.LightCharDeck;
import Observer.ObserverLightView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.boards.token.CharacterName;
import model.cards.CharacterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CharacterCardsController extends Controller implements ObserverLightView
{
    public AnchorPane CharacterPane;
    public LightCharDeck characterDeck;
    public LightActiveDeck activeCharDeck;

    public ArrayList<CharacterCard> sceneCards = new ArrayList<>();
    public int currentlyShowedCard = 2;

    public MainBoardController mainController;

    @FXML public StackPane mainPane;
    @FXML public Button PreviousButton;
    @FXML public Button NextButton;
    @FXML public Button ActivateButton;
    @FXML public Button PlayEffectButton;

    public void setup(AnchorPane characterPane, LightCharDeck inactiveCharacters, LightActiveDeck activeCharacters, MainBoardController controller) throws IOException {
        this.CharacterPane = characterPane;
        this.characterDeck = inactiveCharacters;
        this.activeCharDeck = activeCharacters;
        this.mainController = controller;

        sceneCards.addAll(inactiveCharacters.getLightCharDeck());

        activeCharDeck.addObserverLight(this);

        PreviousButton.setOnMouseClicked(this:: previousOnClick);

        NextButton.setOnMouseClicked(this:: nextOnClick);

        ActivateButton.setOnMouseClicked(this:: activateOnClick);

        PlayEffectButton.setOnMouseClicked(this:: playOnClick);

        for(int i=0; i<3; i++)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/CharacterDepiction.fxml"));

            Pane pane = new Pane();
            pane.getChildren().clear();
            pane.getChildren().add(loader.load());

            pane.setOnMouseClicked(this:: showOnClick);

            ImageView cardImage = new ImageView(getCardPath(sceneCards.get(i).getCharacterName()));
            cardImage.setFitWidth(106);
            cardImage.setFitHeight(113);

            Pane characterImage = (Pane) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterImage")).collect(Collectors.toList()).get(0);
            characterImage.getChildren().clear();
            characterImage.getChildren().add(cardImage);

            Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
            statusIndicator.setFill(Paint.valueOf("FF1F1F"));

            Text statusDescriptor = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("StatusDescriptor")).collect(Collectors.toList()).get(0);
            statusDescriptor.setText(sceneCards.get(i).getCharacterName().toString());

            Text parameters = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Parameters")).collect(Collectors.toList()).get(0);
            parameters.setText("Uses: " + characterDeck.getCard(0).getUses() + "\nCurrent Cost: " + sceneCards.get(i).getCurrentCost());

            pane.setVisible(false);
            mainPane.getChildren().add(pane);
        }
        mainPane.getChildren().get(2).setVisible(true);
    }

    private void playOnClick(MouseEvent mouseEvent) {
    }

    private void activateOnClick(MouseEvent mouseEvent) {
    }

    private void nextOnClick(MouseEvent mouseEvent)
    {
        Node FrontNode = mainPane.getChildren().get(2);
        Node NextNode = mainPane.getChildren().get(1);
        FrontNode.setVisible(false);
        FrontNode.toBack();
        NextNode.setVisible(true);
        currentlyShowedCard--;
        if (currentlyShowedCard < 0)
            currentlyShowedCard = 2;
    }

    private void previousOnClick(MouseEvent mouseEvent)
    {
        Node FrontNode = mainPane.getChildren().get(2);
        Node NextNode = mainPane.getChildren().get(0);
        FrontNode.setVisible(false);
        NextNode.setVisible(true);
        NextNode.toFront();
        currentlyShowedCard++;
        if(currentlyShowedCard > 2)
            currentlyShowedCard = 0;
    }

    private void showOnClick(MouseEvent mouseEvent)
    {
        CharacterCard card = sceneCards.get(currentlyShowedCard);
        String path = getCardPath(card.getCharacterName());
        mainController.displayCharInfo(card, path);
    }


    @Override
    public void update(Object o)
    {
        if(o!= null)
        {
            CharacterCard card = (CharacterCard) o;
            Pane currentPane = getCorrectPane(card);
            Text parameters = (Text) ((Pane) currentPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Parameters")).collect(Collectors.toList()).get(0);
            parameters.setText("Uses: " + card.getUses() + "\nCurrent Cost: " + card.getCurrentCost());
            Circle statusIndicator = (Circle) ((Pane) currentPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
            statusIndicator.setFill(Paint.valueOf("1A8000"));
        }
        else
        {
            for(Node node1: mainPane.getChildren())
            {
                Pane pane = (Pane)node1;
                Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
                statusIndicator.setFill(Paint.valueOf("FF1F1F"));
            }
        }
    }


    public Pane getCorrectPane(CharacterCard card)
    {
        CharacterName name = card.getCharacterName();
        for(Node node1: mainPane.getChildren())
        {
            Pane pane = (Pane)node1;
            Text statusDescriptor = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("StatusDescriptor")).collect(Collectors.toList()).get(0);
            if(CharacterName.valueOf(statusDescriptor.getText()).equals(name))
                return pane;
        }
        return null;
    }


    public String getCardPath(CharacterName name){
        switch(name)
        {
            case HERALD:
                return "/Client/GUI/Images/Characters/Herald.jpg";
            case CENTAUR:
                return "/Client/GUI/Images/Characters/Centaur.jpg";
            case GRANDMA_HERBS:
                return "/Client/GUI/Images/Characters/Gweed.jpg";
            case KNIGHT:
                return "/Client/GUI/Images/Characters/Knight.jpg";
            case POSTMAN:
                return "/Client/GUI/Images/Characters/Postman.jpg";
            case PRIEST:
                return "/Client/GUI/Images/Characters/Priest.jpg";
            case PRINCESS:
                return "/Client/GUI/Images/Characters/Princess.jpg";
            case TRUFFLE_HUNTER:
                return "/Client/GUI/Images/Characters/THunter.jpg";
            default:
                return "";
        }
    }

}
