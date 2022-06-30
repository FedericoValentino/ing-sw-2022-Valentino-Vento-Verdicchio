package it.polimi.ingsw.controller;

import it.polimi.ingsw.Client.Messages.ActionMessages.ACTIONMESSAGETYPE;
import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.checksandbalances.MovesChecks;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import junit.framework.TestCase;

public class MovesChecksTest extends TestCase {

    MainController controllerTest = new MainController(2, true);
    MainController getControllerTestFor3 = new MainController(3, true);

    /**
     * For each value of planningMoves, checks the positive and negative response of the method for both cases.
     */
    public void testIsExpectedPlanningMove()
    {
        TestUtilities.setupTestFor2(controllerTest);

        assertTrue(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.CLOUD_CHOICE));
        assertFalse(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.DRAW_CHOICE));

        controllerTest.getGame().getCurrentTurnState().updatePlanningMoves();
        assertTrue(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.DRAW_CHOICE));
        assertFalse(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.CLOUD_CHOICE));

        controllerTest.getGame().getCurrentTurnState().updatePlanningMoves();
        assertFalse(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.CLOUD_CHOICE));
        assertFalse(MovesChecks.isExpectedPlanningMove(controllerTest.getGame(), ACTIONMESSAGETYPE.DRAW_CHOICE));
    }

    /**
     * Both for two and three players, fir each value of actionMoves checks the positive or negative response of the method,
     * based on what to expect: instead of using all combinations, it generally uses a combination of the right move with
     * the previous, now wrong, move
     */
    public void testIsExpectedActionMove() {
        TestUtilities.setupTestFor2(controllerTest);
        TestUtilities.setupTestFor3(getControllerTestFor3);

        controllerTest.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);
        getControllerTestFor3.getGame().getCurrentTurnState().updateGamePhase(GamePhase.ACTION);

        for (int i = 0; i < 3; i++)
        {
            assertTrue(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.STUD_MOVE));
            controllerTest.getGame().getCurrentTurnState().updateActionMoves();
        }
        assertFalse(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.STUD_MOVE));

        assertTrue(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.MN_MOVE));
        controllerTest.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.MN_MOVE));
        assertTrue(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.ENTRANCE_REFILL));
        controllerTest.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.ENTRANCE_REFILL));
        assertTrue(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.TURN_END));
        controllerTest.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(controllerTest.getGame(), 2, ACTIONMESSAGETYPE.TURN_END));


        for (int i = 0; i < 4; i++)
        {
            assertTrue(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.STUD_MOVE));
            getControllerTestFor3.getGame().getCurrentTurnState().updateActionMoves();
        }
        assertFalse(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.STUD_MOVE));

        assertTrue(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.MN_MOVE));
        getControllerTestFor3.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.MN_MOVE));
        assertTrue(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.ENTRANCE_REFILL));
        getControllerTestFor3.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.ENTRANCE_REFILL));
        assertTrue(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.TURN_END));
        getControllerTestFor3.getGame().getCurrentTurnState().updateActionMoves();

        assertFalse(MovesChecks.isExpectedActionMove(getControllerTestFor3.getGame(), 3, ACTIONMESSAGETYPE.TURN_END));

    }
}