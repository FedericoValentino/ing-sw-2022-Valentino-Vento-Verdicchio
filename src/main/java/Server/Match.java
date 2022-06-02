package Server;
//ciao
import Server.Answers.ActionAnswers.WinMessage;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.GameStarting;
import Server.Answers.SetupAnswers.InfoMessage;
import controller.Checks;
import controller.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Match
{
    private int matchID;
    private MainController mainController;
    private ArrayList<GameHandler> clients = new ArrayList<>();
    private HashMap<String, String> moves = new HashMap<>();
    private boolean running = false;

    public Match(int playerNumber, boolean expert, int matchID)
    {
        this.matchID = matchID;
        this.mainController = new MainController(playerNumber, expert);
    }

    public void addClient(ClientConnection client) throws IOException {
        GameHandler gameHandler = new GameHandler(mainController, client, client.getTeam(),this);
        clients.add(gameHandler);
    }

    public void startGame()
    {
        running = true;
        for(GameHandler client : clients)
        {
            mainController.getGame().addObserver(client);
            client.start();
        }
    }

    public void announceGameReady()
    {
        for(GameHandler GH : clients)
        {
            GH.getSocket().sendAnswer(new SerializedAnswer(new GameStarting()));
        }
    }

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
