package Client;

import Client.Messages.SerializedMessage;
import Client.Messages.SetupMessages.SetupConnection;


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
    private boolean connected = false;


    public ServerConnection(String nickname, int team, String serverIP) throws IOException
    {
        this.nickname = nickname;
        this.Team = team;
        this.ServerIP = serverIP;
    }

    public void establishConnection() throws IOException {
        server = new Socket(ServerIP, 1234);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());

        SetupConnection setup = new SetupConnection(nickname, Team);
        out.writeObject(setup);
        out.flush();
        out.reset();
        connected = true;
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

    public void disconnect()
    {
        this.connected = false;
    }

    public void nicknameChange(String input)
    {
        this.nickname = input;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
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

    public boolean getConnected(){return connected;}

}
