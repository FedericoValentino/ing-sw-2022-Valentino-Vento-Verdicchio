package it.polimi.ingsw.controller.checksandbalances;

import it.polimi.ingsw.Client.Messages.ActionMessages.ACTIONMESSAGETYPE;
import it.polimi.ingsw.model.CurrentGameState;

/**
 * MovesChecks, as opposed to the specific checks in the Checks class, enforces general checks based on the expected move
 * at any given time. Using the structures and parameters in currentTurnState, the checks compare the action type coming
 * from the server to what it is expected to happen, and then deliver a boolean response
 */
public final class MovesChecks
{
    /**
     * Checks if the player's move is the expected move at the given state of the game
     * @param game an instance of the game
     * @param planningPhase the planning action to be examined
     * @return true if the action is possible, false if it isn't
     */
    public static boolean isExpectedPlanningMove(CurrentGameState game, ACTIONMESSAGETYPE planningPhase)
    {
        if(planningPhase.equals(ACTIONMESSAGETYPE.CLOUD_CHOICE))
            return game.getCurrentTurnState().getPlanningMoves() == 0;
        else if(planningPhase.equals(ACTIONMESSAGETYPE.DRAW_CHOICE))
        {
            return game.getCurrentTurnState().getPlanningMoves() == 1;
        }
        return false;
    }

    /**
     * Same as the check above, but for the action phase. Needs the number of players to function properly
     * @param game an instance of the game
     * @param playerNumber number of players
     * @param actionPhase the action move that the player wants to perform
     * @return true if the action is expected, false otherwise
     */
    public static boolean isExpectedActionMove(CurrentGameState game, int playerNumber, ACTIONMESSAGETYPE actionPhase)
    {
        int moves = game.getCurrentTurnState().getActionMoves();
        boolean threePlayerGame;
        threePlayerGame = playerNumber == 3;
        if(actionPhase.equals(ACTIONMESSAGETYPE.STUD_MOVE))
        {
            if(threePlayerGame)
                return moves >= 0 && moves < 4;
            else
                return moves >= 0 && moves < 3;
        }
        else if(actionPhase.equals(ACTIONMESSAGETYPE.MN_MOVE))
        {
            if(threePlayerGame)
                return moves == 4;
            else
                return moves == 3;
        }
        else if(actionPhase.equals(ACTIONMESSAGETYPE.ENTRANCE_REFILL))
        {
            if(threePlayerGame && moves == 5) {
                return true;
            }
            else if(!threePlayerGame && moves == 4) {
                return true;
            }

        }
        else if(actionPhase.equals(ACTIONMESSAGETYPE.TURN_END))
            if(threePlayerGame)
            {
                if(moves == 6)
                    return true;
                else
                    return false;
            }
            else
            {
                if(moves == 5)
                    return true;
                else
                    return false;
            }

        return false;
    }
}
