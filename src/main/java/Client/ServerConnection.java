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


    public ServerConnection(String nickname, int team, String serverIP) throws IOException
    {
        this.nickname = nickname;
        this.Team = team;
        this.ServerIP = serverIP;

        server = new Socket(ServerIP, 1234);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());
    }


    public void sendMessage(SerializedMessage answer)
    {
        try {
            out.writeObject(answer);
            out.flush();
            out.reset();
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
