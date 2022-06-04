package Client.GUI;

import Client.ClientView;
import Client.GUI.Controllers.*;
import Client.LightView.LightView;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.ErrorMessage;
import Server.Answers.ActionAnswers.RequestCloud;
import Server.Answers.ActionAnswers.StandardActionAnswer;
import Server.Answers.ActionAnswers.ViewMessage;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXML;
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


    @Override
    public void run() {
        guiMainStarter=new GuiMainStarter();
        lightView=MyView;
        guiMainStarter.setClientGUI(this);
        guiMainStarter.main();
    }


    @Override
    public void readMessage() throws IOException, ClassNotFoundException {
        input = (SerializedAnswer) serverConnection.getIn().readObject();
        if(input.getCommand() != null)
        {
            System.out.println("Intro nel read message in comando standard setup");
            StandardSetupAnswer answer = input.getCommand();
            setupHandler(answer);
        }
        if(input.getAction() != null)
        {
            StandardActionAnswer answer = input.getAction();
            messageHandler(answer);
        }
    }

    @Override
    public void setupHandler(StandardSetupAnswer answer) throws IOException {
        System.out.println(Thread.currentThread());
        switch(answer.getType())
        {
            case GAME_NFO_REQ:
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Lobby.fxml";
                    FXMLLoader load=changeScene(path);
                    LobbyController lc=load.getController();
                    lc.setGuiMainStarter(guiMainStarter);
                });
                break;
            case TEAMS:
                Platform.runLater(()->
                {
                    int[] availableTeams = ((AvailableTeams) answer).getAvailableTeams();
                    String path = "/Client/GUI/Controllers/Teams.fxml";
                    FXMLLoader load = changeScene(path);
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
                    FXMLLoader load=changeScene(path);
                    WizardController wc=load.getController();
                    wc.setGuiMainStarter(guiMainStarter);
                    wc.updateOpacity(available);
                });

                break;
            case GAME_NFO:
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Ready.fxml";
                    FXMLLoader load=changeScene(path);
                    ReadyController rc=load.getController();
                    rc.setGuiMainStarter(guiMainStarter);
                });
                if(setupState)
                {
                    setupState = false;
                }
                break;
            case GAME_START:
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/WaitingGameStart.fxml";
                    changeScene(path);
                });
                break;
            /*default:
                System.out.println("Waiting");
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Waiting.fxml";
                    changeScene(path);
                });*/

        }
    }

    public FXMLLoader changeScene(String path)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);
        return loader;
    }


    @Override
    public void messageHandler(StandardActionAnswer answer) throws JsonProcessingException {

        switch(answer.getType())
        {
            case ERROR:
                System.out.println(((ErrorMessage) answer).getError());
                break;
            case START_NFO:
                System.out.println("Your Turn, start playing!");
                break;
            case VIEW:
                MyView.parse((ViewMessage) answer);
                if(!firstView)
                {
                    Platform.runLater(()->
                    {
                        String path= "/Client/GUI/Controllers/MainBoard.fxml";
                        FXMLLoader fxml=changeScene(path);
                        MainBoardController mbc=fxml.getController();
                        mbc.setGuiMainStarter(guiMainStarter);
                        try {
                            mbc.initialSetupOtherSchool(lightView.getCurrentTeams());
                            mbc.initialSetupAssistantCard(lightView.getCurrentTeams());
                            mbc.initialSetupIsland(lightView.getCurrentIslands());
                            mbc.initialSetupMineSchool(lightView.getCurrentTeams());
                            mbc.initialSetupCharacterCard(lightView.getCurrentCharacterDeck(), lightView.getCurrentActiveCharacterCard());
                            //altri setup      mbc.;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                    firstView = true;
                }
                break;
            case CLOUD_REQ:
                System.out.println(((RequestCloud) answer).getMessage());
                break;
            case CARD_REQ:
                System.out.println("Choose a Character Card!");
                break;
            case STUD_REQ:
                System.out.println("Move a student from your entrance!");
                break;
            case MN_REQ:
                System.out.println("Move Mother Nature!");
                break;
        }

    }



    public void setServerConnection(String nickname, String IP) throws IOException {
        serverConnection= new ServerConnection(nickname,IP);
        serverConnection.establishConnection();
        listenerGui = new ListenerGui(this);
        executor.execute(listenerGui);
    }
    public ServerConnection getServerConnection()
    {
        return serverConnection;
    }
    public Boolean getSetupState() {
        return setupState;
    }

    public LightView getLightView() {
        return lightView;
    }
}
