import Client.CLI.ClientCLI;
import Client.GUI.ClientGUI;
import Client.ClientView;

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
