package it.polimi.ingsw.Client.CLI;


public class ListenerCLI implements Runnable
{
    private ClientCLI client;

    /**
     * Class Constructor instatiates a new Listener for the CLI
     * @param client
     */
    public ListenerCLI(ClientCLI client)
    {
        this.client = client;
    }

    /**
     * Method run reads messages incoming from the server while the client is connected
     */
    @Override
    public void run()
    {
        while(client.getMain().getConnected())
        {
            client.readMessage();
        }
    }
}
