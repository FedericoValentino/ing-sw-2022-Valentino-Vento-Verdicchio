package Server;
//TODO Smistare metodi
//TODO Spostare GamePhase dentro al mainController + metodo per avanzare di GamePhase
//TODO Modificare le funzioni di effetto per essere standardizzate a 5 input
//TODO Usare Executor per lanciare thread

import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.Disconnect;
import Client.Messages.SetupMessages.ReadyStatus;
import Client.Messages.SetupMessages.StandardSetupMessage;
import Client.Messages.SetupMessages.WizardChoice;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SetupAnswers.AvailableWizards;
import Server.Answers.SetupAnswers.GameStarting;
import Server.Answers.SetupAnswers.StandardSetupAnswer;
import controller.CharacterController;
import controller.MainController;
import model.boards.token.Wizard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class GameHandler extends Thread
{
    private ClientConnection socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MainController mainController;
    private boolean ready;
    private GamePhase phase;
    private int planning;

    public GameHandler(MainController m, ClientConnection s, ObjectInputStream in, ObjectOutputStream out) throws IOException {

        this.socket = s;
        this.in = in;
        this.out = out;
        this.mainController = m;
        this.ready = false;
        this.phase = GamePhase.SETUP;
        this.planning = 0;
    }

    public void setupHandler(StandardSetupMessage message) throws IOException {
        if(message instanceof WizardChoice)
        {
            synchronized (mainController.getAvailableWizards())
            {
                Wizard w = ((WizardChoice) message).getWizard();
                if(mainController.getAvailableWizards().contains(w) && MainController.findPlayerByName(mainController.getGame(), socket.getNickname()) == null)
                {
                    mainController.AddPlayer(0, socket.getNickname(), 8, w);
                    mainController.getAvailableWizards().remove(w);
                }
            }
        }
        if(message instanceof ReadyStatus)
        {
            if(!ready)
            {
                mainController.readyPlayer();
                ready = true;
                if(mainController.getReadyPlayers() == mainController.getPlayers())
                {
                    phase = GamePhase.GAMEREADY;
                }
            }
        }
        if(message instanceof Disconnect)
        {
            socket.getClient().close();

        }

    }

    public void planningHandler(StandardActionMessage message)
    {
        if(message instanceof ChooseCloud)
        {
            mainController.getPlanningController()
                    .drawStudentForClouds(mainController.getGame(), ((ChooseCloud) message).getCloudIndex());
            planning++;
        }
        if(message instanceof DrawAssistantCard)
        {
            mainController.getPlanningController()
                    .drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
            if(mainController.lastPlayer())
            {
                mainController.updateTurnState();
                mainController.determineNextPlayer();
                phase = GamePhase.ACTION;
            }
            else
            {
                mainController.determineNextPlayer();
            }
            planning = 0;
        }
    }

    public void actionHandler(StandardActionMessage message) throws IOException {
        if(message instanceof MoveStudent)
        {
            if(((MoveStudent) message).isToIsland())
            {
                mainController.getActionController()
                        .placeStudentToIsland(((MoveStudent) message).getEntrancePos(),
                                ((MoveStudent) message).getIslandId(),
                                mainController.getGame(),
                                socket.getNickname());
            }

        }
        if(message instanceof MoveMN)
        {
            if(mainController.getActionController().possibleMNmove(((MoveMN) message).getAmount(), mainController.getGame()))
            {
                mainController.getActionController().MoveMN(((MoveMN) message).getAmount(), mainController.getGame());
            }
            else
            {
                out.writeObject(new ErrorMessage("Too much movement"));
            }
        }

        if(message instanceof DrawFromPouch)
        {
            if(mainController.getActionController().isCloudEmpty(((DrawFromPouch) message).getCloudIndex(), mainController.getGame()))
            {
                out.writeObject(new ErrorMessage("You selected an empty Cloud"));
            }
            else
            {
                mainController.getActionController().drawFromClouds(((DrawFromPouch) message).getCloudIndex(), mainController.getGame(), socket.getNickname());
            }
        }
        if(message instanceof EndTurn)
        {
            if(mainController.lastPlayer())
            {
                mainController.updateTurnState();
                mainController.determineNextPlayer();
                phase = GamePhase.PLANNING;
            }
            else
            {
                mainController.determineNextPlayer();
            }
        }
    }


    public void characterHandler(StandardActionMessage message) throws  IOException
    {
        if(message instanceof PlayCharacter)
        {
            if(CharacterController.isPickable(mainController.getGame(),
                    ((PlayCharacter) message).getCharacterId(),
                    MainController.findPlayerByName(mainController.getGame(), socket.getNickname())))
            {
                mainController.getCharacterController().pickCard(mainController.getGame(),((PlayCharacter) message).getCharacterId(), MainController.findPlayerByName(mainController.getGame(), socket.getNickname()));
            }
        }
        if(message instanceof PlayCharacterEffect)
        {
            if(mainController.getCharacterController().isEffectPlayable(mainController.getGame(),((PlayCharacterEffect)message).getCharacterId()))
            {
               // mainController.getCharacterController().effect(mainController.getGame().getCurrentActiveCharacterCard().get(mainController.getCharacterController().getCardByID(mainController.getGame(), ((PlayCharacterEffect) message).getCharacterId())), mainController.getGame(), ((PlayCharacterEffect) message).getFirst(), ((PlayCharacterEffect) message).getSecond(), ((PlayCharacterEffect) message).getThird(), ((PlayCharacterEffect) message).getStudentColor()));
            }
        }
    }


    public void messageHandler(StandardActionMessage message) throws IOException {
        if(socket.getNickname().equals(mainController.getCurrentPlayer()))
        {
            if(phase == GamePhase.PLANNING)
            {
                planningHandler(message);
            }
            if(phase == GamePhase.ACTION)
            {
                actionHandler(message);
            }
        }
        else
        {
            out.writeObject(new ErrorMessage("Not your turn!"));
        }

    }

    public void readMessage() throws IOException, ClassNotFoundException {
        SerializedMessage input = (SerializedMessage) in.readObject();
        if(input.getCommand() != null)
        {
            StandardSetupMessage message = input.getCommand();
            setupHandler(message);
        }
        if(input.getAction() != null)
        {
            StandardActionMessage message = input.getAction();
            messageHandler(message);
        }
    }

    @Override
    public void run()
    {
        try
        {
            while(isAlive())
            {
                if(phase == GamePhase.SETUP)
                {
                    out.writeObject(new AvailableWizards(mainController.getAvailableWizards()));
                }
                if(phase == GamePhase.GAMEREADY)
                {
                    out.writeObject(new GameStarting());
                    mainController.updateTurnState();
                    phase = GamePhase.PLANNING;
                }
                if(phase == GamePhase.PLANNING)
                {
                    out.writeObject(new StartTurn());
                    if(planning == 0)
                    {
                        out.writeObject(new RequestCloud());
                    }
                    if(planning == 1)
                    {
                        out.writeObject(new RequestCard());
                    }
                }
                readMessage();
            }
        }
        catch(IOException e)
        {

        }
        catch(ClassNotFoundException e)
        {

        }



    }
}
