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
            client.readMessage();
        }
    }
}
