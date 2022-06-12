package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class MainBoardController extends Controller {
    @FXML private AnchorPane mainAnchorPane;

    @FXML private AnchorPane otherSchoolAnchorPane;
    @FXML private AnchorPane characterAnchorPane;

    @FXML private AnchorPane islandAnchorPane;
    @FXML private AnchorPane assistantCardAnchorPane;

    @FXML private AnchorPane buttonAreaAnchorPane;
    @FXML private AnchorPane mineSchoolAnchorPane;


    public GridPane PlayedAssistants;

    /**
     * This method it's called when the mainBoardController is setted for the first time.
     * Firstly it loads the island's fxml from the specified path into the FXMLLoader object called loader.
     * Then it replaces the content of the islandAnchorPane with the loader's one.
     * Lastly it creates the reference to the IslandController and it calls the setup method specified in it.
     * **/
    public void initialSetupIsland(LightView view) throws IOException {
        String path= "/Client/GUI/Controllers/Islands.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        islandAnchorPane.getChildren().clear();
        islandAnchorPane.getChildren().add(loader.load());
        IslandsController contr = loader.getController();
        contr.setup(islandAnchorPane, view);
    }


    /**
     * This method it's called when the mainBoardController is setted for the first time.
     * Firstly it loads the Assistant fxml from the specified path into the FXMLLoader object called loader.
     * Then it replaces the content of the assistantCardAnchorPane with the loader's one.
     * Lastly it creates the reference to the AssistantCardController, store the value of the current player into the
     * local variable called player and it calls the setup method specified in it.
     * **/
   public void initialSetupAssistantCard(ArrayList<LightTeam> teams) throws IOException {
        String path = "/Client/GUI/Controllers/Assistants.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        assistantCardAnchorPane.getChildren().clear();
        assistantCardAnchorPane.getChildren().add(loader.load());
        AssistantCardsController assistantController= loader.getController();
        String player=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        assistantController.setup(player, teams, assistantCardAnchorPane, this);

    }
    public void initialSetupCharacterCard(LightCharDeck charDeck, LightActiveDeck activeDeck) throws IOException
    {
        String path = "/Client/GUI/Controllers/Character.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        characterAnchorPane.getChildren().clear();
        characterAnchorPane.getChildren().add(loader.load());
        CharacterCardsController characterController = loader.getController();

        characterController.setup(characterAnchorPane, charDeck, activeDeck, this);
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

    public void initialSetupPropaganda(LightView view, InfoDispenser infos) throws IOException
    {
        String path = "/Client/GUI/Controllers/Propaganda.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        buttonAreaAnchorPane.getChildren().clear();
        buttonAreaAnchorPane.getChildren().add(0,loader.load());
        PropagandaController propagandaController = loader.getController();

        propagandaController.setup(infos, view);
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

    public void displayCharInfo(LightCharacterCard card, String path)
    {
        Pane charDescription = (Pane) (mainAnchorPane.getChildren().stream().filter(node -> node.getId().equals("CharDescription")).collect(Collectors.toList()).get(0));

        Pane charImage = (Pane) charDescription.getChildren().stream().filter(node -> node.getId().equals("CharImage")).collect(Collectors.toList()).get(0);
        charImage.getChildren().clear();

        ImageView image = new ImageView(path);
        image.setFitHeight(358);
        image.setFitWidth(244);
        charImage.getChildren().add(image);

        Text name = (Text) charDescription.getChildren().stream().filter(node -> node.getId().equals("Name")).collect(Collectors.toList()).get(0);
        name.setText("");
        name.setText(card.getName().toString());

        Text description = (Text) charDescription.getChildren().stream().filter(node -> node.getId().equals("Description")).collect(Collectors.toList()).get(0);
        description.setText("");
        description.setText(Arrays.toString(card.getDescription()));

        Button back = (Button) (charDescription.getChildren().stream().filter(node -> node.getId().equals("BackButton")).collect(Collectors.toList()).get(0));
        back.setOnMouseClicked(this:: hideCharacterInfo);

        charDescription.setVisible(true);
        charDescription.setMouseTransparent(false);
    }

    public void hideCharacterInfo(MouseEvent mouseEvent)
    {
        Pane charDescription = (Pane) mainAnchorPane.getChildren().stream().filter(node -> node.getId().equals("CharDescription")).collect(Collectors.toList()).get(0);

        charDescription.setVisible(false);
        charDescription.setMouseTransparent(true);
    }

    public void showPlayedAssistants(String path, int column, int row, LightPlayer player, boolean hide)
    {
        if(!hide)
        {
            Pane cell = getCellFromGridPane(PlayedAssistants, column, row);
            cell.getChildren().clear();
            Text name = new Text(player.getNome());
            cell.getChildren().add(name);
            name.translateXProperty().add(-15);
            name.translateYProperty().add(70);


            ImageView image = new ImageView(path);
            image.setFitWidth(140);
            image.setFitHeight(150);
            cell.getChildren().add(image);
            PlayedAssistants.setVisible(true);
            PlayedAssistants.setMouseTransparent(false);
        }
        else
        {
            PlayedAssistants.setVisible(false);
            PlayedAssistants.setMouseTransparent(true);
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
