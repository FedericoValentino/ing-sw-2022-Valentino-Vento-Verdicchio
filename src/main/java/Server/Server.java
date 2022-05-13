package Server;

import Client.Messages.SetupMessages.GameMode;
import Client.Messages.SetupMessages.SetupConnection;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.RequestGameInfo;
import controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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

   public void registerConnection(Socket s) throws IOException, ClassNotFoundException
   {
      System.out.println("registering new connection");
      //Receiving Client Nickname
      ClientConnection connection = new ClientConnection(s);
      SetupConnection message = (SetupConnection) connection.getInputStream().readObject();
      connection.setNickname(message.getNickname());
      connection.setTeam(message.getTeam());
      //adding Client To waiting Lobby
      waitLobby.add(connection);
   }

   public GameMode requestGameInfo(ClientConnection c) throws IOException, ClassNotFoundException {
      //requesting GameInfo to first client
      c.sendAnswer(new SerializedAnswer(new RequestGameInfo()));
      //receiving GameMode info from first client
      return (GameMode) c.getInputStream().readObject();
   }

   public void run()
   {
      GameMode info = new GameMode();
      while(true)
      {
         try
         {
            Socket socket = server.accept();
            System.out.println("New Connection!");
            registerConnection(socket);
            System.out.println(waitLobby.size());
            if(waitLobby.size() == 1)
            {
               info = requestGameInfo(waitLobby.get(0));
            }
            if(waitLobby.size() == info.getMaxPlayers())
            {
               MainController mc = new MainController(info.getMaxPlayers(), info.isExpertGame());
               Semaphore sem = new Semaphore(0);
               int x = 0;
               for(ClientConnection c : waitLobby)
               {
                  GameHandler GH = new GameHandler(mc, c, c.getTeam(), sem);
                  mc.getGame().addObserver(GH);
                  GH.start();
                  x++;
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
