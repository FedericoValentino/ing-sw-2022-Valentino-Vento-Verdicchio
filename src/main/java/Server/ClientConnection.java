package Server;

import Server.Answers.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection
{
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String nickname;
    private int team;

    public ClientConnection(Socket client) throws IOException {
        this.client = client;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.inputStream = new ObjectInputStream(client.getInputStream());
    }

    public void sendAnswer(SerializedAnswer answer)
    {
        try {
            outputStream.writeObject(answer);
            outputStream.flush();
            outputStream.reset();
        }
        catch (IOException e)
        {
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public Socket getClient()
    {
        return client;
    }

    public String getNickname()
    {
        return nickname;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public int getTeam() {
        return team;
    }
}
