package Server;

import Client.Client;
import Client.Messages.SetupMessages.GameMode;
import Client.Messages.SetupMessages.SetupConnection;
import Server.Answers.SetupAnswers.RequestGameInfo;
import controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
   private static final int PORT = 1234;
   private ServerSocket server;
   private ArrayList<ClientConnection> waitLobby = new ArrayList<>();
   private ExecutorService executor = Executors.newFixedThreadPool(128);

   public Server() throws IOException
   {
       this.server = new ServerSocket(PORT);
   }

   public void registerConnection(Socket s, ObjectInputStream in) throws IOException, ClassNotFoundException
   {
      System.out.println("registering new connection");
      //Receiving Client Nickname
      SetupConnection message = (SetupConnection) in.readObject();
      ClientConnection connection = new ClientConnection(s, message.getNickname());
      //adding Client To waiting Lobby
      waitLobby.add(connection);
   }

   public GameMode requestGameInfo(Socket s, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
      //requesting GameInfo to first client
      out.writeObject(new RequestGameInfo());
      //receiving GameMode info from first client
      return (GameMode) in.readObject();
   }

   public void run()
   {
      GameMode info = new GameMode();
      while(true)
      {
         try
         {
            Socket socket = server.accept();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("New Connection!");
            registerConnection(socket, in);
            System.out.println(waitLobby.size());
            if(waitLobby.size() == 1)
            {
               info = requestGameInfo(socket, in, out);
            }
            if(waitLobby.size() == info.getMaxPlayers())
            {
               MainController mc = new MainController(info.getMaxPlayers(), info.isExpertGame());
               for(ClientConnection c : waitLobby)
               {
                  GameHandler GH = new GameHandler(mc, c, in, out);
                  mc.getGame().addObserver(GH);
                  executor.submit(GH);
               }
               waitLobby.removeAll(waitLobby);
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
