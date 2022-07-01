package it.polimi.ingsw.Server;

import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.Ping;

/**
 * Class ClientPinger handles the pinging of the various clients in the game
 */
public class ClientPinger implements /*always*/ Runnable
{
    private GameHandler client;

    public ClientPinger(GameHandler client)
    {
        this.client = client;
    }

    @Override
    public void run()
    {
       while(true)
       {
           client.getSocket().sendAnswer(new SerializedAnswer(new Ping()));
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
