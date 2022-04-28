package Server;

import Client.Messages.SetupMessages.GameMode;
import Client.Messages.SetupMessages.SetupConnection;
import Server.Answers.SetupAnswers.RequestGameInfo;
import controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
   public static void main(String[] args) throws IOException, ClassNotFoundException {
       ServerSocket serverSocket = new ServerSocket(1234);
       ArrayList<ClientConnection> waitList = new ArrayList<>();
       while(true)
       {
           //Accepting Connections
           GameMode info = new GameMode();
           Socket socket = serverSocket.accept();
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
           if(waitList.size() == 0)
           {
               //Receiving Client Nickname
               SetupConnection message = (SetupConnection) in.readObject();
               ClientConnection connection = new ClientConnection(socket, message.getNickname());
               //adding Client To waiting Lobby
               waitList.add(connection);
               //requesting GameInfo to first client
               out.writeObject(new RequestGameInfo());
               //receiving GameMode info from first client
               info = (GameMode) in.readObject();
               System.out.println("N players: " + info.getMaxPlayers() + " ExpertGame: " + info.isExpertGame());
           }
           else
           {
               SetupConnection message = (SetupConnection) in.readObject();
               ClientConnection connection = new ClientConnection(socket, message.getNickname());
               waitList.add(connection);
           }
           if(waitList.size() == info.getMaxPlayers())
           {
               MainController mc = new MainController(info.getMaxPlayers(), info.isExpertGame());
               for(ClientConnection c: waitList)
               {
                   GameHandler GH = new GameHandler(mc, c);
                   GH.start();
               }
           }
       }
   }
}
