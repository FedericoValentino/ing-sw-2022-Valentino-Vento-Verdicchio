package Client;

import java.io.IOException;

public class ClientListener extends Thread
{
    private ClientCLI client;

    public ClientListener(ClientCLI client)
    {
        this.client = client;
    }
    @Override
    public void run()
    {
        while(isAlive())
        {
            //System.out.println("Waiting for Server...");
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
