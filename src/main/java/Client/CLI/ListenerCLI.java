package Client.CLI;

import java.io.IOException;

public class ListenerCLI implements Runnable
{
    private ClientCLI client;

    public ListenerCLI(ClientCLI client)
    {
        this.client = client;
    }
    @Override
    public void run()
    {
        while(true)
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
