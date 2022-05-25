package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView;
import Observer.ObserverLightView;

public class DashboardController extends Controller implements ObserverLightView {
    private LightView lightView;

    @Override
    public void update(Object o) {
        // casto l'oggetto a light view
        //updato la gui

    }
}
