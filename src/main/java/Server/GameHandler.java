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
    private Semaphore sem;

    public GameHandler(MainController m, ClientConnection s, int team, Semaphore sem) throws IOException {

        this.socket = s;
        this.mainController = m;
        this.ready = false;
        this.choseWizard = false;
        this.planning = 0;
        this.action = 0;
        this.team = team;
        this.sem = sem;
    }

    public void setupHandler(StandardSetupMessage message) throws IOException, InterruptedException {
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
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Sorry, Wizard Already Taken")));
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
                    mainController.getGame().getCurrentTurnState().updateGamePhase(GamePhase.GAMEREADY);
                    sem.release(mainController.getPlayers()-1);
                    System.out.println(sem.availablePermits());
                }
                else
                {
                    System.out.println("Waiting for all players to be ready");
                    sem.acquire();
                    System.out.println(sem.availablePermits());
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
                mainController.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);
            }
            else
            {
                mainController.determineNextPlayer();
            }
            planning = 0;
        }
    }

    public void actionHandler(StandardActionMessage message) throws IOException
    {

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
                socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Too much movement")));
            }
        }

        if(message instanceof DrawFromPouch)
        {
            if(mainController.getActionController().isCloudEmpty(((DrawFromPouch) message).getCloudIndex(), mainController.getGame()))
            {
                socket.sendAnswer(new SerializedAnswer(new ErrorMessage("You selected an empty Cloud")));
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
                mainController.getGame().getCurrentTurnState().updateGamePhase(GamePhase.PLANNING);
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
                mainController.getCharacterController().playEffect(((PlayCharacterEffect) message).getCharacterId(), mainController.getGame(), ((PlayCharacterEffect) message).getFirst(), ((PlayCharacterEffect) message).getSecond(), ((PlayCharacterEffect) message).getThird(), ((PlayCharacterEffect) message).getStudentColor());
            }
        }
    }


    public void messageHandler(StandardActionMessage message) throws IOException {
        if(socket.getNickname().equals(mainController.getCurrentPlayer()))
        {
            if(mainController.isGamePhase(GamePhase.PLANNING))
            {
                planningHandler(message);
            }
            if(mainController.isGamePhase(GamePhase.ACTION))
            {
                actionHandler(message);
            }
        }
        else
        {
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Not your turn!")));
        }

    }

    public void readMessage() throws IOException, ClassNotFoundException, InterruptedException {
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
        boolean flag = false;
        try
        {
            while(isAlive())
            {
                if(mainController.isGamePhase(GamePhase.SETUP) && !choseWizard)
                {
                    socket.sendAnswer(new SerializedAnswer(new AvailableWizards(mainController.getAvailableWizards())));
                    flag = true;
                }
                else if(mainController.isGamePhase(GamePhase.GAMEREADY))
                {
                    socket.sendAnswer(new SerializedAnswer(new GameStarting()));
                    //mainController.getGame().getCurrentTurnState().updateGamePhase(GamePhase.PLANNING);
                }
                else if(mainController.isGamePhase(GamePhase.PLANNING) && mainController.getCurrentPlayer().equals(socket.getNickname()))
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
                    flag = true;
                }
                else if(mainController.isGamePhase(GamePhase.ACTION) && mainController.getCurrentPlayer().equals(socket.getNickname()))
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
                    flag = true;

                }
                if(flag)
                {
                    readMessage();
                }

            }
        }
        catch(IOException e)
        {

        }
        catch(ClassNotFoundException e)
        {

        } catch (InterruptedException e)
        {

        }


    }


    @Override
    public void update(String message)
    {
        socket.sendAnswer(new SerializedAnswer(new ViewMessage(message)));
    }
}
