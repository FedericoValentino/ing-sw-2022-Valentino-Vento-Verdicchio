//TODO rimandare in caso di reject connection al menù di scelta con IP e Team
//TODO Scelta team up to server
//TODO sincronizzare su setupState

package Client.CLI;

import Client.ClientView;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.boards.token.Wizard;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientCLI implements ClientView
{
    private ServerConnection main;
    private InputParser stdin;
    private SerializedAnswer input;
    private Boolean setupState = true;
    private Object setupLock = new Object();
    private Scanner info = new Scanner(System.in);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void setupHandler(StandardSetupAnswer answer){
        switch(answer.getType())
        {
            case GAME_NFO_REQ:
                GameMode gm = new GameMode();
                System.out.println("First Client, what gamemode would you like to play?");
                System.out.println("Player number?[2][3][4]");
                gm.setMaxPlayers(Integer.parseInt(stdin.getParser().nextLine()));
                while(gm.getMaxPlayers() > 4 || gm.getMaxPlayers() < 2)
                {
                    System.out.println("Wrong input");
                    System.out.println("Player number?[2][3][4]");
                    gm.setMaxPlayers(Integer.parseInt(stdin.getParser().nextLine()));
                }

                System.out.println("Expert Mode?[true][false]");
                gm.setExpertGame(Boolean.parseBoolean(stdin.getParser().nextLine()));
                try
                {
                    main.getOut().writeObject(gm);
                    main.getOut().flush();
                    main.getOut().reset();
                }
                catch(IOException e)
                {
                    System.out.println("Server unreachable");
                }

                break;
            case WIZARDS:
                ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
                System.out.println("Which wizard are you playing with?");
                for(Wizard w : available)
                {
                    System.out.print("[" + w.ordinal() + "] " + w + " ");
                }
                System.out.println();
                int choice = Integer.parseInt(stdin.getParser().nextLine());
                while(choice > 4 || choice < 0)
                {
                    System.out.println("You must enter a number between 0 and 4");
                    choice = Integer.parseInt(stdin.getParser().nextLine());
                }
                main.sendMessage(new SerializedMessage(new WizardChoice(Wizard.values()[choice])));
                break;
            case GAME_NFO:
                AnsiConsole.out().println(((InfoMessage) answer).getInfo());
                if(getSetupState())
                {
                    setupState = false;
                    synchronized (setupLock)
                    {
                        setupLock.notify();
                    }
                }
                break;
            case GAME_START:
                System.out.println("Game Starting!");
                break;
            case REJECT:
                System.out.println(((RejectConnection)answer).getRejectionInfo());
                main.nicknameChange(info.next());
                try
                {
                    main.establishConnection();
                }
                catch(IOException e)
                {
                    System.out.println("Server unreachable");
                }
        }
    }

    @Override
    public void messageHandler(StandardActionAnswer answer) {

        switch(answer.getType())
        {
            case ERROR:
                AnsiConsole.out().println(((ErrorMessage) answer).getError());
                break;
            case START_NFO:
                System.out.println("Your Turn, start playing!");
                break;
            case VIEW:
                try
                {
                    MyView.parse((ViewMessage) answer);
                    stdin.printGame();
                    System.out.println(MyView.getCurrentTurnState().getGamePhase());
                    if(main.getNickname().equals(MyView.getCurrentTurnState().getCurrentPlayer()))
                    {
                        System.out.println(MyView.getInformations().informationCreator(MyView.getCurrentTurnState(), MyView.getCurrentTeams()).getInfoMessage());
                    }
                }
                catch(JsonProcessingException e)
                {
                    System.out.println("Error in processing view, show commands aren't available");
                }
                break;
            case CLOUD_REQ:
                System.out.println(((RequestCloud) answer).getMessage());
                break;
            case CARD_REQ:
                System.out.println("Choose an Assistant Card");
                break;
            case STUD_REQ:
                System.out.println("Move a student from your entrance");
                break;
            case MN_REQ:
                System.out.println("Move Mother Nature");
                break;
            case WIN:
                System.out.println(((WinMessage) answer).getWinningTeam());
                System.exit(0);
                break;
        }

    }

    @Override
    public void readMessage(){
        try
        {
            input = (SerializedAnswer) main.getIn().readObject();
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
            main.disconnect();
            System.exit(0);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Client couldn't understand Server");
        }

    }

    @Override
    public void run(){
        AnsiConsole.systemInstall();
        System.out.println("Nickname?");
        String nickname = info.nextLine();
        System.out.println("Team?");
        int team = Integer.parseInt(info.nextLine());
        while(team < 0 || team > 2)
        {
            System.out.println("ERROR: No such Team exists");
            team = Integer.parseInt(info.nextLine());
        }
        System.out.println("ServerIP?");
        String IP = info.nextLine();

        try
        {
            main = new ServerConnection(nickname, team, IP);
            main.establishConnection();
        }
        catch(IOException e)
        {
            System.out.println("Server unreachable or non existent");
            return;
        }
        ListenerCLI Listener = new ListenerCLI(this);
        this.stdin = new InputParser(main, MyView);
        executor.execute(Listener);
        while(main.getConnected())
        {
            synchronized (setupLock)
            {
                while(getSetupState())
                {
                    try {
                        setupLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(!getSetupState())
            {
                stdin.newMove();
            }
        }
    }

    public ServerConnection getMain() {
        return main;
    }

    public synchronized boolean getSetupState(){
        return setupState;
    }
}


