package it.polimi.ingsw.Server;
//
import it.polimi.ingsw.Server.Answers.ActionAnswers.ERRORTYPES;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ErrorMessage;
import it.polimi.ingsw.Server.Answers.ActionAnswers.WinMessage;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.GameStarting;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.controller.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Class Match contains all the information about a match and handles the various game announcements broadcasting to all clients
 */
public class Match
{
    private int matchID;
    private MainController mainController;
    private int players;
    private boolean expertMode;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private boolean running = false;
    private boolean isGameSet = false;
    private boolean hasEnded = false;
    private boolean lastTurnAnnounced = false;
    private Object announceLock = new Object();
    private ExecutorService clientPinger = Executors.newFixedThreadPool(128);


    /**
     * Class Constructor, creates a new match with rules determined by the first client that connects to the waitList in the Server class
     * @param matchID is the unique gameID
     */
    public Match(int matchID)
    {
        this.matchID = matchID;

    }

    /**
     * Method addClient adds a client to the clients ArrayList
     */
    public void addClient(ClientConnection client) {
        ClientHandler clientHandler = new ClientHandler(client, this);
        clients.add(clientHandler);
        clientPinger.execute(new ClientPinger(clientHandler));
    }

    /**
     * Method startGame starts every client GameHandler thread
     */
    public void startGame()
    {
        for(ClientHandler GH: getClients())
        {
            GH.setMainController(mainController);
        }
        running = true;
        for(ClientHandler client : clients)
        {
            mainController.getGame().addObserver(client);
            client.start();
        }
    }

    /**
     * Method announceGameReady tells every client that the game is starting once everyone has sent a READINESS message
     */
    public void announceGameReady()
    {
        for(ClientHandler GH : clients)
        {
            GH.getSocket().sendAnswer(new SerializedAnswer(new GameStarting()));
        }
    }

    /**
     * Ends the current match and terminates every thread sending the reason the game ended
     */
    public void end()
    {
        running = false;
        if(!Checks.isThereAWinner(mainController.getGame()))
        {
            for(ClientHandler ClientHandler : clients)
            {
                ClientHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage("Game ended with no winner due to disconnection")));
                ClientHandler.interrupt();
            }
        }
        else
        {
            for(ClientHandler ClientHandler : clients)
            {
                ClientHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage(mainController.getGame().getCurrentTurnState().getWinningTeam() + "is the Winner!")));
                try
                {
                    ClientHandler.getSocket().getClient().close();
                    ClientHandler.interrupt();
                }
                catch(IOException e)
                {
                    System.out.println("Couldn't close connection");
                }
            }
        }
        hasEnded = true;

    }

    public void announceLastTurn()
    {
        synchronized (announceLock)
        {
            if(!lastTurnAnnounced)
            {
                lastTurnAnnounced = true;
                for(ClientHandler ClientHandler : clients)
                    ClientHandler.getSocket().sendAnswer(new SerializedAnswer(new ErrorMessage(ERRORTYPES.LAST_TURN)));
            }
        }

    }


    public void announceWinner(String announcement)
    {
        synchronized (announceLock)
        {
            if(!hasEnded)
            {
                hasEnded = true;
                for(ClientHandler ClientHandler : clients)
                    ClientHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage(announcement)));
            }
        }

    }

    public void setMode(boolean expert, int playerNumber)
    {
        mainController = new MainController(playerNumber, expert);
        expertMode = expert;
        players = playerNumber;
        isGameSet = true;
    }

    public boolean getRunning(){return running;}

    public ArrayList<ClientHandler> getClients(){return clients;}

    public boolean isGameSet(){ return isGameSet; }

    public boolean hasEnded()
    {
        return hasEnded;
    }

    public int getMatchID() {
        return matchID;
    }

    public int getPlayers() {
        return players;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public boolean isLastTurnAnnounced() {
        return lastTurnAnnounced;
    }
}
