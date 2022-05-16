package Server;



import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.Disconnect;
import Client.Messages.SetupMessages.ReadyStatus;
import Client.Messages.SetupMessages.StandardSetupMessage;
import Client.Messages.SetupMessages.WizardChoice;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.AvailableWizards;
import Server.Answers.SetupAnswers.GameStarting;
import Server.Answers.SetupAnswers.InfoMessage;
import controller.CharacterController;
import controller.MainController;
import controller.Checks;
import model.boards.token.GamePhase;
import model.boards.token.Wizard;
import Observer.Observer;

import java.io.IOException;
import java.util.concurrent.Semaphore;


public class GameHandler extends Thread implements Observer
{
    private ClientConnection socket;
    private MainController mainController;
    private boolean ready;
    private boolean choseWizard;
    private int planning;
    private int action;
    private int team;
    private Semaphore threadSem;
    private Semaphore globalSem;

    public GameHandler(MainController m, ClientConnection s, int team, Semaphore sem) throws IOException
    {

        this.socket = s;
        this.mainController = m;
        this.ready = false;
        this.choseWizard = false;
        this.planning = 0;
        this.action = 0;
        this.team = team;
        this.globalSem = sem;
        this.threadSem = new Semaphore(0);
    }

    public void setupHandler(StandardSetupMessage message) throws IOException
    {
        if(message instanceof WizardChoice)
        {
            synchronized (mainController.getAvailableWizards())
            {
                Wizard w = ((WizardChoice) message).getWizard();
                if(mainController.getAvailableWizards().contains(w) && MainController.findPlayerByName(mainController.getGame(), socket.getNickname()) == null)
                {
                    mainController.AddPlayer(team, socket.getNickname(), 8, w);
                    mainController.getAvailableWizards().remove(w);
                    socket.sendAnswer(new SerializedAnswer(new InfoMessage("Wizard Selected, type [Ready] if you're ready to start!")));
                    choseWizard = true;
                    threadSem.release(1);
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Sorry, wrong input or Wizard already taken")));
                    threadSem.release(1);
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
                    System.out.println("All players ready!");
                    mainController.updateTurnState();
                    mainController.determineNextPlayer();
                    mainController.Setup();
                    mainController.resetReady();
                    mainController.updateGamePhase(GamePhase.GAMEREADY);
                }
                else
                {
                    System.out.println("Waiting for all players to be ready");
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
        if(message instanceof DrawFromPouch && planning == 0)
        {
            mainController.getPlanningController()
                    .drawStudentForClouds(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex());
            planning++;
            threadSem.release(1);
        }
        if(message instanceof DrawAssistantCard && planning == 1)
        {
            mainController.getPlanningController()
                    .drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
            if(mainController.getChecks().isLastPlayer(mainController.getGame()))
            {
                mainController.updateGamePhase(GamePhase.ACTION);
                mainController.updateTurnState();
                mainController.determineNextPlayer();
                threadSem.release(1);
            }
            else
            {
                mainController.determineNextPlayer();
                threadSem.release(1);
            }
            planning = 0;
        }
    }

    public void actionHandler(StandardActionMessage message)
    {

        if(message instanceof MoveStudent && (action >= 0 && action <= 2))
        {
            if(((MoveStudent) message).isToIsland())
            {
                mainController.getActionController()
                        .placeStudentToIsland(((MoveStudent) message).getEntrancePos(),
                                ((MoveStudent) message).getIslandId(),
                                mainController.getGame(),
                                socket.getNickname());
            }
            else
            {
                mainController.getActionController()
                        .placeStudentToDiningRoom(((MoveStudent) message).getEntrancePos(),
                                                    mainController.getGame(),
                                                    socket.getNickname());
            }
            action++;
            threadSem.release(1);
        }
        if(message instanceof MoveMN && action == 3)
        {
            if(mainController.getChecks().isAcceptableMovementAmount(mainController.getGame(), mainController.getCurrentPlayer(), ((MoveMN) message).getAmount()))
            {
                mainController.getActionController().MoveMN(((MoveMN) message).getAmount(), mainController.getGame());
            }
            else
            {
                socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Too much movement")));
            }
            action++;
            threadSem.release(1);
        }

        if(message instanceof ChooseCloud && action == 4)
        {
            if(!mainController.getChecks().isCloudAvailable(mainController.getGame(), ((ChooseCloud) message).getCloudIndex()))
            {
                socket.sendAnswer(new SerializedAnswer(new ErrorMessage("You selected an empty Cloud")));
            }
            else
            {
                mainController.getActionController().drawFromClouds(((ChooseCloud) message).getCloudIndex(), mainController.getGame(), socket.getNickname());
            }
            action++;
            threadSem.release(1);
        }
        if(message instanceof EndTurn && action == 5)
        {
            if(mainController.getChecks().isLastPlayer(mainController.getGame()))
            {
                mainController.updateGamePhase(GamePhase.PLANNING);
                mainController.updateTurnState();
                mainController.determineNextPlayer();
            }
            else
            {
                mainController.determineNextPlayer();
            }
            action = 0;
            threadSem.release(1);
        }

    }

    public void characterHandler(StandardActionMessage message)
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
                mainController.getCharacterController().playEffect(((PlayCharacterEffect) message).getCharacterId(), mainController.getGame(), ((PlayCharacterEffect) message).getFirst(), ((PlayCharacterEffect) message).getSecond(), ((PlayCharacterEffect) message).getThird(), ((PlayCharacterEffect) message).getStudentColor());
            }
        }
        threadSem.release(1);
    }

