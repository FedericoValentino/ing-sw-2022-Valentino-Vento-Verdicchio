package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.CharacterActivationParser;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.*;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    @FXML private Text EffectDescription;
    @FXML private Pane ParametersSlice;
    @FXML private Button PlayButton;
    @FXML private Button BackToBoard;

    @FXML  private GridPane PlayedAssistants;

    @FXML private Text ErrorMessage;
    @FXML private Pane ErrorDisplay;
    @FXML private Button CloseError;

    private int integerChoice_1;
    private int integerChoice_2;
    private Col colorChoice;

    private LightView view;


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


    public void showCharacterEffectPanel(LightCharacterCard card) {
        EffectPane.setMouseTransparent(false);
        EffectPane.setVisible(true);
        EffectDescription.setText("You are playing " + card.getName());
        BackToBoard.setOnMouseClicked((MouseEvent) ->
        {
            EffectPane.setMouseTransparent(true);
            EffectPane.setVisible(false);
        });
        switch (card.getType())
        {
            case NONE:
                ParametersSlice.getChildren().clear();
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
                    HBox students = new HBox();
                    int studentPosition = 0;
                    for(Student student : card.getStudentList())
                    {
                        ImageView image = new ImageView(getRightColorPath(student));
                        image.setFitHeight(27);
                        image.setFitWidth(27);
                        image.setId(String.valueOf(studentPosition));
                        image.setOnMouseClicked((MouseEvent) ->
                        {
                            integerChoice_1 = Integer.parseInt(((Node)MouseEvent.getSource()).getId());
                        });
                        students.getChildren().add(image);
                    }
                    ParametersSlice.getChildren().clear();
                    ParametersSlice.getChildren().add(students);
                }
                else
                {
                    HBox islandChoice = new HBox();
                    Text hint = new Text();
                    hint.setText("Select the target island");
                    islandChoice.getChildren().add(hint);
                    ChoiceBox box = new ChoiceBox();
                    for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
                    {
                        box.getItems().add(i);
                    }
                    box.setOnAction((Event) ->
                    {
                        integerChoice_1 = box.getSelectionModel().getSelectedIndex();
                    });
                    islandChoice.getChildren().add(box);
                    ParametersSlice.getChildren().clear();
                    ParametersSlice.getChildren().add(islandChoice);
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
                VBox priestParameters = new VBox();
                priestParameters.getChildren().clear();

                //setup islands
                HBox islandChoice = new HBox();
                Text hint = new Text();
                hint.setText("Select the target island");
                islandChoice.getChildren().add(hint);
                ChoiceBox box = new ChoiceBox();
                for(int i = 0; i < view.getCurrentIslands().getIslands().size(); i++)
                {
                    box.getItems().add(i);
                }
                box.setOnAction((Event) ->
                {
                    integerChoice_1 = box.getSelectionModel().getSelectedIndex();
                });
                islandChoice.getChildren().add(box);

                priestParameters.getChildren().add(islandChoice);

                //setup students
                HBox students = new HBox();
                int studentPosition = 0;
                for(Student student : card.getStudentList())
                {
                    ImageView image = new ImageView(getRightColorPath(student));
                    image.setFitHeight(27);
                    image.setFitWidth(27);
                    image.setId(String.valueOf(studentPosition));
                    image.setOnMouseClicked((MouseEvent) ->
                    {
                        integerChoice_2 = Integer.parseInt(((Node)MouseEvent.getSource()).getId());
                    });
                    students.getChildren().add(image);
                }
                priestParameters.getChildren().add(students);

                ParametersSlice.getChildren().clear();
                ParametersSlice.getChildren().add(priestParameters);

                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            CharacterActivationParser activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName(), integerChoice_1, integerChoice_2);
                            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                            EffectPane.setVisible(false);
                            EffectPane.setMouseTransparent(true);
                        }
                );

                break;

            case COLOR:
                HBox Colors = new HBox();
                for(int i = 0; i < 5; i++)
                {
                    Student student = new Student(Col.values()[i]);
                    ImageView color = new ImageView(getRightColorPath(student));
                    color.setId(Col.values()[i].toString());
                    color.setOnMouseClicked((MouseEvent) -> {
                        colorChoice = Col.valueOf(((Node) MouseEvent.getSource()).getId());
                    });
                }
                ParametersSlice.getChildren().clear();
                ParametersSlice.getChildren().add(Colors);

                PlayButton.setOnMouseClicked((MouseEvent) ->
                        {
                            CharacterActivationParser activation = new CharacterActivationParser(GuiMainStarter.getClientGUI().getServerConnection().getNickname(), card.getName(), colorChoice);
                            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(activation.buildMessage()));
                            EffectPane.setVisible(false);
                            EffectPane.setMouseTransparent(true);
                        }
                );

                break;

            default:

        }
    }

    public void DisplayError(String error)
    {
        ErrorDisplay.setVisible(true);
        ErrorDisplay.setMouseTransparent(false);
        ErrorMessage.setText("");
        ErrorMessage.setText(error);
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

    public String getRightColorPath(Student s)
    {
        switch(s.getColor())
        {
            case GREEN:
                return "/Client/GUI/Images/Student/student_green.png";
            case YELLOW:
                return "/Client/GUI/Images/Student/student_yellow.png";
            case RED:
                return "/Client/GUI/Images/Student/student_red.png";
            case BLUE:
                return "/Client/GUI/Images/Student/student_blue.png";
            case PINK:
                return "/Client/GUI/Images/Student/student_pink.png";
            default:
                return "";
        }
    }

    public void setup(LightView view)
    {
        this.view = view;
        CloseError.setOnMouseClicked((MouseEvent) -> {
            ErrorDisplay.setVisible(false);
            ErrorDisplay.setMouseTransparent(true);
        });
    }

}
