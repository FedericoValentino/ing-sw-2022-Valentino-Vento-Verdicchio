package Server;

import Client.Messages.SetupMessages.GameMode;
import Client.Messages.SetupMessages.SetupConnection;
import Server.Answers.SerializedAnswer;
import Server.Answers.SetupAnswers.RejectConnection;
import Server.Answers.SetupAnswers.RequestGameInfo;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
   private static final int PORT = 1234;
   private ServerSocket server;
   private ArrayList<ClientConnection> waitLobby = new ArrayList<>();
   private ArrayList<Match> matches = new ArrayList<>();
   private Boolean isGameSet = false;

   public Server() throws IOException
   {
       this.server = new ServerSocket(PORT);
   }

   public void registerConnection(Socket s) throws IOException, ClassNotFoundException
   {

      try
      {
         System.out.println("registering new connection");
         //Receiving Client Nickname
         ClientConnection connection = new ClientConnection(s);
         SetupConnection message = (SetupConnection) connection.getInputStream().readObject();
         connection.setNickname(message.getNickname());
         for(ClientConnection C : waitLobby)
         {
            if(C.getNickname().equals(connection.getNickname()))
            {
               connection.sendAnswer(new SerializedAnswer(new RejectConnection()));
               connection.getClient().close();
               return;
            }
         }
         connection.setTeam(message.getTeam());
         //adding Client To waiting Lobby
         waitLobby.add(connection);
      }
      catch(IOException e)
      {
         System.out.println("Client disconnected during connection registering");
      }
      catch(ClassNotFoundException e)
      {
         System.out.println("Couldn't understand client");
      }
   }

   public GameMode requestGameInfo(ClientConnection client)
   {
      try
      {

         //requesting GameInfo to first client
         client.sendAnswer(new SerializedAnswer(new RequestGameInfo()));
         //receiving GameMode info from first client
         GameMode info =(GameMode) client.getInputStream().readObject();
         isGameSet = true;
         return info;
      }
      catch(IOException e)
      {
         System.out.println("Client disconnected");
         waitLobby.remove(client);
      }
      catch(ClassNotFoundException e)
      {
         System.out.println("Couldn't understand client");
         waitLobby.remove(client);
      }
      return new GameMode();
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
            if(waitLobby.size() == 1 && !isGameSet)
            {
               info = requestGameInfo(waitLobby.get(0));

            }
            if(waitLobby.size() == info.getMaxPlayers())
            {
               Match match = new Match(info.getMaxPlayers(), info.isExpertGame(), matches.size());
               for(ClientConnection client : waitLobby)
               {
                  match.addClient(client);
               }
               match.startGame();
               matches.add(match);
               waitLobby.removeAll(waitLobby);
               isGameSet = false;
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
