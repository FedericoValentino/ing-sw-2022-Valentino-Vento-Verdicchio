package it.polimi.ingsw.Server;
//ciao
import it.polimi.ingsw.Server.Answers.ActionAnswers.WinMessage;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.GameStarting;
import it.polimi.ingsw.Server.Answers.SetupAnswers.InfoMessage;
import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.controller.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Match
{
    private int matchID;
    private MainController mainController;
    private int players;
    private boolean expertMode;
    private ArrayList<GameHandler> clients = new ArrayList<>();
    private boolean running = false;
    private boolean isGameSet = false;
    private boolean hasEnded = false;
    private Object winnerLock = new Object();
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
        GameHandler gameHandler = new GameHandler(client, this);
        clients.add(gameHandler);
        clientPinger.execute(new ClientPinger(gameHandler));
    }

    /**
     * Method startGame starts every client GameHandler thread
     */
    public void startGame()
    {
        for(GameHandler GH: getClients())
        {
            GH.setMainController(mainController);
        }
        running = true;
        for(GameHandler client : clients)
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
        for(GameHandler GH : clients)
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
            for(GameHandler GameHandler: clients)
            {
                GameHandler.getSocket().sendAnswer(new SerializedAnswer(new InfoMessage("Client Disconnected, game is Ending")));
                GameHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage("Game ended with no winner due to disconnection")));
                GameHandler.interrupt();
            }
        }
        else
        {
            for(GameHandler GameHandler: clients)
            {
                GameHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage(mainController.getGame().getCurrentTurnState().getWinningTeam() + "is the Winner!")));
                try
                {
                    GameHandler.getSocket().getClient().close();
                    GameHandler.interrupt();
                }
                catch(IOException e)
                {
                    System.out.println("Couldn't close connection");
                }
            }
        }
        hasEnded = true;

    }


    public void announceWinner(String announcement)
    {
        synchronized (winnerLock)
        {
            if(!hasEnded)
            {
                hasEnded = true;
                for(GameHandler GameHandler: clients)
                    GameHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage(announcement)));
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

    public ArrayList<GameHandler> getClients(){return clients;}

    public boolean isGameSet(){ return isGameSet; }

    public boolean hasEnded()
    {
        return hasEnded;
    }
}
