package it.polimi.ingsw.Client.GUI.Controllers.OutOfGameControllers.InformationAndMiscellanea;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;

public class DescriptionController extends Controller {

    /**This function is called when the Back button is pressed and use the loadNewScreen method, inherited from the
     * abstract class controller, to rollback from the current scene to the Intro scene fxml (specified in the variable
     * called path).
     * The location of the intro scene it's stored in the variable called path.
     */
    public void onClickBackToIntro(){
        String path= "/Client/GUI/Controllers/Intro.fxml";
        loadNewScreen(path);
    }
}
