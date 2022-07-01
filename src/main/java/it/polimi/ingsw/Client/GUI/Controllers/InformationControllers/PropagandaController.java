package it.polimi.ingsw.Client.GUI.Controllers.InformationControllers;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.EndTurn;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.Disconnect;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.stream.Collectors;

/**
 * GUI controller responsible for the graphical representation and the functions associated with the Information panel
 */
public class PropagandaController extends Controller implements ObserverLightView
{

    @FXML private AnchorPane propaganda;
    @FXML private Button endTurn;
    @FXML private Button exit;

    private LightView view;

    /**
     * Method setup adds the observer to the currentTurnState and sets up the buttons for ending the turn and to exit the game
     * @param view is our current game view
     */
    public void setup(LightView view)
    {
        endTurn.setOnMouseClicked(this:: endTurnOnClick);
        exit.setOnMouseClicked(this:: exitOnClick);

        this.view = view;

        view.getCurrentTurnState().addObserverLight(this);

        update(view.getCurrentTurnState());
    }

    /**
     * Generates and displays a string containing the current hint
     */
    public void hintGeneration()
    {
        Text hint = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("hints")).collect(Collectors.toList()).get(0));
        hint.setText("");
        hint.setText(GuiMainStarter.getClientGUI().informationCreator(view.getCurrentTurnState(), view.getCurrentTeams()).getInfoMessage());
    }

    /**
     * Method exitOnClick is called whenever the exit button is clicked. It sends a disconnect message to the server and closes the game
     * @param mouseEvent the mouseEvent
     */
    private void exitOnClick(MouseEvent mouseEvent)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new Disconnect()));
        System.exit(0);
        Platform.exit();
    }

    /**
     * Method endTurnOnClick is called whenever the endTurn button is clicked. It sends a EndTurn message to the server
     * @param mouseEvent the mouseEvent
     */
    private void endTurnOnClick(MouseEvent mouseEvent)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new EndTurn()));
    }

    /**
     * Method "update" updates the hints, the current coins and the bank balance for our user
     * @param o the LightTurnState object it receives with each update
     */
    @Override
    public void update(Object o)
    {
        Platform.runLater(() -> {
            LightTurnState state = (LightTurnState) o;

            hintGeneration();
            Text turn = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("turn")).collect(Collectors.toList()).get(0));
            turn.setText("");
            turn.setText("It's " + state.getCurrentPlayer() + "'s turn");
            LightPlayer currentPlayer = view.findPlayerByName(view.getCurrentTeams(), GuiMainStarter.getClientGUI().getServerConnection().getNickname());

            Text coins = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("coins")).collect(Collectors.toList()).get(0));
            coins.setText("");
            coins.setText("Your coins: " + currentPlayer.getCoinAmount() + "   Bank Balance: " + view.getBankBalance());
        });
    }
}
