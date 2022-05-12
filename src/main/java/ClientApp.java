import Client.ClientCLI;
import Client.ClientGUI;
import Client.ClientView;
import Server.Server;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        ClientView view;
        Scanner stdin = new Scanner(System.in);
        System.out.println("Quale interfaccia vuoi far partire?");
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
            default:
                break;
        }
    }
}