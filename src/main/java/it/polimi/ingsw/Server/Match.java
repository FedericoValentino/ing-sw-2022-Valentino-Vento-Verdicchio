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
import java.util.HashMap;

public class Match
{
    private int matchID;
    private MainController mainController;
    private ArrayList<GameHandler> clients = new ArrayList<>();
    private HashMap<String, String> moves = new HashMap<>();
    private boolean running = false;

    /**
     * Class Constructor, creates a new match with rules determined by the first client that connects to the waitList in the Server class
     * @param playerNumber is the number of players playing the game
     * @param expert is the game mode selector
     * @param matchID is the unique gameID
     */
    public Match(int playerNumber, boolean expert, int matchID)
    {
        this.matchID = matchID;
        this.mainController = new MainController(playerNumber, expert);
    }

    /**
     * Method addClient adds a client to the clients ArrayList
     * @param client
     * @throws IOException
     */
    public void addClient(ClientConnection client) throws IOException {
        GameHandler gameHandler = new GameHandler(mainController, client, this);
        clients.add(gameHandler);
    }

    /**
     * Method startGame starts every client GameHandler thread
     */
    public void startGame()
    {
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
                GameHandler.getSocket().sendAnswer(new SerializedAnswer(new WinMessage(String.valueOf(mainController.getGame().getCurrentTurnState().getWinningTeam()) + "is the Winner!")));
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


    }

    public boolean getRunning(){return running;}

}
