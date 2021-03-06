package it.polimi.ingsw;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

/**
 * These are utilities methods to make easier and faster the coding of some test methods
 */
public final class TestUtilities
{
    /**
     * Sets up a test environment for two players
     * @param controller an instance of the MainController
     */
     public static void setupTestFor2(MainController controller)
    {
        controller.addPlayer(0, "jack", 8, Wizard.LORD );
        controller.addPlayer(1, "fede", 8, Wizard.DRUID);
        controller.setup();
    }

    /**
     * Sets up a test environment for three players
     * @param controller an instance of the MainController
     */
    public static void setupTestFor3(MainController controller)
    {
        controller.addPlayer(0, "jack", 6, Wizard.LORD );
        controller.addPlayer(1, "fede", 6, Wizard.DRUID);
        controller.addPlayer(2,"puddu",6, Wizard.SENSEI);
        controller.setup();
    }

    /**
     * Sets up the initial turn order for a test environment
     * @param controller an instance of the MainController
     */
    public static void setupTurn(MainController controller)
    {
        controller.updateTurnState();
        controller.determineNextPlayer();
    }

    /**
     * Grants more coins to both players to ease the testing of character effects
     * @param controller an instance of the MainController
     */
    public static void gainCoins(MainController controller)
    {
        controller.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controller.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
    }

}
