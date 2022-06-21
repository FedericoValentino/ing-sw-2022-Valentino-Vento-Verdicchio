package it.polimi.ingsw;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.Wizard;

public final class TestUtilities
{
    public static void setupTestfor2(MainController controller)
    {
        controller.addPlayer(0, "jack", 8, Wizard.LORD );
        controller.addPlayer(1, "fede", 8, Wizard.DRUID);
        controller.setup();
    }

    public static void setupTestfor3(MainController controller)
    {
        controller.addPlayer(0, "jack", 6, Wizard.LORD );
        controller.addPlayer(1, "fede", 6, Wizard.DRUID);
        controller.addPlayer(2,"puddu",6, Wizard.SENSEI);
        controller.setup();
    }
}
