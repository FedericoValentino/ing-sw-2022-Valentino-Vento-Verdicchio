package Server;

import Client.SetupMessages.Disconnect;
import controller.MainController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameHandler extends Thread
{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MainController mainController;

    public GameHandler(MainController m, Socket s) throws IOException {
        this.socket = s;
        this.in = new ObjectInputStream(this.socket.getInputStream());
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
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
