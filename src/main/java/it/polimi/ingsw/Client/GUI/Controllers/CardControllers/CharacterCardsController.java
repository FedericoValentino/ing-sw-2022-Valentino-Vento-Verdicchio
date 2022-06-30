package it.polimi.ingsw.Client.GUI.Controllers.CardControllers;
//y
import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.Controllers.MainBoardController;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightActiveDeck;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharDeck;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
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
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;


import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CharacterCardsController extends Controller implements ObserverLightView
{
    private AnchorPane characterPane;
    private LightCharDeck characterDeck;
    private LightActiveDeck activeCharDeck;

    private ArrayList<LightCharacterCard> sceneCards = new ArrayList<>();
    private int currentlyShowedCard = 2;

    private MainBoardController mainController;

    @FXML private StackPane mainPane;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    @FXML private Button activateButton;

    /**
     * Method Setup is called when first initializing the characterCard AnchorPane. It reads through all the active deck and inactive deck and sets up a stackPane,
     * containing the character to show.
     * If the game is not set to be played in expertMode then no stackpane is created and just a text telling the user that the slot is not available will show up
     * @param characterPane is the anchorPane to be modified
     * @param inactiveCharacters is our characterDeck in the lightView
     * @param activeCharacters is our activeCharacterDeck in the lightView
     * @param controller is our mainBoarController. We save it here so that we can then call the cards visualization methods contained in there.
     * @throws IOException
     */
    public void setup(AnchorPane characterPane, LightCharDeck inactiveCharacters, LightActiveDeck activeCharacters, MainBoardController controller) throws IOException {
        this.characterPane = characterPane;
        if(inactiveCharacters != null)
        {
            this.characterDeck = inactiveCharacters;
            this.activeCharDeck = activeCharacters;
            this.mainController = controller;

            sceneCards.addAll(inactiveCharacters.getLightCharDeck());

            activeCharDeck.addObserverLight(this);

            previousButton.setOnMouseClicked(this::previousOnClick);

            nextButton.setOnMouseClicked(this::nextOnClick);

            activateButton.setOnMouseClicked(this::activateOnClick);


            for (int i = 0; i < 3; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/CharacterDepiction.fxml"));

                Pane pane = new Pane();
                pane.getChildren().clear();
                pane.getChildren().add(loader.load());

                pane.setOnMouseClicked(this::showOnClick);

                ImageView cardImage = new ImageView(getCardPath(sceneCards.get(i).getName()));
                cardImage.setFitWidth(106);
                cardImage.setFitHeight(113);

                Pane characterImage = (Pane) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterImage")).collect(Collectors.toList()).get(0);
                characterImage.getChildren().clear();
                characterImage.getChildren().add(cardImage);

                Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
                statusIndicator.setFill(Paint.valueOf("FF1F1F"));

                Text statusDescriptor = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("StatusDescriptor")).collect(Collectors.toList()).get(0);
                statusDescriptor.setText(sceneCards.get(i).getName().toString());

                Text parameters = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Parameters")).collect(Collectors.toList()).get(0);
                parameters.setText("Uses: " + characterDeck.getCard(0).getUses() + "\nCurrent Cost: " + sceneCards.get(i).getCurrentCost());

                pane.setVisible(false);
                mainPane.getChildren().add(pane);
            }
            mainPane.getChildren().get(2).setVisible(true);

        }
        else
        {
            characterPane.getChildren().clear();
            Text description = new Text("This slot is unlocked when playing expert mode");
            description.setLayoutY(100);
            description.setLayoutX(15);
            characterPane.getChildren().add(description);
        }
    }

    /**
     * Method activateOnClick is called whenever the play button is clicked. It calls the function in the mainBoardController showing the right form to fulfill
     * @param mouseEvent is the click event
     */
    private void activateOnClick(MouseEvent mouseEvent)
    {
        mainController.showCharacterEffectPanel(sceneCards.get(currentlyShowedCard));
    }

    /**
     * Shows the next character in the stackPane
     * @param mouseEvent is the click event
     */
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

    /**
     * Shows the previous character in the stackPane
     * @param mouseEvent is the click event
     */
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

    /**
     * Method show on click is called whenever the character ImageView is clicked. It then calls the method in main controller
     * for displaying the character info on screen
     * @param mouseEvent is the click event
     */
    private void showOnClick(MouseEvent mouseEvent)
    {
        LightCharacterCard card = sceneCards.get(currentlyShowedCard);
        String path = getCardPath(card.getName());
        mainController.displayCharInfo(card, path);
    }


    /**
     * Method update is called whenever the LightCharacterDeck is updated if updated with a card then the method finds the
     * right pane and sets the usually red dot to green, indicating that the character is active. If the Update doesn't receive
     * anything then it updates the cards with the new data and sets the dot of every pane in the stackPane to red.
     * @param o is our activeCard, can be also NULL
     */
    @Override
    public void update(Object o)
    {
        Platform.runLater(() -> {
            if(o!= null)
            {
                LightCharacterCard card = (LightCharacterCard) o;
                Pane currentPane = getCorrectPane(card);
                Text parameters = (Text) ((Pane) currentPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Parameters")).collect(Collectors.toList()).get(0);
                parameters.setText("");
                parameters.setText("Uses: " + card.getUses() + "\nCurrent Cost: " + card.getCurrentCost());
                Circle statusIndicator = (Circle) ((Pane) currentPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
                statusIndicator.setFill(Paint.valueOf("1A8000"));
                activateButton.setMouseTransparent(true);
                activateButton.setOpacity(0.30);
            }
            else
            {
                sceneCards.clear();
                sceneCards.addAll(characterDeck.getLightCharDeck());
                for(Node node1: mainPane.getChildren())
                {
                    Pane pane = (Pane)node1;
                    Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
                    statusIndicator.setFill(Paint.valueOf("FF1F1F"));
                    activateButton.setMouseTransparent(false);
                    activateButton.setOpacity(1);
                }
            }
            });
    }


    public Pane getCorrectPane(LightCharacterCard card)
    {
        CharacterName name = card.getName();
        for(Node correct: mainPane.getChildren())
        {
            Pane pane = (Pane)correct;
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
            case COOK:
                return "/Client/GUI/Images/Characters/Cook.jpg";
            case JESTER:
                return "/Client/GUI/Images/Characters/Jester.jpg";
            case MINSTREL:
                return "/Client/GUI/Images/Characters/Minstrel.jpg";
            case THIEF:
                return "/Client/GUI/Images/Characters/Thief.jpg";
            default:
                return "";
        }
    }

}
