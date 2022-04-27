package Server;

import java.net.Socket;

public class ClientConnection
{
    private Socket client;
    private String nickname;

    public ClientConnection(Socket client, String nickname)
    {
        this.client = client;
        this.nickname = nickname;
    }

    public Socket getClient()
    {
        return client;
    }

    public String getNickname()
    {
        return nickname;
    }

}
