package Client.GUI;

import java.io.IOException;

public class ListenerGui implements Runnable{

    private ClientGUI client;

    public ListenerGui(ClientGUI client)
    {
        this.client = client;
    }
    @Override
    public void run()
    {
        while(client.getServerConnection().getConnected())
        {
            try {
                client.readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}