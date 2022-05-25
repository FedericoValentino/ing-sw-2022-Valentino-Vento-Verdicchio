package Client.GUI;

import Client.ClientView;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.StandardActionAnswer;
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
    private int setupHandlerAnswerID=0;
    private ArrayList<Wizard> available= new ArrayList<Wizard>();

    @Override
    public void run() {


        GuiMainStarter.setClientGUI(this);
        GuiMainStarter.main();
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
                setupHandlerAnswerID=1;
                Platform.runLater(()->
                        {
                            String path= "/Client/GUI/Controllers/Lobby.fxml";
                            changeScene(path);
                        });
                break;
            case WIZARDS:
                setupHandlerAnswerID=2;
                ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
                System.out.println("instance of Avaiable Wizz");
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Wizard.fxml";
                    changeScene(path);
                });

                break;
            case GAME_NFO:
                setupHandlerAnswerID=3;
                System.out.println(((InfoMessage) answer).getInfo());
                System.out.println("Scelta fatta");
                if(setupState)
                {
                    setupState = false;
                }
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Waiting.fxml";
                    changeScene(path);
                });
                break;
            case GAME_START:
                setupHandlerAnswerID=4;
                System.out.println("Game Starting!");
                Platform.runLater(()->
                {
                    //devo capire quale screen proiettare
                    //String path="/GUI/Controllers/Wizard.fxml";
                    //changeScene(path);
                });
                break;
            default:
                System.out.println("Waiting");
                Platform.runLater(()->
                {
                    String path= "/Client/GUI/Controllers/Waiting.fxml";
                    changeScene(path);
                });

        }
    }

    public void changeScene(String path)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiMainStarter.getMainStage().setScene(scene);
    }





    @Override
    public void messageHandler(StandardActionAnswer answer) throws JsonProcessingException {
    }
    public Boolean getSetupState() {
        return setupState;
    }


    public void setServerConnection(String nickname,int team,String IP) throws IOException {
        serverConnection= new ServerConnection(nickname,team,IP);
        connection();
    }
    public ServerConnection getServerConnection()
    {
        return serverConnection;
    }

    private void connection() throws IOException {
        serverConnection.establishConnection();
        listenerGui = new ListenerGui(this);
        executor.execute(listenerGui);
    }


    public int getSetupHandlerAnswerID() {
        return setupHandlerAnswerID;
    }
    public void setSetupHandlerAnswerID(int s){setupHandlerAnswerID=s;}

    public ArrayList<Wizard> getAvailable() {
        return available;
    }














    /*cose copiate e incollate



    @Override
    public void setupHandler(StandardSetupAnswer answer) throws IOException {
        if(answer instanceof RequestGameInfo)
        {
            GameMode gm = new GameMode();
            System.out.println("First Client, what gamemode would you like to play?");
            System.out.println("Player number?[2][3][4]");
            gm.setMaxPlayers(stdin.getParser().nextInt());
            while(gm.getMaxPlayers() > 4 || gm.getMaxPlayers() < 2)
            {
                System.out.println("Wrong input");
                System.out.println("Player number?[2][3][4]");
                gm.setMaxPlayers(stdin.getParser().nextInt());
            }

            System.out.println("Expert Mode?[true][false]");
            gm.setExpertGame(stdin.getParser().nextBoolean());
            System.out.println(gm.isExpertGame());
            main.getOut().writeObject(gm);
            main.getOut().flush();
            main.getOut().reset();
        }
        if(answer instanceof AvailableWizards)
        {
            ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
            System.out.println("Which wizard are you playing with?");
            for(Wizard w : available)
            {
                System.out.print(String.valueOf("[" + w.ordinal()) + "] " + w + " ");
            }
            System.out.println();
            int choice = stdin.getParser().nextInt();
            while(choice > 4 || choice < 0)
            {
                System.out.println("You must enter a number between 0 and 4");
                choice = stdin.getParser().nextInt();
            }
            main.sendMessage(new SerializedMessage(new WizardChoice(Wizard.values()[choice])));
        }
        if(answer instanceof InfoMessage)
        {
            System.out.println(((InfoMessage) answer).getInfo());
            if(setupState)
            {
                setupState = false;
            }
        }
        if(answer instanceof GameStarting)
        {
            System.out.println("Game Starting!");
        }

    }

    @Override
    public void messageHandler(StandardActionAnswer answer) throws JsonProcessingException {
        if(answer instanceof ErrorMessage)
        {
            System.out.println(((ErrorMessage) answer).getError());
        }
        else if(answer instanceof StartTurn)
        {
            System.out.println("Your Turn, start playing!");
        }
        else if(answer instanceof ViewMessage)
        {
            MyView.parse((ViewMessage) answer);
        }
        else if(answer instanceof RequestCloud)
        {
            System.out.println(((RequestCloud) answer).getMessage());
        }
        else if(answer instanceof RequestCard)
        {
            System.out.println("Choose a Character Card!");
        }
        else if(answer instanceof RequestMoveStudent)
        {
            System.out.println("Move a student from your entrance!");
        }
        else if(answer instanceof RequestMotherNatureMove)
        {
            System.out.println("Move Mother Nature!");
        }
        else if(answer instanceof RequestCloud)
        {
            System.out.println(((RequestCloud) answer).getMessage());
        }
    }*/



}
