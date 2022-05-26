import Client.CLI.ClientCLI;
import Client.ClientView;

import java.io.IOException;

public class main
{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientView CLI = new ClientCLI();
        CLI.run();
    }
}
