package Client.GUI;

import Client.ClientView;
import Client.GUI.Controllers.*;
import Client.LightView.LightView;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.ErrorMessage;
import Server.Answers.ActionAnswers.StandardActionAnswer;
import Server.Answers.ActionAnswers.ViewMessage;
import Server.Answers.ActionAnswers.WinMessage;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.boards.token.Wizard;

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


    @Override
    public void run() {
        guiMainStarter=new GuiMainStarter();
        lightView=MyView;
        guiMainStarter.setClientGUI(this);
        guiMainStarter.main();
    }

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
