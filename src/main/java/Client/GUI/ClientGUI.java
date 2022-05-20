package Client.GUI;

import Client.CLI.InputParser;
import Client.ClientView;
import Client.LightView;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.GameMode;
import Client.Messages.SetupMessages.ReadyStatus;
import Client.Messages.SetupMessages.WizardChoice;
import Client.ServerConnection;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.boards.token.Wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientGUI implements ClientView
{
    @Override
    public void run() {
        GuiMainStarter.main();
    }

    public ClientGUI getCLientGUi()
    {
        return this;
    }
































    /*cose copiate e incollate

     */




    private ServerConnection main;
    private InputParser stdin;
    private SerializedAnswer input;
    private Boolean setupState = true;
    private String currentInput;
    private LightView MyView = new LightView();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

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
            setupState = false;
        }

    }

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
    }



}
