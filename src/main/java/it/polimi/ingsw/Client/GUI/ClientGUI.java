package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.InformationGenerator;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.IntroController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.LobbyController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.RejectionController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea.WinnerController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.SelectionControllers.ReadyController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.SelectionControllers.TeamController;
import it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.SelectionControllers.WizardController;
import it.polimi.ingsw.Client.ServerConnection;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ErrorMessage;
import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ViewMessage;
import it.polimi.ingsw.Server.Answers.ActionAnswers.WinMessage;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.Client.GUI.Controllers.*;
import it.polimi.ingsw.Server.Answers.SetupAnswers.AvailableTeams;
import it.polimi.ingsw.Server.Answers.SetupAnswers.AvailableWizards;
import it.polimi.ingsw.Server.Answers.SetupAnswers.InfoMessage;
import it.polimi.ingsw.Server.Answers.SetupAnswers.StandardSetupAnswer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Contains all the references needed to set up the GUI correctly, from the MainBoardController to the ServerConnection
 */
public class ClientGUI implements ClientView, InformationGenerator
{
    private GuiMainStarter guiMainStarter;
    private SerializedAnswer input;
    private ServerConnection serverConnection;
    private Boolean setupState = true;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ListenerGui listenerGui;
    private Boolean firstView = false;
    private MainBoardController mbc;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";


    /**
     * This method is called by the ClientApp when the user sets the type of view that he wants to use.
     * In particular this function creates a new instance of guiMainStarter, sets the lightView (using the object MyView
     * defined in the Client View interface), then sets the parameter ClientGUi in the GuiMainStarter on this
     * and then starts the guiMainStarter by calling its own method called main().
     */
    @Override
    public void run() {
        guiMainStarter=new GuiMainStarter();
        GuiMainStarter.setClientGUI(this);
        GuiMainStarter.main();
    }


