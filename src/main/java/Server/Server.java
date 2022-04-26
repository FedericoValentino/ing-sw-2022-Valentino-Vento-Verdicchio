package Server;

import Client.SetupMessages.GameMode;
import Client.SetupMessages.SetupConnection;
import Server.SetupAnswers.RequestGameInfo;
import controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
   public static void main(String[] args) throws IOException, ClassNotFoundException {
       ServerSocket serverSocket = new ServerSocket(1234);
       ArrayList<Socket> waitList = new ArrayList<>();
       while(true)
       {
           GameMode info = new GameMode();
           Socket socket = serverSocket.accept();
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
           if(waitList.size() == 0)
           {
               SetupConnection message = (SetupConnection) in.readObject();
               waitList.add(socket);
               out.writeObject(new RequestGameInfo());
               info = (GameMode) in.readObject();
           }
           else
           {
               SetupConnection message = (SetupConnection) in.readObject();
               waitList.add(socket);
           }
           if(waitList.size() == info.getMaxPlayers())
           {
               MainController mc = new MainController(info.getMaxPlayers(), info.isExpertGame());
               for(Socket c: waitList)
               {
                   GameHandler GH = new GameHandler(mc, c);
                   GH.start();
               }
           }
       }
   }
}
