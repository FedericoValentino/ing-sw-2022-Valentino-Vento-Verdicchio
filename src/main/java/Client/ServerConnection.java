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
    private String ServerIP;
    private boolean connected = false;


    public ServerConnection(String nickname, String serverIP) throws IOException
    {
        this.nickname = nickname;
        this.ServerIP = serverIP;
    }

    public void establishConnection() throws IOException {
        server = new Socket(ServerIP, 1234);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());

        SetupConnection setup = new SetupConnection(nickname);
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

    public String getServerIP() {
        return ServerIP;
    }

    public boolean getConnected(){return connected;}

}
