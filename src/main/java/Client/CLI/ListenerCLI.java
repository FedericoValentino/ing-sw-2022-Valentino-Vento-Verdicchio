package Client.CLI;


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
        while(client.getMain().getConnected())
        {
            client.readMessage();
        }
    }
}
