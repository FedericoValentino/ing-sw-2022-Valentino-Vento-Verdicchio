package it.polimi.ingsw.Server;
//


import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ERRORTYPES;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.AvailableTeams;
import it.polimi.ingsw.Server.Answers.SetupAnswers.AvailableWizards;
import it.polimi.ingsw.Server.Answers.SetupAnswers.InfoMessage;
import it.polimi.ingsw.Client.Messages.ActionMessages.*;
import it.polimi.ingsw.Client.Messages.SetupMessages.StandardSetupMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.TeamChoice;
import it.polimi.ingsw.Client.Messages.SetupMessages.WizardChoice;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ErrorMessage;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ViewMessage;
import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.controller.checksandbalances.MovesChecks;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.io.IOException;


/**
 * Class gameHandler contains all methods to interpret a client message and ask the controller to modify the game accordingly.
 * GameHandler also alerts the client about errors in the moves.
 */
public class ClientHandler extends Thread implements Observer
{
    private ClientConnection socket;
    private MainController mainController;
    private boolean ready;
    private boolean choseWizard;
    private boolean choseTeam;
    private int team;
    private boolean connected = true;
    private Match currentMatch;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public ClientHandler(ClientConnection s, Match match)
    {
        this.socket = s;
        this.ready = false;
        this.choseWizard = false;
        this.choseTeam = false;
        this.currentMatch = match;
    }


