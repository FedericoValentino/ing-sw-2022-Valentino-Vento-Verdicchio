package it.polimi.ingsw.Client.PreemptiveChecks;

import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.ACTIONMESSAGETYPE;
import it.polimi.ingsw.model.CurrentGameState;

public final class MovesChecks
{
    public static boolean isExpectedPlanningMove(LightView view, ACTIONMESSAGETYPE planningPhase)
    {
        if(planningPhase.equals(ACTIONMESSAGETYPE.CLOUD_CHOICE))
            return view.getCurrentTurnState().getPlanningMoves() == 0;
        else if(planningPhase.equals(ACTIONMESSAGETYPE.DRAW_CHOICE))
        {
            return view.getCurrentTurnState().getPlanningMoves() == 1;
        }
        return false;
    }

    public static boolean isExpectedActionMove(LightView view, int playerNumber, ACTIONMESSAGETYPE actionPhase)
    {
        int moves = view.getCurrentTurnState().getActionMoves();
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
