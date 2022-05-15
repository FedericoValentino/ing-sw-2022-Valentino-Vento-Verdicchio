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
        while(isAlive())
        {
            try {
                game.readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
