import it.polimi.ingsw.Client.CLI.ClientCLI;
import it.polimi.ingsw.Client.GUI.ClientGUI;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Server.Server;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp
{
    /**
     * This main method display into the standard output console which interface (cli or gui) we want to run and
     * according to the choice it starts the ClientCLI or the ClientGUI.
     * @param args is the Standard String vector that main must have
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        ClientView view;
        Scanner stdin = new Scanner(System.in);
        System.out.println("Which graphical interface would you like to run, CLI or GUI? If you would like to run the server instead just type SERVER");
        String selection = stdin.next();

        switch(selection)
        {
            case "CLI":
                view = new ClientCLI();
                view.run();
                break;
            case "GUI":
                view = new ClientGUI();
                view.run();
                break;
            case "SERVER":
                Server server;
                try {
                    server = new Server();
                    server.run();
                } catch(IOException e){
                    System.err.println("Impossible to start the server!\n" + e.getMessage());
                    throw new RuntimeException(e);
                }
            default:
                break;
        }
    }
}
