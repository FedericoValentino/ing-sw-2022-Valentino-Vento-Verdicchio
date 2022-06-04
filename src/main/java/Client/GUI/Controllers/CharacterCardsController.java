package Client.GUI.Controllers;

import Client.LightView.LightActiveDeck;
import Client.LightView.LightCharDeck;
import Observer.ObserverLightView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.boards.token.CharacterName;
import model.cards.CharacterCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CharacterCardsController extends Controller implements ObserverLightView
{
    public AnchorPane CharacterPane;
    public LightCharDeck characterDeck;
    public LightActiveDeck activeCharDeck;
    public ArrayList<CharacterName> sceneCards = new ArrayList<>();
    public boolean firstTime = true;
    public Pane CharacterImage;
    public Circle CharacterStatusIndicator;
    public TextField StatusDescriptor;
    public TextArea Parameters;
//

    public void setup(AnchorPane characterPane, LightCharDeck inactiveCharacters, LightActiveDeck activeCharacters)
    {
        this.CharacterPane = characterPane;
        this.characterDeck = inactiveCharacters;
        this.activeCharDeck = activeCharacters;
        if(firstTime) {
            for(int i = 0; i < characterDeck.getLightCharDeck().size(); i++)
                sceneCards.add(characterDeck.getCard(i).getCharacterName());
            firstTime = false;
        }
        characterDeck.addObserverLight(this);
        activeCharDeck.addObserverLight(this);

        Button previous = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("PreviousButton")).collect(Collectors.toList()).get(0);
        previous.setOnMouseClicked(this:: previousOnClick);
        Button next = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("NextButton")).collect(Collectors.toList()).get(0);
        next.setOnMouseClicked(this:: nextOnClick);
        Button activate = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("ActivateButton")).collect(Collectors.toList()).get(0);
        next.setOnMouseClicked(this:: activateOnClick);
        Button playEffect = (Button) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("PlayEffectButton")).collect(Collectors.toList()).get(0);
        playEffect.setOnMouseClicked(this:: playOnClick);
        update(characterDeck);
        update(activeCharDeck);
    }

    private void playOnClick(MouseEvent mouseEvent) {
    }

    private void activateOnClick(MouseEvent mouseEvent) {
    }

    private void nextOnClick(MouseEvent mouseEvent) {
    }

    private void previousOnClick(MouseEvent mouseEvent) {
    }


    @Override
    public void update(Object o)
    {

        CharacterName name = CharacterName.valueOf(StatusDescriptor.getCharacters().toString());
        ArrayList<CharacterCard> deck = findDeck(name);
        int position = getPositionInDeck(name, deck);
        ImageView cardImage = new ImageView(getCardPath(deck.get(position).getCharacterName()));
        CharacterImage.getChildren().add(cardImage);
        TextField description = (TextField) ((AnchorPane) CharacterPane.getChildren().get(0)).getChildren().stream().filter(node -> node.getId().equals("Description")).collect(Collectors.toList()).get(0);
        description.setText(Arrays.toString(deck.get(position).description()));
        CharacterStatusIndicator.setFill(Paint.valueOf("FF1F1F"));
        StatusDescriptor.clear();
        StatusDescriptor.setText(deck.get(position).getCharacterName().toString());
        Parameters.clear();
        Parameters.setText("Uses: " + characterDeck.getCard(0).getUses() + "\nCurrent Cost: " + deck.get(position).getCurrentCost());
    }



    public ArrayList<CharacterCard> findDeck(CharacterName name)
    {
        for(CharacterCard card: characterDeck.getLightCharDeck())
            if(card.getCharacterName().equals(name))
                return characterDeck.getLightCharDeck();
            else
                return activeCharDeck.getLightActiveDeck();
        return null;
    }

    public int getPositionInDeck(CharacterName name, ArrayList<CharacterCard> deck)
    {
        for(int i = 0; i < deck.size(); i++)
            if(deck.get(i).getCharacterName().equals(name))
                return i;

        return -1;
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
