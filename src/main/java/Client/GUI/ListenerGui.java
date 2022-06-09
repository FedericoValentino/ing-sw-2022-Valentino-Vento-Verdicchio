package Client.GUI;

import java.io.IOException;

public class ListenerGui implements Runnable{

    private ClientGUI client;

    public ListenerGui(ClientGUI client)
    {
        this.client = client;
    }

    /**
     * This method it's called by the setServerConnection method in ClientGUI class and it read the message from the
     * server as long as the connection is available.
     */
    @Override
    public void run()
    {
        while(client.getServerConnection().getConnected())
        {
            client.readMessage();
        }
    }
}