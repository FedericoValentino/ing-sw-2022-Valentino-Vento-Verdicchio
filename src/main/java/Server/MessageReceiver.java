package Server;

import java.io.IOException;

public class MessageReceiver extends Thread
{
    private GameHandler game;

    public MessageReceiver(GameHandler game) {
        this.game = game;
    }

    @Override
    public void run()
    {
        while(game.getConnected())
        {
            game.readMessage();
        }
    }
}
