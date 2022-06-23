package it.polimi.ingsw.Client.GUI.Controllers.InformationControllers;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightUtilities.InfoDispenser;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.EndTurn;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.stream.Collectors;

public class PropagandaController extends Controller implements ObserverLightView
{

    @FXML private AnchorPane propaganda;
    @FXML private Button endTurn;
    @FXML private Button exit;
    @FXML private Button playersInfo;

    private LightView view;

    public void setup(LightView view)
    {
        endTurn.setOnMouseClicked(this:: endTurnOnClick);
        exit.setOnMouseClicked(this:: exitOnClick);
        playersInfo.setOnMouseClicked(this:: infosOnClick);

        this.view = view;

        view.getCurrentTurnState().addObserverLight(this);

        update(view.getCurrentTurnState());
    }

    public void hintGeneration()
    {
        Text hint = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("hints")).collect(Collectors.toList()).get(0));
        hint.setText("");
        hint.setText(GuiMainStarter.getClientGUI().informationCreator(view.getCurrentTurnState(), view.getCurrentTeams()).getInfoMessage());
    }

    private void infosOnClick(MouseEvent mouseEvent)
    {
    }

    private void exitOnClick(MouseEvent mouseEvent)
    {
    }

    private void endTurnOnClick(MouseEvent mouseEvent)
    {
        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new EndTurn()));
    }

    @Override
    public void update(Object o)
    {
        LightTurnState state = (LightTurnState) o;

        hintGeneration();
        Text turn = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("turn")).collect(Collectors.toList()).get(0));
        turn.setText("");
        turn.setText("It's " + state.getCurrentPlayer() + "'s turn");
        LightPlayer currentPlayer = view.findPlayerByName(view.getCurrentTeams(), GuiMainStarter.getClientGUI().getServerConnection().getNickname());

        Text coins = (Text) (propaganda.getChildren().stream().filter(node -> node.getId().equals("coins")).collect(Collectors.toList()).get(0));
        coins.setText("");
        coins.setText("Your coins: " + currentPlayer.getCoinAmount());
    }
}
