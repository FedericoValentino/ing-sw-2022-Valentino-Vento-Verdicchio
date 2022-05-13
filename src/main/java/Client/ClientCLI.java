package Client;

import Client.Messages.ActionMessages.StandardActionMessage;
import Client.Messages.Message;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
import Server.Answers.ActionAnswers.ErrorMessage;
import Server.Answers.ActionAnswers.StandardActionAnswer;
import Server.Answers.ActionAnswers.StartTurn;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import model.boards.token.Wizard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientCLI implements ClientView
{
    private ServerConnection main;
    private Scanner stdin = new Scanner(System.in);
    private SerializedAnswer input;
    private Boolean MyTurn = false;
    private String currentInput;


    public void setupHandler(StandardSetupAnswer answer) throws IOException {
        if(answer instanceof RequestGameInfo)
        {
            GameMode gm = new GameMode();
            System.out.println("First Client, what gamemode would you like to play?");
            System.out.println("Player number?");
            gm.setMaxPlayers(stdin.nextInt());
            System.out.println("Expert Mode?");
            gm.setExpertGame(stdin.nextBoolean());
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
            int choice = stdin.nextInt();
            main.sendMessage(new SerializedMessage(new WizardChoice(Wizard.values()[choice])));
        }
        if(answer instanceof InfoMessage)
        {
            System.out.println(((InfoMessage) answer).getInfo());
            if(stdin.next().equals("Ready"))
            {
                main.sendMessage(new SerializedMessage(new ReadyStatus()));
            }
        }
        if(answer instanceof GameStarting)
        {
            System.out.println("Game Starting!");
        }

    }

    public void messageHandler(StandardActionAnswer answer)
    {
        if(answer instanceof ErrorMessage)
        {
            System.out.println(((ErrorMessage) answer).getError());
        }
        if(answer instanceof StartTurn)
        {
            System.out.println("Your Turn, start playing!");
            MyTurn = true;
        }
    }

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
    public void run() throws IOException, ClassNotFoundException {

        main = new ServerConnection();
        SetupConnection setup = new SetupConnection(main.getNickname(), main.getTeam());
        main.getOut().writeObject(setup);
        main.getOut().flush();
        main.getOut().reset();

        while(true)
        {
            System.out.println("Waiting for Server...");
            readMessage();
            if(MyTurn)
            {
                currentInput = stdin.next();

            }
        }


    }
}
