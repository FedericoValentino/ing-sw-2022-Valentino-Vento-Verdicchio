package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.InfoDispenser;
import it.polimi.ingsw.Client.LightView.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.EndTurn;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.stream.Collectors;

public class PropagandaController extends Controller implements ObserverLightView
{

    public AnchorPane Propaganda;
    public Button EndTurn;
    public Button Exit;
    public Button PlayersInfo;

    private InfoDispenser infoGenerator;
    private LightView view;

    public void setup(InfoDispenser infoDispenser, LightView view)
    {
        EndTurn.setOnMouseClicked(this:: endTurnOnClick);
        Exit.setOnMouseClicked(this:: exitOnClick);
        PlayersInfo.setOnMouseClicked(this:: infosOnClick);

        this.infoGenerator = infoDispenser;
        this.view = view;

        view.getCurrentTurnState().addObserverLight(this);

        update(view.getCurrentTurnState());
    }

    public void hintGeneration()
    {
        Text hint = (Text) (Propaganda.getChildren().stream().filter(node -> node.getId().equals("Hints")).collect(Collectors.toList()).get(0));
        hint.setText("");
        hint.setText(infoGenerator.informationCreator(view.getCurrentTurnState(), view.getCurrentTeams()).getInfoMessage());
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
        Text turn = (Text) (Propaganda.getChildren().stream().filter(node -> node.getId().equals("Turn")).collect(Collectors.toList()).get(0));
        turn.setText("");
        turn.setText("It's " + state.getCurrentPlayer() + "'s turn");
        LightPlayer currentPlayer = view.findPlayerByName(view.getCurrentTeams(), GuiMainStarter.getClientGUI().getServerConnection().getNickname());

        Text coins = (Text) (Propaganda.getChildren().stream().filter(node -> node.getId().equals("Coins")).collect(Collectors.toList()).get(0));
        coins.setText("");
        coins.setText("Your coins: " + currentPlayer.getCoinAmount());
    }
}
