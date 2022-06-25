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
   public void registerConnection(Socket s, Match lastCreated)
   {
      try
      {
         System.out.println("registering new connection");
         //Receiving Client Nickname
         ClientConnection connection = new ClientConnection(s);
         SetupConnection message = (SetupConnection) connection.getInputStream().readObject();
         connection.setNickname(message.getNickname());



         for(GameHandler GH : lastCreated.getClients())
         {
            if(GH.getSocket().getNickname().equals(connection.getNickname()))
            {
               connection.sendAnswer(new SerializedAnswer(new RejectConnection()));
               connection.getClient().close();
               return;
            }
         }
         //adding Client To waiting Lobby
         lastCreated.addClient(connection);
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
   public GameMode requestGameInfo(GameHandler GH)
   {
      try
      {

         //requesting GameInfo to first client
         GH.getSocket().sendAnswer(new SerializedAnswer(new RequestGameInfo()));
         //receiving GameMode info from first client
         GameMode info =(GameMode) GH.getSocket().getInputStream().readObject();
         isGameSet = true;
         return info;
      }
      catch(IOException e)
      {
         System.out.println("Client disconnected");
         matches.get(matches.size() - 1).getClients().remove(GH);
      }
      catch(ClassNotFoundException e)
      {
         System.out.println("Couldn't understand client");
         matches.get(matches.size() - 1).getClients().remove(GH);
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
            if(matches.size() == 0)
            {
               matches.add(new Match(matches.size()));
            }
            int runningMatches = 0;
            for(Match match: matches)
            {
               if (match.getRunning())
                  runningMatches ++;
            }
            if(runningMatches == matches.size())
            {
               matches.add(new Match(matches.size()));
            }
            Match last = matches.get(matches.size() - 1);
            Socket socket = server.accept();
            System.out.println("New Connection!");
            registerConnection(socket, last);

            if(last.getClients().size() == 1 && !last.isGameSet())
            {
               info = requestGameInfo(last.getClients().get(0));
               last.setMode(info.isExpertGame(), info.getMaxPlayers());
            }
            if(last.getClients().size() == info.getMaxPlayers())
            {
               last.startGame();
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
