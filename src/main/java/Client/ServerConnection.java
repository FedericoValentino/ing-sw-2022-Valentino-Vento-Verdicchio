package Client;

import Client.Messages.SerializedMessage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection
{
    private Socket server;
    private int PORT = 1234;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nickname;
    private int Team;
    private String ServerIP;


    public ServerConnection() throws IOException
    {
        Scanner stdin = new Scanner(System.in);
        System.out.println("NickName?");
        nickname = stdin.next();
        System.out.println("Team?");
        Team = stdin.nextInt();
        System.out.println("Server IP?");
        ServerIP = stdin.next();

        server = new Socket(ServerIP, 1234);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());
    }


    public void sendMessage(SerializedMessage answer)
    {
        try {
            out.reset();
            out.writeObject(answer);
            out.flush();
        }
        catch (IOException e)
        {
        }
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTeam() {
        return Team;
    }

    public String getServerIP() {
        return ServerIP;
    }

    public ObjectOutputStream getOut() {
        return out;
    }
}
