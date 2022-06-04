package Client.GUI.Controllers;

import Client.LightView.LightActiveDeck;
import Client.LightView.LightCharDeck;
import Observer.ObserverLightView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.boards.token.CharacterName;
import model.cards.CharacterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CharacterCardsController extends Controller implements ObserverLightView
{
    public AnchorPane CharacterPane;
    public ArrayList<Pane> characters = new ArrayList<>();
    public LightCharDeck characterDeck;
    public LightActiveDeck activeCharDeck;
    public ArrayList<CharacterCard> sceneCards = new ArrayList<>();
    public int currentPane = 0;


    public void setup(AnchorPane characterPane, LightCharDeck inactiveCharacters, LightActiveDeck activeCharacters) throws IOException {
        this.CharacterPane = characterPane;
        this.characterDeck = inactiveCharacters;
        this.activeCharDeck = activeCharacters;

        sceneCards.addAll(inactiveCharacters.getLightCharDeck());

        activeCharDeck.addObserverLight(this);

        Button previous = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("PreviousButton")).collect(Collectors.toList()).get(0);
        previous.setOnMouseClicked(this:: previousOnClick);

        Button next = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("NextButton")).collect(Collectors.toList()).get(0);
        next.setOnMouseClicked(this:: nextOnClick);

        Button activate = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ActivateButton")).collect(Collectors.toList()).get(0);
        activate.setOnMouseClicked(this:: activateOnClick);

        Button playEffect = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("PlayEffectButton")).collect(Collectors.toList()).get(0);
        playEffect.setOnMouseClicked(this:: playOnClick);

        for(int i=0; i<3; i++)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/CharacterDepiction.fxml"));

            Pane pane = new Pane();
            pane.getChildren().clear();
            pane.getChildren().add(loader.load());

            ImageView cardImage = new ImageView(getCardPath(sceneCards.get(i).getCharacterName()));
            cardImage.setFitWidth(85);
            cardImage.setFitHeight(160);

            Pane characterImage = (Pane) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterImage")).collect(Collectors.toList()).get(0);
            characterImage.getChildren().clear();
            characterImage.getChildren().add(cardImage);

            Text description = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Description")).collect(Collectors.toList()).get(0);
            description.setText(Arrays.toString(sceneCards.get(i).description()));

            Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
            statusIndicator.setFill(Paint.valueOf("FF1F1F"));

            Text statusDescriptor = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("StatusDescriptor")).collect(Collectors.toList()).get(0);
            statusDescriptor.setText(sceneCards.get(i).getCharacterName().toString());

            Text parameters = (Text) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Parameters")).collect(Collectors.toList()).get(0);
            parameters.setText("Uses: " + characterDeck.getCard(0).getUses() + "\nCurrent Cost: " + sceneCards.get(i).getCurrentCost());

            characters.add(pane);
        }
        Pane display = (Pane) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("mainPane")).collect(Collectors.toList()).get(0);
        display = characters.get(0);
    }

    private void playOnClick(MouseEvent mouseEvent) {
    }

    private void activateOnClick(MouseEvent mouseEvent) {
    }

    private void nextOnClick(MouseEvent mouseEvent)
    {
        currentPane++;
        Pane display = (Pane) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("mainPane")).collect(Collectors.toList()).get(0);
        display = characters.get(currentPane%3);
    }

    private void previousOnClick(MouseEvent mouseEvent)
    {
        currentPane--;
        Pane display = (Pane) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("mainPane")).collect(Collectors.toList()).get(0);
        display = characters.get(currentPane%3);
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
            for(Pane pane: characters)
            {
                Circle statusIndicator = (Circle) ((Pane) pane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("CharacterStatusIndicator")).collect(Collectors.toList()).get(0);
                statusIndicator.setFill(Paint.valueOf("FF1F1F"));
            }
        }
    }


    public Pane getCorrectPane(CharacterCard card)
    {
        CharacterName name = card.getCharacterName();
        for(Pane pane: characters)
        {
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
