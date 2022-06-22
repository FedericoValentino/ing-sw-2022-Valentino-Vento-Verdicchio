package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.Messages.SetupMessages.GameMode;
import it.polimi.ingsw.Client.Messages.SetupMessages.SetupConnection;
import it.polimi.ingsw.Server.Answers.SerializedAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.RejectConnection;
import it.polimi.ingsw.Server.Answers.SetupAnswers.RequestGameInfo;



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
   private boolean isGameSet = false;


   /**
    * Class Constructor
    * @throws IOException
    */
   public Server() throws IOException
   {
       this.server = new ServerSocket(PORT);
   }

   /**
    * Method registerConnection registers the clients connecting to the server adding them to the server waitLobby
    * @param s
    */
   public void registerConnection(Socket s)
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

   /**
    * Method requestGameInfo asks the first client connecting to the waitLobby what gameMode he would like to play
    * @param client
    * @return the requested GameMode
    */
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

   /**
    * Method run starts up the server making it accept connections and creating a new Match every time enough clients connect
    */
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