    public void messageHandler(StandardActionMessage message)
    {
        if(socket.getNickname().equals(mainController.getCurrentPlayer()))
        {
            if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.PLANNING));
            {
                planningHandler(message);
            }
            if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.ACTION))
            {
                if(message instanceof PlayCharacter || message instanceof PlayCharacterEffect)
                {
                    characterHandler(message);
                }
                else
                {
                    actionHandler(message);
                }
            }
        }
        else
        {
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Not your turn!")));
        }

    }

    public void readMessage() throws IOException, ClassNotFoundException, InterruptedException
    {
        SerializedMessage input = (SerializedMessage) socket.getInputStream().readObject();
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
            MessageReceiver receiver = new MessageReceiver(this);
            receiver.start();
            while(isAlive())
            {
                if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.SETUP) && !choseWizard)
                {
                    socket.sendAnswer(new SerializedAnswer(new AvailableWizards(mainController.getAvailableWizards())));
                    threadSem.acquire();
                }
                else if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.GAMEREADY))
                {
                    socket.sendAnswer(new SerializedAnswer(new GameStarting()));
                    mainController.readyPlayer();
                    if(mainController.getReadyPlayers() >= mainController.getPlayers())
                    {
                        mainController.updateGamePhase(GamePhase.PLANNING);
                        globalSem.release(mainController.getPlayers() - 1);
                    }
                    else
                    {
                        globalSem.acquire();
                    }
                }
                else if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.PLANNING) && mainController.getCurrentPlayer().equals(socket.getNickname()))
                {
                    socket.sendAnswer(new SerializedAnswer(new StartTurn()));
                    if(planning == 0)
                    {
                        socket.sendAnswer(new SerializedAnswer(new RequestCloud("Seleziona nuvola da riempire")));
                    }
                    if(planning == 1)
                    {
                        socket.sendAnswer(new SerializedAnswer(new RequestCard()));
                    }
                    threadSem.acquire();
                }
                else if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.ACTION) && mainController.getCurrentPlayer().equals(socket.getNickname()))
                {
                    socket.sendAnswer(new SerializedAnswer(new StartTurn()));
                    if(action >= 0 && action <= 2)
                    {
                        socket.sendAnswer(new SerializedAnswer(new RequestMoveStudent(String.valueOf(mainController.getActionController().getMovableStudents()))));
                    }
                    if(action == 3)
                    {
                        socket.sendAnswer(new SerializedAnswer(new RequestMotherNatureMove(String.valueOf(MainController.findPlayerByName(mainController.getGame(), socket.getNickname()).getMaxMotherMovement()))));
                    }
                    if(action == 4)
                    {
                        socket.sendAnswer(new SerializedAnswer(new RequestCloud("Seleziona nuvola da svuotare")));
                    }
                    threadSem.acquire();
                }

            }
        }
        catch (InterruptedException e)
        {

        }
    }


    @Override
    public void update(String message)
    {
        System.out.println(message);
        socket.sendAnswer(new SerializedAnswer(new ViewMessage(message, mainController.getGame().getCurrentCharacterDeck(), mainController.getGame().getCurrentActiveCharacterCard())));
    }
}
