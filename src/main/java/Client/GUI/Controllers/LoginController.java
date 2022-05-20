package Client.GUI.Controllers;

import Client.CLI.InputParser;
import Client.CLI.ListenerCLI;
import Client.GUI.ClientGUI;
import Client.GUI.ListenerGui;
import Client.Messages.SetupMessages.SetupConnection;
import Client.ServerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


import java.io.IOException;

public class LoginController extends Controller{

    @FXML public TextField nickname;
    @FXML public TextField IP;
    @FXML public TextField Port;
    @FXML public Button TryConn;
    @FXML public Button Back;
    @FXML public ChoiceBox <String>teamChoice;

    ServerConnection sv;


    public void onClickBack(ActionEvent actionEvent) throws IOException {
        String path="/GUI/Controllers/Intro.fxml";
        loadNewScreen(path,actionEvent);
    }

    public void initialize()
    {
        teamChoice.getItems().addAll("Grey","White","Black");
        teamChoice.setValue("Black");
    }

    public void onClickTryConnection(ActionEvent actionEvent) throws IOException {
        //se non è il primo prendo i parametri e mando la connection
        //se è il primo devo salvare i messaggi e caricare la seconda schermata


    //occhio che fin'pra il team è un numero, invece io ho una stringa
        System.out.println(IP.getText());

        //teamChoice.getItems()
        sv = new ServerConnection(nickname.toString(), 1, IP.getText());

        SetupConnection setup = new SetupConnection(sv.getNickname(), sv.getTeam());
        sv.getOut().writeObject(setup);
        sv.getOut().flush();
        sv.getOut().reset();


        //a questo punto devo capire se è il primo o meno

        //cioè mi creo il mio listener e se è il primo mi va a chiamare un metodo che mi carica la lobby

        /*parte copiataaaaaaaaaa
        ClientGUI cgTemp=new ClientGUI();
        ListenerGui Listener = new ListenerGui(cgTemp.getCLientGUi());

        this.stdin = new InputParser(main, MyView);
        executor.execute(Listener);
        while(true)
        {
            if(!setupState)
            {
                stdin.newMove();
            }
        }*/

    }

}
