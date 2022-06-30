package it.polimi.ingsw.Client.GUI.Controllers;
//
import it.polimi.ingsw.Client.CharacterActivationParser;
import it.polimi.ingsw.Client.GUI.Controllers.BoardControllers.IslandsController;
import it.polimi.ingsw.Client.GUI.Controllers.BoardControllers.MineSchoolController;
import it.polimi.ingsw.Client.GUI.Controllers.BoardControllers.OtherSchoolController;
import it.polimi.ingsw.Client.GUI.Controllers.CardControllers.AssistantCardsController;
import it.polimi.ingsw.Client.GUI.Controllers.CardControllers.CharacterCardsController;
import it.polimi.ingsw.Client.GUI.Controllers.InformationControllers.PropagandaController;
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.*;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightActiveDeck;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharDeck;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterType;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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

    @FXML private Pane EffectPane;
    @FXML private HBox input1;
    @FXML private HBox input2;
    @FXML private Text EffectDescription;
    @FXML private Button PlayButton;
    @FXML private Button BackToBoard;

    @FXML  private GridPane PlayedAssistants;

    @FXML private Text ErrorMessage;
    @FXML private Pane ErrorDisplay;
    @FXML private Button CloseError;

    private ArrayList<Integer> integerChoice_1 = new ArrayList<>();
    private ArrayList<Integer> integerChoice_2 = new ArrayList<>();
    private Col colorChoice;

    private LightView view;


    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the island's fxml file from the specified path into the FXMLLoader object called loader.
     *  Then it replaces the content of the islandAnchorPane with the loader's one.
     *  Lastly it creates the reference to the IslandController, and it calls the setup method specified in it.
     * @param view is used to invoke the setup method of the IslandController
     * @throws IOException to resolve possible problems with the load() method
     * **/
    public void initialSetupIsland(LightView view) throws IOException {
        String path= "/Client/GUI/Controllers/Islands.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        islandAnchorPane.getChildren().clear();
        islandAnchorPane.getChildren().add(loader.load());
        IslandsController contr = loader.getController();
        contr.setup(view);
    }

    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the Assistant fxml file from the specified path into the FXMLLoader object called loader.
     *  Then it replaces the content of the assistantCardAnchorPane with the loader's one.
     *  Lastly it creates the reference to the AssistantCardController, store the value of the current player into the
     * local variable called player and it calls the setup method specified in it.
     * @param teams contains all the team in the game and it' used in the setup of the assistant's card
     * @throws IOException to resolve possible problems with the load() method
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

    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the Character fxml file from the specified path into the FXMLLoader object called loader.
     *  Then it replaces the content of the characterAnchorPane with the loader's one.
     *  Lastly it creates the reference to the CharacterCardsController and it calls the setup method specified in it.
     * @param charDeck contains the available character not chosen yet
     * @param activeDeck contains the character card that have been already used in the game
     * @throws IOException to resolve possible problems with the load() method
     */
    public void initialSetupCharacterCard(LightCharDeck charDeck, LightActiveDeck activeDeck) throws IOException
    {
        String path = "/Client/GUI/Controllers/Character.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        characterAnchorPane.getChildren().clear();
        characterAnchorPane.getChildren().add(loader.load());
        CharacterCardsController characterController = loader.getController();

        characterController.setup(characterAnchorPane, charDeck, activeDeck, this);
    }

    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the MineSchool fxml file from the specified path into the FXMLLoader object called loader.
     *  Then it replaces the content of the mineSchoolAnchorPane with the loader's one. Then it also adds a text.
     *  Lastly it creates the reference to the MineSchoolController, takes the player name and it calls the setup method
     * specified in it.
     * @throws IOException to resolve possible problems with the load() method
     */
    public void initialSetupMineSchool() throws IOException {
        String path= "/Client/GUI/Controllers/MineSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        mineSchoolAnchorPane.getChildren().clear();
        mineSchoolAnchorPane.getChildren().add(0,loader.load());

        //wrapping the text "Your school" in a hbox and add it to the main area (over the fxml preloaded)
        HBox textHbox=new HBox();
        textHbox.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-padding: 2,0,2,10");
        textHbox.getChildren().add(new Text("Your School"));
        mineSchoolAnchorPane.getChildren().add(textHbox);

        MineSchoolController controller = loader.getController();
        String player=GuiMainStarter.getClientGUI().getServerConnection().getNickname();

        controller.setup(view, player, mineSchoolAnchorPane);
    }

    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the MineSchool fxml file from the specified path into the FXMLLoader object called loader.
     *  Then it replaces the content of the mineSchoolAnchorPane with the loader's one.
     *  Lastly it creates the reference to the MineSchoolController and it calls the setup method
     *specified in it.
     * @param view contain the view used in the setup call
     * @throws IOException to resolve possible problems with the load() method
     */
    public void initialSetupPropaganda(LightView view) throws IOException
    {
        String path = "/Client/GUI/Controllers/Propaganda.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        buttonAreaAnchorPane.getChildren().clear();
        buttonAreaAnchorPane.getChildren().add(0,loader.load());
        PropagandaController propagandaController = loader.getController();

        propagandaController.setup(view);
    }

    /**This method it's called when the mainBoardController is set for the first time.
     *  Firstly it loads the OtherSchool fxml file from the specified path into the FXMLLoader object called loader.
     *  Secondly for each player it creates a button with his name and set its id as "pSchool"+c
     *  Thirdly it replaces the content of the otherSchoolAnchorPane with the loader's one and with the buttons to switch
     * from a player to another one.
     *  Lastly it creates the reference to the OtherSchoolController and it calls the setup method
     *specified in it.
     * @param view  contain the view used in the setup call
     * @throws IOException to resolve possible problems with the load() method
     */
    public void initialSetupOtherSchool(LightView view) throws IOException {

        String path= "/Client/GUI/Controllers/OtherSchool.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));


        HBox otherPlayers = new HBox();
        otherPlayers.setSpacing(6);


        int c=0;
        String tempOwner=GuiMainStarter.getClientGUI().getServerConnection().getNickname();
        for(LightTeam team: view.getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(!player.getName().equals(tempOwner))
                {
                    Button playerSwitch = new Button(player.getName());
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
        controller.setup(view, otherSchoolAnchorPane);
    }

    /**This method show the description and a greater image of the character (it's called when we click on the character pane)
     *  Firstly it chooses the charDesctiption and the charImage panes where it has to show the details.
     *  Secondly it sets the right image and description
     *  Thirdly it finds the backButton by id and set dynamically its function when it's pressed
     *  Lastly it set all the graphics elements added as visible
     * @param card is the character selected
     * @param path is the path where we take the corresponding card
     */
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

    /**This method hide the character description pane set visible in the previous method (displayCharInfo method)
     * @param mouseEvent is the event generated when the user push the back button
     */
    public void hideCharacterInfo(MouseEvent mouseEvent)
    {
        Pane charDescription = (Pane) mainAnchorPane.getChildren().stream().filter(node -> node.getId().equals("CharDescription")).collect(Collectors.toList()).get(0);

        charDescription.setVisible(false);
        charDescription.setMouseTransparent(true);
    }

    /**This method show a greater image of the assistant card played in the current turn
     * (it's called when we click on the activate assistant button)
     * If it has to show the pane (hide is false) it creates a pane cell in which it loads the name of the player that
     * has played that card and the corresponding image.
     * Else it sets not visible the pane
     *
     * @param path where we can load the image of the assistant
     * @param column is the column of the grid pane where there is the node in which it has to insert the image and text
     * @param row is the row of the grid pane where there is the node in which it has to insert the image and text
     * @param player is the name of the player that played the card
     * @param hide is the boolean that says if we have to hide the PlayedAssistants or not
     */
    public void showPlayedAssistants(String path, int column, int row, LightPlayer player, boolean hide)
    {
        if(!hide)
        {
            Pane cell = (Pane) GUIUtilities.getCellFromGridPane(PlayedAssistants, column, row);
            cell.getChildren().clear();
            Text name = new Text(player.getName());
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


    /**This method set visible the effect panel of the character card that the user wanto to play.
     * Firstly it changes the text, the input, the integer and sets the method on click for the backButton.
     * Then according to the type of the cards it choose what to show:
     *  -NONE: send a message to the server to activate the card and stop
     *  -INTEGER_1: called the setup for princess or for the other types and then send a message to the server to activate
     *              the card
     *  -INTEGER_2: called the setup for minstrel or jester or for the other types and then send a message to the server to activate
     *              the card, but only if the user chose all the students from dining and card
     *  -COLOR: call the color setup method and sets the method to handle the click on the playButton
     * @param card is used because it contains the name and the type of the card
     */
    public void showCharacterEffectPanel(LightCharacterCard card) {
        EffectPane.setMouseTransparent(false);
        EffectPane.setVisible(true);
        EffectDescription.setText("You are playing " + card.getName());
        input1.getChildren().clear();
        input2.getChildren().clear();
        integerChoice_1.clear();
        integerChoice_2.clear();
        BackToBoard.setOnMouseClicked((MouseEvent) ->
        {
            EffectDescription.setText("");
            integerChoice_1.clear();
            integerChoice_2.clear();
            colorChoice = null;
            EffectPane.setMouseTransparent(true);
            EffectPane.setVisible(false);
        });

        switch (card.getType())
        {
            case NONE:
                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            CharacterActivationParser activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName());
                            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                            EffectPane.setVisible(false);
                            EffectPane.setMouseTransparent(true);
                        }
                );
                break;

            case INTEGER_1:
                if(card.getName().equals(CharacterName.PRINCESS))
                {
                    PrincessSetup(card);
                }
                else
                {
                    Integer1Setup();
                }
                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            CharacterActivationParser activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName(), integerChoice_1);
                            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                            EffectPane.setVisible(false);
                            EffectPane.setMouseTransparent(true);
                        }
                );
                break;

            case INTEGER_2:
                LightPlayer player = Utilities.findPlayerByName(view, GuiMainStarter.getClientGUI().getServerConnection().getNickname());
                if(card.getName().equals(CharacterName.JESTER))
                {
                    JesterSetup(card, player);
                }
                else if(card.getName().equals(CharacterName.MINSTREL))
                {
                    MinstrelSetup(player);
                }
                else
                {
                    Integer2Setup(card);
                }
                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            if(card.getType().equals(LightCharacterType.INTEGER_2) && integerChoice_2.size() != integerChoice_1.size())
                            {
                                EffectPane.setVisible(false);
                                EffectPane.setMouseTransparent(true);
                                DisplayError("Wrong input, try again");
                            }
                            else
                            {
                                CharacterActivationParser activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName(), integerChoice_1, integerChoice_2);
                                GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                                EffectPane.setVisible(false);
                                EffectPane.setMouseTransparent(true);
                            }
                        }
                );
                break;

            case COLOR:
                ColorSetup();
                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            CharacterActivationParser activation;
                            activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName(), colorChoice);
                            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                            EffectPane.setVisible(false);
                            EffectPane.setMouseTransparent(true);
                        }
                );

                break;

        }
    }

    /**This method sets the character effect pane with the student on the card and also sets the method to
     * handle the selection of the students.
     * @param card is used because it contains the students that can be taken
     */
    public void PrincessSetup(LightCharacterCard card)
    {
        input1.getChildren().add(new Text("Students on Card"));
        int studentPosition = 0;
        for(Student student : card.getStudentList())
        {
            ImageView image = new ImageView(GUIUtilities.getRightColorPath(student));
            image.setFitHeight(27);
            image.setFitWidth(27);

            Pane studentPane = new Pane();
            studentPane.setId(String.valueOf(studentPosition));

            studentPane.setOnMouseClicked((MouseEvent) ->{
                if(integerChoice_1.size() == 0)
                {
                    integerChoice_1.add(Integer.parseInt(((Node)MouseEvent.getSource()).getId()));
                    ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                }
            });
            studentPane.getChildren().add(image);
            input1.getChildren().add(studentPane);

            studentPosition++;
        }
    }

    /**This method sets the character pane with the 5 colors available (showing a pane with the image of a student of
     * each color) and sets also the method to handle the selection
     */
    public void ColorSetup()
    {
        input1.getChildren().add(new Text("Choose a color"));
        for(int i = 0; i < 5; i++)
        {
            Student student = new Student(Col.values()[i]);
            ImageView color = new ImageView(GUIUtilities.getRightColorPath(student));
            Pane studentPane = new Pane();
            studentPane.setId(Col.values()[i].toString());
            studentPane.setOnMouseClicked((MouseEvent) ->
            {
                ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                colorChoice = Col.valueOf(((Node)MouseEvent.getSource()).getId());
            });
            studentPane.getChildren().add(color);
            input1.getChildren().add(studentPane);
        }
    }

    /**This method setup the character pane effect (of the character's type: integer1) adding a text, the islands choice box
     * and sets the method to run if the user select the island
     */
    public void Integer1Setup()
    {
        Text hint = new Text();
        hint.setText("Select the target island");
        input1.getChildren().add(hint);
        ChoiceBox<Integer> box = new ChoiceBox<>();
        for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
        {
            box.getItems().add(i);
        }
        box.setOnAction((Event) ->
        {
            integerChoice_1.clear();
            integerChoice_1.add(box.getSelectionModel().getSelectedIndex());
        });

        input1.getChildren().add(box);
    }

    /**This method setup the character pane effect (of the character's type: integer2) adding a text, the islands choice box
     * and the student that can be selected by the user because they are placed on the card.
     * It also set the method to run if the user select the island and the students
     */
    public void Integer2Setup(LightCharacterCard card)
    {
        //setup islands
        Text hint = new Text();
        hint.setText("Select the target island");
        input1.getChildren().add(hint);
        ChoiceBox<Integer> box = new ChoiceBox<>();
        for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
        {
            box.getItems().add(i);
        }
        box.setOnAction((Event) ->
        {
            integerChoice_1.clear();
            integerChoice_1.add(box.getSelectionModel().getSelectedIndex());
        });
        input1.getChildren().add(box);

        //setup students
        input2.getChildren().add(new Text("Select the student"));
        int studentPosition = 0;
        for(Student student : card.getStudentList())
        {
            ImageView image = new ImageView(GUIUtilities.getRightColorPath(student));
            image.setFitHeight(27);
            image.setFitWidth(27);

            Pane studentPane = new Pane();
            studentPane.setId(String.valueOf(studentPosition));
            studentPane.setOnMouseClicked((MouseEvent) ->
            {
                integerChoice_2.clear();
                ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                integerChoice_2.add(Integer.parseInt(((Node)MouseEvent.getSource()).getId()));
            });
            studentPane.getChildren().add(image);
            input2.getChildren().add(studentPane);
            studentPosition++;
        }
    }


    /**This method sets the character effect pane with the student on the card, the students in the entrance and
     * sets the method to handle the selection of the students
     * @param card is used because it contains the students that can be taken
     * @param player is used because it contains the students in the dining room
     */
    public void JesterSetup(LightCharacterCard card, LightPlayer player)
    {
        input1.getChildren().add(new Text("Students on card"));
        //setup students
        int studentPosition = 0;
        for(Student student : card.getStudentList())
        {
            ImageView image = new ImageView(GUIUtilities.getRightColorPath(student));
            image.setFitHeight(27);
            image.setFitWidth(27);
            Pane studentPane = new Pane();
            studentPane.setId(String.valueOf(studentPosition));
            studentPane.setOnMouseClicked((MouseEvent) ->
            {
                if(integerChoice_2.size() < 3)
                {
                    ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                    integerChoice_2.add(Integer.parseInt(((Node)MouseEvent.getSource()).getId()));
                }

            });
            studentPane.getChildren().add(image);
            input1.getChildren().add(studentPane);
            studentPosition++;
        }
        input2.getChildren().add(new Text("Students in your entrance"));
        //setup students
        int entrancePos = 0;
        for(Student student : player.getSchool().getEntrance())
        {
            ImageView image = new ImageView(GUIUtilities.getRightColorPath(student));
            image.setFitHeight(27);
            image.setFitWidth(27);
            Pane studentPane = new Pane();
            studentPane.setId(String.valueOf(entrancePos));
            studentPane.setOnMouseClicked((MouseEvent) ->
            {
                if(integerChoice_1.size() < integerChoice_2.size())
                {
                    ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                    integerChoice_1.add(Integer.parseInt(((Node)MouseEvent.getSource()).getId()));
                }
            });
            studentPane.getChildren().add(image);
            input2.getChildren().add(studentPane);
            entrancePos++;
        }
    }


    /**This method setups the student choice of the ministrel. In particular it sets all the student that can be moved
     * and sets the method to run if the user select the student. Then it calls the setDiningRoomColors to show also the
     * students in the dining that you can swap with the selected before.
     * @param player is used because it contains the students in the dining room
     */
    public void MinstrelSetup(LightPlayer player)
    {
        final int[] diningRoom = player.getSchool().getDiningRoom().clone();
        //setup students
        input1.getChildren().add(new Text("Students in your entrance   "));
        int studentPosition = 0;
        for(Student student : player.getSchool().getEntrance())
        {
            ImageView image = new ImageView(GUIUtilities.getRightColorPath(student));
            image.setFitHeight(27);
            image.setFitWidth(27);
            Pane studentPane = new Pane();
            studentPane.setId(String.valueOf(studentPosition));
            studentPane.setOnMouseClicked((MouseEvent) ->
            {
                if(integerChoice_2.size() < 3)
                {
                    ((Pane)MouseEvent.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
                    integerChoice_2.add(Integer.parseInt(((Node)MouseEvent.getSource()).getId()));
                }
            });
            studentPane.getChildren().add(image);
            input1.getChildren().add(studentPane);
            studentPosition++;
        }
        input2.getChildren().add(new Text("Students in dining room available for swap"));
        setDiningRoomColors(diningRoom);
    }


    /**This method add the selected student to the integer1 (and if the user doesn't select all the 3 students
     * (or colors in this case is equivalent) it shows the colors already selected and ask to select other colors)
     * @param event used to add the effect to the clicked pane
     */
    public void minstrelColorChoice(MouseEvent event)
    {
        LightPlayer player = Utilities.findPlayerByName(view, GuiMainStarter.getClientGUI().getServerConnection().getNickname());
        if(integerChoice_1.size() < integerChoice_2.size())
        {
            ((Pane)event.getSource()).setEffect(new DropShadow(5, Color.DARKRED));
            integerChoice_1.add(Col.valueOf(((Node)event.getSource()).getId()).ordinal());
            if(integerChoice_1.size() < integerChoice_2.size())
            {
                input2.getChildren().clear();
                input2.getChildren().add(new Text("You selected " + ((Node) event.getSource()).getId() + " choose another color!"));
                int[] diningClone = player.getSchool().getDiningRoom().clone();
                diningClone[integerChoice_1.get(0)]--;
                setDiningRoomColors(diningClone);
            }
        }
    }

    /**This method is called by ministrel character and it adds the studentPane available to swap with the student in
     * the entrance
     * @param dining is used to set the player that can be switch to the entrance
     */
    public void setDiningRoomColors(int[] dining)
    {
        for(int i = 0; i < 5; i++)
        {
            if(dining[i] > 0)
            {
                Student student = new Student(Col.values()[i]);
                ImageView color = new ImageView(GUIUtilities.getRightColorPath(student));
                Pane studentPane = new Pane();
                studentPane.setId(Col.values()[i].toString());
                studentPane.setOnMouseClicked(this::minstrelColorChoice);
                studentPane.getChildren().add(color);
                input2.getChildren().add(studentPane);
            }
        }
    }

    /**This method set visible the error pane and display the error in input
     * @param error is the String that we have to display
     */
    public void DisplayError(String error)
    {
        ErrorDisplay.setVisible(true);
        ErrorDisplay.setMouseTransparent(false);
        ErrorMessage.setText(error);
    }


    /**This method update the view and sets not visible the errors
     * @param view is the new lightView
     */
    public void setup(LightView view)
    {
        this.view = view;
        CloseError.setOnMouseClicked((MouseEvent) -> {
            ErrorDisplay.setVisible(false);
            ErrorDisplay.setMouseTransparent(true);
        });
    }

}
