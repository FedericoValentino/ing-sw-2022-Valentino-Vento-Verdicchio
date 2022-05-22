package Client.CLI;

import Client.ClientView;
import Client.LightView;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.boards.token.Wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientCLI implements ClientView
{
    private ServerConnection main;
    private InputParser stdin;
    private SerializedAnswer input;
    private Boolean setupState = true;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void setupHandler(StandardSetupAnswer answer) throws IOException {
        switch(answer.getType())
        {
            case GAME_NFO_REQ:
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
                break;
            case WIZARDS:
                ArrayList<Wizard> available = (((AvailableWizards) answer).getAvailable());
                System.out.println("Which wizard are you playing with?");
                for(Wizard w : available)
                {
                    System.out.print("[" + w.ordinal() + "] " + w + " ");
                }
                System.out.println();
                int choice = stdin.getParser().nextInt();
                while(choice > 4 || choice < 0)
                {
                    System.out.println("You must enter a number between 0 and 4");
                    choice = stdin.getParser().nextInt();
                }
                main.sendMessage(new SerializedMessage(new WizardChoice(Wizard.values()[choice])));
                break;
            case GAME_NFO:
                System.out.println(((InfoMessage) answer).getInfo());
                if(setupState)
                {
                    setupState = false;
                }
                break;
            case GAME_START:
                System.out.println("Game Starting!");
                break;
        }
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

    @Override
    public void readMessage() throws IOException, ClassNotFoundException {
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

    @Override
    public void run() throws IOException, ClassNotFoundException
    {
        Scanner info = new Scanner(System.in);
        System.out.println("Nickname?");
        String nickname = info.next();
        System.out.println("Team?");
        int team = info.nextInt();
        System.out.println("ServerIP?");
        String IP = info.next();

        main = new ServerConnection(nickname, team, IP);
        main.establishConnection();
        ListenerCLI Listener = new ListenerCLI(this);
        this.stdin = new InputParser(main, MyView);
        executor.execute(Listener);
        while(true)
        {
            if(!setupState)
            {
                stdin.newMove();
            }
        }
    }
}
