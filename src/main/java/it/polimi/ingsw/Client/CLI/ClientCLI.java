package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Printers.PrinterCLI;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.InformationGenerator;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.ServerConnection;
import it.polimi.ingsw.Server.Answers.ActionAnswers.*;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.Client.Messages.SetupMessages.GameMode;
import it.polimi.ingsw.Client.Messages.SetupMessages.TeamChoice;
import it.polimi.ingsw.Client.Messages.SetupMessages.WizardChoice;
import it.polimi.ingsw.Server.Answers.SetupAnswers.*;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClientCLI is the main class for the CLI client, it hosts methods to handle the reception of messages and to get input from keyboard.
 */
public class ClientCLI implements ClientView, InformationGenerator
{
    private ServerConnection main;
    private InputParser stdin;
    private SerializedAnswer input;
    private boolean setupState = true;
    private final Object setupLock = new Object();
    private Scanner info = new Scanner(System.in);
    private ExecutorService executor = Executors.newSingleThreadExecutor();



    @Override
    public void setupHandler(StandardSetupAnswer answer){
        switch(answer.getType())
        {
            case PING:
                //System.out.println("Server pinged do i Pong?");
                break;
            case GAME_NFO_REQ:
                GameMode gm = new GameMode();
                System.out.println("First Client, what gamemode would you like to play?");
                System.out.println("Player number?[2][3][4]");
                gm.setMaxPlayers(stdin.integerParser());
                while(gm.getMaxPlayers() > 4 || gm.getMaxPlayers() < 2)
                {
                    System.out.println("Wrong input");
                    System.out.println("Player number?[2][3][4]");
                    gm.setMaxPlayers(stdin.integerParser());
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
            case TEAMS:
                int[] availableTeams = ((AvailableTeams) answer).getAvailableTeams();
                System.out.println("What Team are you playing in?");
                for(int i = 0; i < 3; i++)
                {
                    System.out.print("[" + i + "] " + ColTow.values()[i] + ": " + availableTeams[i] + " Slots ");
                }
                System.out.println();
                int teamChoice = stdin.integerParser();
                while(teamChoice > 2 || teamChoice < 0)
                {
                    System.out.println("You must enter a number between 0 and 2");
                    teamChoice = stdin.integerParser();
                }
                main.sendMessage(new SerializedMessage(new TeamChoice(teamChoice)));
                break;
            case WIZARDS:
                ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
                System.out.println("Which wizard are you playing with?");
                for(Wizard w : available)
                {
                    System.out.print("[" + w.ordinal() + "] " + w + " ");
                }
                System.out.println();
                int choice = stdin.integerParser();
                while(choice > 4 || choice < 0)
                {
                    System.out.println("You must enter a number between 0 and 4");
                    choice = stdin.integerParser();
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
                System.out.println(errorGenerator(((ErrorMessage)answer).getError(), MyView).getInfoMessage());
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
                        System.out.println(informationCreator(MyView.getCurrentTurnState(), MyView.getCurrentTeams()).getInfoMessage());
                    }
                }
                catch(JsonProcessingException e)
                {
                    System.out.println("Error in processing view, show commands aren't available");
                }
                break;
            case WIN:
                System.out.println(((WinMessage) answer).getWinningTeam());
                System.exit(0);
                break;
        }

    }
//
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
        catch(SocketTimeoutException e)
        {
            main.disconnect();
            System.out.println("Connection to server lost");
            System.exit(0);
        }
        catch(IOException e)
        {
            main.disconnect();
            System.out.println("Something went wrong, your game was stopped");
            System.exit(0);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Client couldn't understand Server");
        }
    }

    /**
     * Method run is the main method of the CLI. after getting username and IP the method creates all the structures necessary
     * for the CLI to work properly and starts the ListenerCLI thread
     */
    @Override
    public void run(){
        AnsiConsole.systemInstall();
        PrinterCLI.cls();
        PrinterCLI.printTitle();
        System.out.println("Nickname?");
        String nickname = info.nextLine();
        checkUsername(nickname);
        System.out.println("ServerIP?");
        String IP = info.nextLine();
        try
        {
            main = new ServerConnection(nickname, IP);
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