    /**
     * This method replaces the current scene with the one that is contained in the loader.
     * @param loader contains the next scene to show
     */
    public void changeScene(FXMLLoader loader)
    {
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);
    }

    /**
     * Method readMessage handles the messages coming from the server and calls the right messageHandler for any occasion.
     * It also handles the correlated exceptions, generally by sending the client back to the Menu or intro screen
     */
    @Override
    public void readMessage()
    {
        try
        {
            input = (SerializedAnswer) serverConnection.getIn().readObject();
            if(input.getCommand() != null)
            {
                StandardSetupAnswer answer = input.getCommand();
                setupHandler(answer);
            }
            if(input.getAction() != null)
            {
                StandardActionAnswer answer = input.getAction();
                messageHandler(answer);
            }
        }
        catch(SocketTimeoutException e)
        {
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Winner.fxml"));
                GuiMainStarter.getClientGUI().changeScene(loader);
                WinnerController winnerControl = loader.getController();
                winnerControl.setGuiMainStarter(guiMainStarter);
                winnerControl.setup("Connection Lost");
            });
        }
        catch(IOException e)
        {
            serverConnection.disconnect();
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Winner.fxml"));
                GuiMainStarter.getClientGUI().changeScene(loader);
                WinnerController winnerControl = loader.getController();
                winnerControl.setGuiMainStarter(guiMainStarter);
                winnerControl.setup("Connection Lost");
            });
        }
        catch(ClassNotFoundException e)
        {
        }

    }

    /**
     * This method takes the answer object and, based on its type, chooses the next scene to show and displays it
     * using the Platform.runLater method. Inside it hosts a call to the changeScene method and a setting of
     * the current instance of the guiMainStarter into the right loader.
     * In particular if it's a: -GAME_NFO_REQ it shows the Lobby scene where the first user set the number of team
     *                                       and the difficulty of the game;
     *                          -TEAM it shows the Team scene where the user choose the team;
     *                          -WIZARD it shows the Wizard scene where the user choose the wizard, according to which is
     *                                  available;
     *                          -GAME_INFO it shows the ready scene where the user can set the ready status only when
     *                                     it's effectively ready to start the game. It also ends the setup phase setting the
     *                                     setupState to false;
     *                          -GAME_START it shows the waiting scene, that will be replaced when a new message from
     *                                     the server is received;
     *                          -REJECT it shows the disconnect scene in which the player must return to the intro scene
     *                                  clicking the Menu button.
     *
     * @param answer is the object that it's received from the server.
     */
    @Override
    public void setupHandler(StandardSetupAnswer answer)
    {
        switch(answer.getType())
        {
            case GAME_NFO_REQ:
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Lobby.fxml";
                    FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                    changeScene(load);
                    LobbyController lc=load.getController();
                    lc.setGuiMainStarter(guiMainStarter);
                });
                break;
            case TEAMS:
                Platform.runLater(()->
                {
                    int[] availableTeams = ((AvailableTeams) answer).getAvailableTeams();
                    String path = "/Client/GUI/Controllers/Teams.fxml";
                    FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                    changeScene(load);
                    TeamController tc = load.getController();
                    tc.setGuiMainStarter(guiMainStarter);
                    tc.init(availableTeams);
                });
                break;
            case WIZARDS:
                Platform.runLater(()->
                {
                    ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
                    String path= "/Client/GUI/Controllers/Wizard.fxml";
                    FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                    changeScene(load);
                    WizardController wc=load.getController();
                    wc.setGuiMainStarter(guiMainStarter);
                    wc.updateOpacity(available);
                });
                break;
            case GAME_NFO:
                    Platform.runLater(() ->
                    {
                        String path = "/Client/GUI/Controllers/Ready.fxml";
                        FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                        changeScene(load);
                        ReadyController rc = load.getController();
                        rc.setGuiMainStarter(guiMainStarter);
                    });
                    if (setupState) {
                        setupState = false;
                    }
                break;
            case GAME_START:
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/WaitingGameStart.fxml";
                    FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                    changeScene(load);
                });
                break;
            case REJECT:
                serverConnection.disconnect();
                Platform.runLater(() ->
                {
                    String path = "/Client/GUI/Controllers/RejectConnection.fxml";
                    FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                    changeScene(load);
                    RejectionController controller = load.getController();
                    controller.setGuiMainStarter(guiMainStarter);
                    controller.setup();
                });
                break;
        }
    }


    /**
     * This method takes the answer object and, based on its type, it chooses the right instructions.
     * If the type is: ERROR, it shows the error;
     *                 VIEW, it replaces the content of the current view with the one that is stored in the answer.
     *                       If it's the first time receiving this message it also uses the Platform.runLater method
     *                       to replace the scene with the mainBoard one, and then calls all the initial setup methods
     *                       contained in all the controllers linked to a part of the mainBoard scene;
     *                 WIN, it disconnects the client, showing the win scene and calling the setup method of the related
     *                      controller.
     * @param answer is the object that it's received from the server.
     */
    @Override
    public void messageHandler(StandardActionAnswer answer){

        switch(answer.getType())
        {
            case ERROR:
                if(firstView)
                {
                    mbc.DisplayError(errorGenerator(((ErrorMessage)answer).getError(), MyView).getInfoMessage());
                }
                break;
            case VIEW:
                try {
                    MyView.parse((ViewMessage) answer);
                    if (!firstView) {
                        Platform.runLater(() ->
                        {
                            String path = "/Client/GUI/Controllers/MainBoard.fxml";
                            FXMLLoader load = new FXMLLoader(getClass().getResource(path));
                            changeScene(load);
                            mbc = load.getController();
                            mbc.setGuiMainStarter(guiMainStarter);
                            try
                            {
                                mbc.setup(MyView);
                                mbc.initialSetupOtherSchool(MyView);
                                mbc.initialSetupAssistantCard(MyView.getCurrentTeams());
                                mbc.initialSetupIsland(MyView);
                                mbc.initialSetupMineSchool();
                                mbc.initialSetupCharacterCard(MyView.getCurrentCharacterDeck(), MyView.getCurrentActiveCharacterCard());
                                mbc.initialSetupPropaganda(MyView);
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        });
                        firstView = true;
                    }
                }catch(JsonProcessingException e)
                {

                }
                break;
            case WIN:
                serverConnection.disconnect();
                Platform.runLater(() -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Winner.fxml"));
                    GuiMainStarter.getClientGUI().changeScene(loader);
                    WinnerController winnerControl = loader.getController();
                    winnerControl.setGuiMainStarter(guiMainStarter);
                    winnerControl.setup(((WinMessage)answer).getWinningTeam());
                });
                break;

        }

    }

    /**
     * This method tries to establish the connection to the server, identified by the IP parameter: if it's all correct
     * it also creates and start a new Listener GUI. If there are IOException it replaces the current scene with the
     * intro one.
     * @param nickname is the name choose by the player
     * @param IP is the String where the player has specified the IP destination
     */
    public void setServerConnection(String nickname, String IP)
    {
        try
        {
            serverConnection= new ServerConnection(nickname,IP);
            serverConnection.establishConnection();
        }
        catch(IOException e)
        {
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
                GuiMainStarter.getClientGUI().changeScene(loader);
                IntroController intro = loader.getController();
                intro.setGuiMainStarter(guiMainStarter);
            });
        }
        listenerGui = new ListenerGui(this);
        executor.execute(listenerGui);
    }

    public ServerConnection getServerConnection()
    {
        return serverConnection;
    }
}