    /**
     * Method updateLastPlayer analyzes the current state of the game and decides whether to end it if it's the last turn or to just compute a new turn order
     * @param phase the current gamePhase
     */
    public void updateLastPlayer(GamePhase phase)
    {
        if(Checks.isLastTurn(mainController.getGame()) && Checks.isGamePhase(mainController.getGame(), GamePhase.ACTION))
        {
            mainController.selectWinner();
            if(mainController.getGame().getCurrentTurnState().getWinningTeam() != null)
            {
                currentMatch.announceWinner(mainController.getGame().getCurrentTurnState().getWinningTeam() + "is the Winner!");
            }
            else
            {
                currentMatch.announceWinner("Game ended in a tie");
            }
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

    /**
     * method setupHandler handles the messages that could arrive during the setupPhase, team choice, wizard choice and
     * player readiness
     * @param message the message that needs to be handled
     * @throws IOException if closing the connection results in an error
     */
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
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_INPUT)));
                        }
                    }
                    break;
                case WIZARD_CHOICE:
                    synchronized (mainController.getAvailableWizards()) {
                        Wizard wizard = ((WizardChoice) message).getWizard();
                        if (mainController.getAvailableWizards().contains(wizard) && MainController.findPlayerByName(mainController.getGame(), socket.getNickname()) == null) {
                            mainController.addPlayer(team, socket.getNickname(), 8, wizard);
                            mainController.getAvailableWizards().remove(wizard);
                            choseWizard = true;
                        } else {
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_INPUT)));
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
                            mainController.setup();
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
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_PHASE)));
        }

    }


    /**
     * method planningHandler handles the messages that could arrive during the planningPhase, the cloud choice and the
     * assistant card choice
     * @param message the message that needs to be handled
     */
    public void planningHandler(StandardActionMessage message)
    {
        switch(message.type)
        {
            case CLOUD_CHOICE:
                if (MovesChecks.isExpectedPlanningMove(mainController.getGame(), message.type) && Checks.canCloudBeFilled(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex()))
                {
                    if(Checks.isPouchAvailable(mainController.getGame()))
                    {
                        mainController.getPlanningController().drawStudentForClouds(mainController.getGame(), ((DrawFromPouch) message).getCloudIndex());
                    }
                    else
                    {
                        mainController.emergencyUpdatePlanning();
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.EMPTY_POUCH)));
                    }
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.CLOUD_ERROR)));
                }
                break;
            case DRAW_CHOICE:
                boolean drawnCard = false;
                if (MovesChecks.isExpectedPlanningMove(mainController.getGame(), message.type))
                {
                    if (Checks.isAssistantValid(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                    {
                        if(!Checks.isAssistantAlreadyPlayed(mainController.getGame(), mainController.getCurrentPlayer(), ((DrawAssistantCard) message).getCardIndex()))
                        {
                            mainController.getPlanningController().drawAssistantCard(mainController.getGame(), socket.getNickname(), ((DrawAssistantCard) message).getCardIndex());
                            drawnCard = true;
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
                                drawnCard = true;
                                if(Checks.checkLastTurnDueToAssistants(mainController.getGame(), mainController.getCurrentPlayer()))
                                {
                                    mainController.lastTurn();
                                }
                            }
                        }
                        if(drawnCard)
                        {
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
                            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.CARD_ERROR)));
                        }
                    }
                    else
                    {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.CARD_ERROR)));
                    }
                }
                break;
        }
    }

    /**
     * Method actionHandler handles the messages that could arrive during the actionPhase, student moves, mother nature
     * moves, cloud choice for the entrance refill and the turn end message
     * @param message the message that needs to be handled
     */
    public void actionHandler(StandardActionMessage message)
    {
        switch(message.type)
        {
            case STUD_MOVE:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type)) {
                    if(Checks.isDestinationAvailable(mainController.getGame(), mainController.getCurrentPlayer(),
                            ((MoveStudent) message).getEntrancePos(),
                            ((MoveStudent) message).isToIsland(),
                            ((MoveStudent) message).getIslandId()))
                    {
                        if (((MoveStudent) message).isToIsland()) {
                            mainController.getActionController()
                                    .placeStudentToIsland(((MoveStudent) message).getEntrancePos(),
                                            ((MoveStudent) message).getIslandId(),
                                            mainController.getGame());
                        } else {
                            mainController.getActionController()
                                    .placeStudentToDiningRoom(((MoveStudent) message).getEntrancePos(),
                                            mainController.getGame());
                        }
                    }
                    else
                    {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.STUD_MOVE_ERROR)));
                    }
                }
                break;


            case MN_MOVE:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type))
                {
                    if (Checks.isAcceptableMovementAmount(mainController.getGame(), mainController.getCurrentPlayer(), ((MoveMN) message).getAmount())) {
                        mainController.getActionController().MoveMN(((MoveMN) message).getAmount(), mainController.getGame());
                    } else {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.MOTHER_ERROR)));
                    }
                }
                break;


            case ENTRANCE_REFILL:
                if (MovesChecks.isExpectedActionMove(mainController.getGame(), mainController.getPlayers(), message.type)) {
                    if (!Checks.isCloudAvailable(mainController.getGame(), ((ChooseCloud) message).getCloudIndex())) {
                        socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.CLOUD_ERROR)));
                    } else {
                        mainController.getActionController().drawFromClouds(((ChooseCloud) message).getCloudIndex(), mainController.getGame());
                    }
                }
                break;

            case CHARACTER_PLAY:
                if (CharacterController.canBePicked(mainController.getGame(),
                        ((PlayCharacter) message).getCharacterName(),
                        MainController.findPlayerByName(mainController.getGame(), socket.getNickname())))
                {
                    mainController.getCharacterController().pickCard(mainController.getGame(), ((PlayCharacter) message).getCharacterName(), MainController.findPlayerByName(mainController.getGame(), socket.getNickname()));
                    if(((PlayCharacter) message).getCharacterName().equals(CharacterName.TRUFFLE_HUNTER))
                    {
                        mainController.getCharacterController().setTruffleHunterColor(mainController.getGame(), ((PlayCharacter) message).getStudentColor());
                    }
                    if(!Checks.checkForInfluenceCharacter(mainController.getGame()))
                    {
                        mainController.getCharacterController().playEffect(
                                ((PlayCharacter) message).getCharacterName(),
                                mainController.getGame(),
                                ((PlayCharacter) message).getFirst(),
                                ((PlayCharacter) message).getSecond(),
                                ((PlayCharacter) message).getThird(),
                                ((PlayCharacter) message).getStudentColor()
                        );
                    }
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.CARD_ERROR)));
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

    /**
     * Method messageHandler decides wheter to redirect the handling of the game messages to the planningHandler method
     * or the actionHandler
     * @param message the message that needs to be handles
     */
    public void messageHandler(StandardActionMessage message)
    {
        if(Checks.isCurrentPlayer(socket.getNickname(), mainController.getCurrentPlayer()))
        {
            if(message.type == ACTIONMESSAGETYPE.CLOUD_CHOICE || message.type == ACTIONMESSAGETYPE.DRAW_CHOICE)
            {
                if(Checks.isGamePhase(mainController.getGame(), GamePhase.PLANNING))
                        planningHandler(message);
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_PHASE)));
                }
            }
            else
            {
                if(Checks.isGamePhase(mainController.getGame(), GamePhase.ACTION))
                {
                    actionHandler(message);
                }
                else
                {
                    socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_PHASE)));
                }
            }
        }
        else
        {
            socket.sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.WRONG_TURN)));
        }
    }

    /**
     * Method readMessage reads messages coming from the client and redirects them to the correct handler
     */
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
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Couldn't understand what " + socket.getNickname() + " was saying...");
        }
    }

    /**
     * Method run is the method that every thread runs. It handles the communication with the client telling it 2 main things:
     * 1) if a winner is detected it will alert the client with a win message
     * 2) if the game is starting it will tell the client which teams are available to join and which wizards are available to choose
     */
    @Override
    public void run()
    {
        while(isAlive())
        {
            if(connected)
            {
                if(Checks.isLastTurn(mainController.getGame()) && !currentMatch.isLastTurnAnnounced())
                {
                    currentMatch.announceLastTurn();
                }
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


    /**
     * The update function is run after every successful client interaction with the model. It sends the client a view message, containing the json serialization of the game model
     * @param message is the serialized model
     */
    @Override
    public void update(String message)
    {
        System.out.println(message);
        socket.sendAnswer(new SerializedAnswer(new ViewMessage(message, mainController.getGame().getCurrentCharacterDeck(), mainController.getGame().getCurrentActiveCharacterCard(), mainController.isExpertGame())));
    }

    public void setMainController(MainController controller)
    {
        mainController = controller;
    }

    public ClientConnection getSocket()
    {
        return socket;
    }
}