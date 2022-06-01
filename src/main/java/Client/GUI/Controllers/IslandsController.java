package Client.GUI.Controllers;

import Client.LightView.LightIslands;
import Observer.ObserverLightView;
import javafx.scene.layout.AnchorPane;

public class IslandsController extends Controller implements ObserverLightView {

    public AnchorPane cloudsAnchorPane;

    public void setup(AnchorPane anchorPane, LightIslands lightIslands)
    {
        lightIslands.addObserverLight(this);
        //Con il mio anchor pane  poi vado a settare la
    }

    @Override
    public void update(Object o) {

    }
}
