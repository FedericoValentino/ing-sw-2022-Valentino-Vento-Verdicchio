package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.InfoDispenser;
import Client.LightView.LightView;
import Client.Messages.ActionMessages.EndTurn;
import Client.Messages.SerializedMessage;
import Observer.ObserverLightView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    }

    public void hintGeneration()
    {
        Text hint = (Text) (Propaganda.getChildren().stream().filter(node -> node.getId().equals("StatusDescriptor")).collect(Collectors.toList()).get(0));
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
        hintGeneration();
    }
}
