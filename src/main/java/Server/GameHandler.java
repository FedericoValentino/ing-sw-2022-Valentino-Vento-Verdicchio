//TODO mai dire "Il Server dice al client"
//TODO spostare logica nel controller

//ciao
package Server;



import Client.Messages.ActionMessages.*;
import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.*;
import Server.Answers.ActionAnswers.*;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.AvailableTeams;
import Server.Answers.SetupAnswers.AvailableWizards;
import Server.Answers.SetupAnswers.GameStarting;
import Server.Answers.SetupAnswers.InfoMessage;
import controller.CharacterController;
import controller.Checks;
import controller.MainController;
import controller.MovesChecks;
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
    private boolean choseTeam;
    private int team;
    private Boolean connected = true;
    private Match currentMatch;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public GameHandler(MainController m, ClientConnection s, Match match)
    {
        this.socket = s;
        this.mainController = m;
        this.ready = false;
        this.choseWizard = false;
        this.choseTeam = false;
        this.currentMatch = match;
    }


    public void updateLastPlayer(GamePhase phase)
    {
        if(Checks.isLastTurn(mainController.getGame()) && Checks.isGamePhase(mainController.getGame(), GamePhase.ACTION))
        {
            mainController.selectWinner();
            socket.sendAnswer(new SerializedAnswer(new WinMessage(String.valueOf(mainController.getGame().getCurrentTurnState().getWinningTeam()) + "is the Winner!")));
            try
            {
                socket.getClient().close();
            }
            catch(IOException e)
            {
                System.out.println("Couldn't close connection");
            }
            currentMatch.end();
        }
        else
        {
            mainController.updateGamePhase(phase);
            mainController.updateTurnState();
            mainController.determineNextPlayer();
        }

    }

    public void setupHandler(StandardSetupMessage message) throws IOException
    {
        if(Checks.isGamePhase(mainController.getGame(), GamePhase.SETUP))
        {
            switch(message.type)
            {
                case TEAM_CHOICE:
                    synchronized (mainController.getAvailableTeams())
                    {
                        int teamChoice = ((TeamChoice) message).getTeam();
                        if(mainController.getAvailableTeams()[teamChoice] > 0)
                        {
                            this.team = teamChoice;
                            mainController.removeSlotFromTeam(teamChoice);
                            choseTeam = true;
                        }
                        else
                        {
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, wrong input or Team already full" + ANSI_RESET)));
                        }
                    }
                    break;
                case WIZARD_CHOICE:
                    synchronized (mainController.getAvailableWizards()) {
                        Wizard wizard = ((WizardChoice) message).getWizard();
                        if (mainController.getAvailableWizards().contains(wizard) && MainController.findPlayerByName(mainController.getGame(), socket.getNickname()) == null) {
                            mainController.AddPlayer(team, socket.getNickname(), 8, wizard);
                            mainController.getAvailableWizards().remove(wizard);
                            choseWizard = true;
                        } else {
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, wrong input or Wizard already taken" + ANSI_RESET)));
                        }
                    }
                    break;
                case READINESS:
                    if (!ready) {
                        mainController.readyPlayer();
                        ready = true;
                        if (mainController.getReadyPlayers() == mainController.getPlayers()) {
                            mainController.updateGamePhase(GamePhase.GAMEREADY);
                            System.out.println("All players ready!");
                            currentMatch.announceGameReady();
                            mainController.updateTurnState();
                            mainController.determineNextPlayer();
                            mainController.updateGamePhase(GamePhase.PLANNING);
                            mainController.Setup();
                        }
                        else
                        {
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
                if (MovesChecks.isExpectedPlanningMove(mainController.getGame(), message.type) && Checks.isCloudFillable(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex())) {
                    mainController.getPlanningController().drawStudentForClouds(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex());
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage("Cloud is not empty or wrong index")));
                }
                break;
            case DRAW_CHOICE:
                if (MovesChecks.isExpectedPlanningMove(mainController.getGame(), message.type) && Checks.isAssistantValid(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                {
                    if(!Checks.isAssistantAlreadyPlayed(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                    {
                        mainController.getPlanningController().drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
                        if(Checks.checkLastTurnDueToAssistants(mainController.getGame(), mainController.getCurrentPlayer()))
                        {
                            mainController.lastTurn();
                        }
                    }
                    else
                    {
                        if(Checks.canCardStillBePlayed(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                        {
                            mainController.getPlanningController().drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
                            if(Checks.checkLastTurnDueToAssistants(mainController.getGame(), mainController.getCurrentPlayer()))
                            {
                                mainController.lastTurn();
                            }
                        }
                    }

                    if (Checks.isLastPlayer(mainController.getGame()))
                    {
                        updateLastPlayer(GamePhase.ACTION);
                    }
                    else
                    {
                        mainController.determineNextPlayer();
                    }
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
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type)) {
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
                }
                break;


            case MN_MOVE:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type))
                {
                    if (Checks.isAcceptableMovementAmount(mainController.getGame(), mainController.getCurrentPlayer(), ((MoveMN) message).getAmount())) {
                        mainController.getActionController().MoveMN(((MoveMN) message).getAmount(), mainController.getGame());
                    } else {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Too much movement" + ANSI_RESET)));
                    }
                }
                break;


            case DRAW_POUCH:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type)) {
                    if (!Checks.isCloudAvailable(mainController.getGame(), ((ChooseCloud) message).getCloudIndex())) {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "You selected an empty Cloud" + ANSI_RESET)));
                    } else {
                        mainController.getActionController().drawFromClouds(((ChooseCloud) message).getCloudIndex(), mainController.getGame(), socket.getNickname());
                    }
                }
                break;


            case TURN_END:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type))
                {
                    if (Checks.isLastPlayer(mainController.getGame())) {
                        updateLastPlayer(GamePhase.PLANNING);
                    } else {
                        mainController.determineNextPlayer();
                    }
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
    }

    public void messageHandler(StandardActionMessage message)
    {
        if(socket.getNickname().equals(mainController.getCurrentPlayer()))
        {
            if(message.type == ACTIONMESSAGETYPE.CLOUD_CHOICE || message.type == ACTIONMESSAGETYPE.DRAW_CHOICE)
            {
                if(Checks.isGamePhase(mainController.getGame(), GamePhase.PLANNING))
                        planningHandler(message);
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ANSI_RED_BACKGROUND + ANSI_BLACK + "Wrong Phase, you are in Action Phase!" + ANSI_RESET)));
                }
            }
            else
            {
                if(Checks.isGamePhase(mainController.getGame(), GamePhase.ACTION))
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
            return;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Couldn't understand what " + socket.getNickname() + " was saying...");
        }
    }

    @Override
    public void run()
    {
        while(isAlive())
        {
            if(connected)
            {
                if(Checks.isThereAWinner(mainController.getGame()))
                {
                    currentMatch.end();
                }
                else if (Checks.isGamePhase(mainController.getGame(), GamePhase.SETUP) && !choseTeam)
                {
                    socket.sendAnswer(new SerializedAnswer(new AvailableTeams(mainController.getAvailableTeams())));
                }
                else if(Checks.isGamePhase(mainController.getGame(), GamePhase.SETUP) && !choseWizard)
                {
                    socket.sendAnswer(new SerializedAnswer(new AvailableWizards(mainController.getAvailableWizards())));
                }
                else if(Checks.isGamePhase(mainController.getGame(), GamePhase.SETUP) && choseWizard && !ready)
                {
                    socket.sendAnswer(new SerializedAnswer(new InfoMessage("Wizard Selected, type " + ANSI_GREEN + "[Ready] " + ANSI_RESET + "if you're ready to start!")));
                }
                readMessage();
            }
            else
            {
                currentMatch.end();
            }

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

    public ClientConnection getSocket()
    {
        return socket;
    }
}