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
        while(true)
        {
            System.out.println("Waiting for Server...");
            try {
                System.out.println("Try ");
                client.readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("cazzz");
            }
        }
    }
}