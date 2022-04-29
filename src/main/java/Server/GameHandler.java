package Server;


import Server.Answers.SetupAnswers.GameStarting;
import controller.MainController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class GameHandler extends Thread
{
    private ClientConnection socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MainController mainController;

    public GameHandler(MainController m, ClientConnection s) throws IOException {
        this.socket = s;
        this.in = new ObjectInputStream(s.getClient().getInputStream());
        this.out = new ObjectOutputStream(s.getClient().getOutputStream());
        this.mainController = m;
    }

    @Override
    public void run()
    {
        try {
            out.writeObject(new GameStarting());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
