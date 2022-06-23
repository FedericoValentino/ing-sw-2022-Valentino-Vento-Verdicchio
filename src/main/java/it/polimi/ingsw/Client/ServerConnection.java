package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.SetupConnection;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection
{
    private Socket server;
    private int PORT = 1234;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nickname;
    private String ServerIP;
    private boolean connected = false;


    /**
     * Class Constructor saves the parameters to send to the server after connecting
     * @param nickname
     * @param serverIP
     * @throws IOException
     */
    public ServerConnection(String nickname, String serverIP) throws IOException
    {
        this.nickname = nickname;
        this.ServerIP = serverIP;
    }

    /**
     * Method establishConnection tries to connect to the server
     * @throws IOException
     */
    public void establishConnection() throws IOException {
        server = new Socket(ServerIP, PORT);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());

        SetupConnection setup = new SetupConnection(nickname);
        out.writeObject(setup);
        out.flush();
        out.reset();
        connected = true;
    }


    /**
     * Sends a SerializedMessage to the server
     * @param answer
     */
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
