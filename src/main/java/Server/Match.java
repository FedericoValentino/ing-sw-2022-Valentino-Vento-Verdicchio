package Server;

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
    private Semaphore gameSemaphore = new Semaphore(0);
    private boolean running = false;

    public Match(int playerNumber, boolean expert, int matchID)
    {
        this.matchID = matchID;
        this.mainController = new MainController(playerNumber, expert);
    }

    public void addClient(ClientConnection client) throws IOException {
        GameHandler gameHandler = new GameHandler(mainController, client, client.getTeam(), gameSemaphore,this);
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

    public void end()
    {
        running = false;
    }

    public boolean getRunning(){return running;}

}
