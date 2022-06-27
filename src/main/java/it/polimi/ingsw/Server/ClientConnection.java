package it.polimi.ingsw.Server;

import it.polimi.ingsw.Server.Answers.SerializedAnswer;

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

    /**
     * Class Constructor, instantiates a new instance of ClientConnection taking as a parameter the newly bound client socket
     * @param client the Client socket
     */
    public ClientConnection(Socket client) throws IOException {
        this.client = client;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.inputStream = new ObjectInputStream(client.getInputStream());
    }

    /**
     * Method sendAnswer sends a message to the client
     * @param answer the message to be sent
     */
    public synchronized void sendAnswer(SerializedAnswer answer)
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

}
