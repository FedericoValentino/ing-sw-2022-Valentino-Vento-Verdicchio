package Server;

import Client.SetupMessages.Disconnect;
import controller.MainController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void run()
    {
        Disconnect dc = null;
        while(!dc.isDisconnecting());
        {

        }
    }
}
