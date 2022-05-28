package Server;



import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
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
    private Boolean connected = true;
    private Match currentMatch;
    private Semaphore threadSem;
    private Semaphore globalSem;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public GameHandler(MainController m, ClientConnection s, int team, Semaphore sem, Match match)
    {
        this.socket = s;
        this.mainController = m;
        this.ready = false;
        this.choseWizard = false;
        this.planning = 0;
        this.action = 0;
        this.team = team;
        this.currentMatch = match;
        this.globalSem = sem;
        this.threadSem = new Semaphore(0);
    }

    public void setupHandler(StandardSetupMessage message) throws IOException
    {
        if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.SETUP))
        {
            switch(message.type)
            {
                case WIZARD_CHOICE:
                    synchronized (mainController.getAvailableWizards()) {
                        Wizard wizard = ((WizardChoice) message).getWizard();
                        if (mainController.getAvailableWizards().contains(wizard) && MainController.findPlayerByName(mainController.getGame(), socket.getNickname()) == null) {
                            mainController.AddPlayer(team, socket.getNickname(), 8, wizard);
                            mainController.getAvailableWizards().remove(wizard);
                            socket.sendAnswer(new SerializedAnswer(new InfoMessage("Wizard Selected, type " + ANSI_GREEN + "[Ready] " + ANSI_RESET + "if you're ready to start!")));
                            choseWizard = true;
                            threadSem.release(1);
                        } else {
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, wrong input or Wizard already taken" + ANSI_RESET)));
                            threadSem.release(1);
                        }
                    }
                    break;
                case READINESS:
                    if (!ready) {
                        mainController.readyPlayer();
                        ready = true;
                        if (mainController.getReadyPlayers() == mainController.getPlayers()) {
                            System.out.println("All players ready!");
                            mainController.updateTurnState();
                            mainController.determineNextPlayer();
                            mainController.Setup();
                            mainController.resetReady();
                            mainController.updateGamePhase(GamePhase.GAMEREADY);
                        } else {
                            System.out.println("Waiting for all players to be ready");
                        }
                    }
                    break;
                case DISCONNECTION:
                    socket.getClient().close();
                    break;
            }
        }
        else
        {
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Wrong Phase" + ANSI_RESET)));
        }

    }

    public void planningHandler(StandardActionMessage message)
    {
        switch(message.type)
        {
            case CLOUD_CHOICE:
                if (planning == 0 && mainController.getChecks().isCloudFillable(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex())) {
                    mainController.getPlanningController()
                            .drawStudentForClouds(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex());
                    planning++;
                    threadSem.release(1);
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Cloud is not empty or wrong index")));
                }
                break;
            case DRAW_CHOICE:
                if (planning == 1 && mainController.getChecks().isAssistantValid(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                {
                    if(!mainController.getChecks().isAssistantAlreadyPlayed(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                        mainController.getPlanningController().drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
                    else
                    {
                        if(mainController.getChecks().canCardStillBePlayed(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                        {
                            mainController.getPlanningController().drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
                        }
                    }

                    if (mainController.getChecks().isLastPlayer(mainController.getGame()))
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
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Wrong Card!")));
                }
                break;
        }
    }

    public void actionHandler(StandardActionMessage message)
    {
        switch(message.type)
        {
            case STUD_MOVE:
                if (action >= 0 && action <= 2) {
                    if (((MoveStudent) message).isToIsland()) {
                        mainController.getActionController()
                                .placeStudentToIsland(((MoveStudent) message).getEntrancePos(),
                                        ((MoveStudent) message).getIslandId(),
                                        mainController.getGame(),
                                        socket.getNickname());
                    } else {
                        mainController.getActionController()
                                .placeStudentToDiningRoom(((MoveStudent) message).getEntrancePos(),
                                        mainController.getGame(),
                                        socket.getNickname());
                    }
                    action++;
                    threadSem.release(1);
                }
                break;


            case MN_MOVE:
                if (action == 3)
                {
                    if (mainController.getChecks().isAcceptableMovementAmount(mainController.getGame(), mainController.getCurrentPlayer(), ((MoveMN) message).getAmount())) {
                        mainController.getActionController().MoveMN(((MoveMN) message).getAmount(), mainController.getGame());
                    } else {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Too much movement" + ANSI_RESET)));
                    }
                    action++;
                    threadSem.release(1);
                }
                break;


            case DRAW_POUCH:
                if (action == 4) {
                    if (!mainController.getChecks().isCloudAvailable(mainController.getGame(), ((ChooseCloud) message).getCloudIndex())) {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "You selected an empty Cloud" + ANSI_RESET)));
                    } else {
                        mainController.getActionController().drawFromClouds(((ChooseCloud) message).getCloudIndex(), mainController.getGame(), socket.getNickname());
                    }
                    action++;
                    threadSem.release(1);
                }
                break;


            case TURN_END:
                if (action == 5) {
                    if (mainController.getChecks().isLastPlayer(mainController.getGame())) {
                        mainController.updateGamePhase(GamePhase.PLANNING);
                        mainController.updateTurnState();
                        mainController.determineNextPlayer();
                    } else {
                        mainController.determineNextPlayer();
                    }
                    action = 0;
                    threadSem.release(1);
                }
                break;

        }
    }

    public void characterHandler(StandardActionMessage message)
    {
        switch(message.type)
        {
            case CHARACTER_PLAY:
                if (CharacterController.isPickable(mainController.getGame(),
                        ((PlayCharacter) message).getCharacterName(),
                        MainController.findPlayerByName(mainController.getGame(), socket.getNickname()))) {
                    mainController.getCharacterController().pickCard(mainController.getGame(), ((PlayCharacter) message).getCharacterName(), MainController.findPlayerByName(mainController.getGame(), socket.getNickname()));
                }
                break;
            case CHARACTER_ACTIVATE:
                if (mainController.getCharacterController().isEffectPlayable(mainController.getGame(), ((PlayCharacterEffect) message).getCharacterName())) {
                    mainController.getCharacterController().playEffect(((PlayCharacterEffect) message).getCharacterName(), mainController.getGame(), ((PlayCharacterEffect) message).getFirst(), ((PlayCharacterEffect) message).getSecond(), ((PlayCharacterEffect) message).getThird(), ((PlayCharacterEffect) message).getStudentColor());
                }
                break;
        }
        threadSem.release(1);

    }


    public void messageHandler(StandardActionMessage message)
    {
        if(socket.getNickname().equals(mainController.getCurrentPlayer()))
        {
            if(message.type == ACTIONMESSAGETYPE.CLOUD_CHOICE || message.type == ACTIONMESSAGETYPE.DRAW_CHOICE)
            {
                if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.PLANNING))
                        planningHandler(message);
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Wrong Phase, you are in Action Phase!" + ANSI_RESET)));
                }
            }
            else
            {
                if(mainController.getChecks().isGamePhase(mainController.getGame(), GamePhase.ACTION))
                {
                    if(message.type == ACTIONMESSAGETYPE.CHARACTER_PLAY || message.type == ACTIONMESSAGETYPE.CHARACTER_ACTIVATE)
                        characterHandler(message);
                    else
                        actionHandler(message);
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Wrong Phase, you are in Planning Phase!" + ANSI_RESET)));
                }
            }


        }
        else
        {
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Wait for your turn!" + ANSI_RESET)));
        }

    }

    public void readMessage()
    {
        try
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
        catch (IOException e)
        {
            System.out.println(socket.getNickname() + "disconnected");
            connected = false;
            threadSem.release();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Couldn't understand what " + socket.getNickname() + " was saying...");
            threadSem.release(1);
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
                if(connected)
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
                            socket.sendAnswer(new SerializedAnswer(new RequestCloud("Choose " + ANSI_CYAN + "cloud " + ANSI_RESET + "to fill")));
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
                            socket.sendAnswer(new SerializedAnswer(new RequestCloud("Choose " + ANSI_CYAN + "cloud " + ANSI_RESET + "to empty")));
                        }
                        if(action == 5)
                        {
                            socket.sendAnswer(new SerializedAnswer(new InfoMessage("End your turn " + ANSI_GREEN + "[EndTurn]" + ANSI_RESET)));
                        }
                        threadSem.acquire();
                    }
                }
                else
                {
                    currentMatch.end();
                }
                if(!currentMatch.getRunning())
                {
                    socket.sendAnswer(new SerializedAnswer(new InfoMessage("Client Disconnected, game is Ending")));
                    socket.sendAnswer(new SerializedAnswer(new WinMessage("Game ended with no winner due to disconnection")));
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

    public Boolean getConnected() {
        return connected;
    }
}
