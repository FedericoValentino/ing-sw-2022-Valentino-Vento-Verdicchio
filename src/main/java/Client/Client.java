package Client;

import Client.SetupMessages.GameMode;
import Client.SetupMessages.SetupConnection;
import Server.SetupAnswers.RequestGameInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        SetupConnection setup = new SetupConnection();
        System.out.println("Required Nickname: ");
        setup.setNickname(stdin.next());
        out.writeObject(setup);
        while(true)
        {
            if(in.readObject() instanceof RequestGameInfo)
            {
                System.out.println("Required Game info: ");
                GameMode game = new GameMode();
                game.setMaxPlayers(stdin.nextInt());
                game.setExpertGame(stdin.nextBoolean());
                out.writeObject(game);
            }
        }

    }
}
