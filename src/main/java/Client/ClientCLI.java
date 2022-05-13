package Client;

import Client.Messages.ActionMessages.StandardActionMessage;
import Client.Messages.Message;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
import Server.Answers.ActionAnswers.*;
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
    private InputParser stdin;
    private SerializedAnswer input;
    private Boolean MyTurn = false;
    private String currentInput;
    private LightView myView = new LightView();


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
            if(stdin.getParser().next().equals("Ready"))
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
        }
        if(answer instanceof ViewMessage)
        {
            System.out.println("New view!");
            myView.parse((ViewMessage) answer);
        }
        if(answer instanceof RequestCloud)
        {
            System.out.println(((RequestCloud) answer).getMessage());
            MyTurn = true;
        }
        if(answer instanceof RequestCard)
        {
            System.out.println("Choose a Character Card!");
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
    public void run() throws IOException, ClassNotFoundException
    {

        main = new ServerConnection();
        SetupConnection setup = new SetupConnection(main.getNickname(), main.getTeam());
        main.getOut().writeObject(setup);
        main.getOut().flush();
        main.getOut().reset();
        this.stdin = new InputParser(main.getNickname());
        while(true)
        {
            System.out.println("Waiting for Server...");
            readMessage();
            if(MyTurn)
            {
                currentInput = stdin.getParser().next();
                StandardActionMessage action;
                do
                {
                    action = stdin.parseString(currentInput);
                    if(action != null)
                    {
                        main.sendMessage(new SerializedMessage(action));
                    }
                    else
                    {
                        System.out.println("Wrong Action");
                        currentInput = stdin.getParser().next();
                    }
                }while(action == null);


                MyTurn = false;
            }
        }


    }
}
