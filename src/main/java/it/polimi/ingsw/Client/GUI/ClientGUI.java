package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.LightView.LightView;
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
import it.polimi.ingsw.model.boards.token.Wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientGUI implements ClientView
{
    private GuiMainStarter guiMainStarter;
    private SerializedAnswer input;
    private ServerConnection serverConnection;
    private Boolean setupState = true;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ListenerGui listenerGui;
    private LightView lightView;
    private Boolean firstView = false;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";


    /**
     * This method is called by the ClientApp when the user set the type of view that want to use.
     * In particular this function create a new instance of guiMainStarter, set the lightView (using the object MyView
     * that is defined in the Client View interface), then set the parameter ClientGUi in the GuiMainStarter on this
     * and then start the guiMainStarter calling its own method called main().
     */
    @Override
    public void run() {
        guiMainStarter=new GuiMainStarter();
        lightView=MyView;
        GuiMainStarter.setClientGUI(this);
        GuiMainStarter.main();
    }


    /**
     * This method replace the current scene with the one that is contained in the loader.
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
     * to do (because I don't know exactly what to write in it)
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
        catch(IOException e)
        {
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/GUI/Controllers/Intro.fxml"));
                changeScene(loader);
                IntroController intro = loader.getController();
                intro.setGuiMainStarter(guiMainStarter);
            });
        }
        catch(ClassNotFoundException e)
        {

        }

    }

    /**
     * This method take the answer object and based on the type of it choose the next scene to show and display it
     * using the Platform.runLater method. Inside it has a call to the changeScene method and a setting of
     * the current instance of the guiMainStarter into the right loader.
     * In particular if it's a: -GAME_NFO_REQ it shows the Lobby scene where the first user set the number of team
     *                                       and the difficulty of the game;
     *                          -TEAM it shows the Team scene where the user choose the team;
     *                          -WIZARD it shows the Wizard scene where the user choose the wizard, according to which is
     *                                  available;
     *                          -GAME_INFO it shows the ready scene where the user can set the ready status only when
     *                                     it's effectively ready to start the game. It also end the setup phase setting the
     *                                     setupState to false;
     *                          -GAME_START it shows the waiting scene, that will replaced when a new message from
     *                                     the server is received;
     *                          -REJECT it shows the disconnect scene in which the player must return to the intro scene
     *                                  clicking the Menù button.
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
                if(((InfoMessage) answer).getInfo().equals("Wizard Selected, type " + ANSI_GREEN + "[Ready] " + ANSI_RESET + "if you're ready to start!"))
                {
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
     * This method take the answer object and based on the type of it choose the right instructions.
     * If the type is: ERROR, it shows the error;
     *                 VIEW, it replace the content of the current view with the one that is stored in the answer.
     *                       If it's the first time that receive this message it also use the Platform.runLater method
     *                       to replace the scene with the mainBoard one and then call all the initial setup methods
     *                       contained in all the controller linked to a part of the mainBoard scene;
     *                 WIN, it disconnect the client showing the win scene and calling the setup method of the related
     *                      controller.
     * @param answer is the object that it's received from the server.
     */
    @Override
    public void messageHandler(StandardActionAnswer answer){

        switch(answer.getType())
        {
            case ERROR:
                System.out.println(((ErrorMessage) answer).getError());
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
                            MainBoardController mbc = load.getController();
                            mbc.setGuiMainStarter(guiMainStarter);
                            try
                            {
                                mbc.initialSetupOtherSchool(lightView.getCurrentTeams());
                                mbc.initialSetupAssistantCard(lightView.getCurrentTeams());
                                mbc.initialSetupIsland(lightView);
                                mbc.initialSetupMineSchool(lightView.getCurrentTeams());
                                mbc.initialSetupCharacterCard(lightView.getCurrentCharacterDeck(), lightView.getCurrentActiveCharacterCard());
                                mbc.initialSetupPropaganda(lightView, lightView.getInformations());
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
     * This method try to establish the connection to the server identified by the IP parameter and if it's all correct
     * it also create and start a new Listener GUI. If there are IOException it replaces the current scene with the
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

    public LightView getLightView() {
        return lightView;
    }
}