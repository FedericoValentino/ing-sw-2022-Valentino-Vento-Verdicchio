package controller;

import Client.Messages.ActionMessages.ACTIONMESSAGETYPE;
import model.CurrentGameState;

public final class MovesChecks
{
    public static boolean isExpectedPlanningMove(CurrentGameState game, ACTIONMESSAGETYPE planningPhase)
    {
        if(planningPhase.equals(ACTIONMESSAGETYPE.CLOUD_CHOICE))
            return game.getCurrentTurnState().getMoves() == 0;
        else if(planningPhase.equals(ACTIONMESSAGETYPE.DRAW_CHOICE))
        {
            if(game.getCurrentTurnState().getMoves() == 1)
            {
                game.getCurrentTurnState().ResetMoves();
                return true;
            }
            else
                return false;
        }
        return false;
    }

    public static boolean isExpectedActionMove(CurrentGameState game, int playerNumber, ACTIONMESSAGETYPE actionPhase)
    {
        int moves = game.getCurrentTurnState().getMoves();
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
        else if(actionPhase.equals(ACTIONMESSAGETYPE.DRAW_POUCH))
        {
            if(threePlayerGame && moves == 5) {
                game.getCurrentTurnState().ResetMoves();
                return true;
            }
            else if(!threePlayerGame && moves == 4) {
                game.getCurrentTurnState().ResetMoves();
                return true;
            }

        }
        else if(actionPhase.equals(ACTIONMESSAGETYPE.TURN_END))
            if(moves == 0)
                return true;
            else
                return false;
        return false;
    }
}
