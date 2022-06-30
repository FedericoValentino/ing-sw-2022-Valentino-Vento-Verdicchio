package it.polimi.ingsw.Client;
//
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightUtilities.InternalMessage;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ERRORTYPES;
import it.polimi.ingsw.Server.Answers.SetupAnswers.SETUPANSWERTYPE;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

import java.util.ArrayList;

public interface InformationGenerator
{
    /**
     * Factory Method to produce the correct error message string
     * @param error the type of error
     * @param view our current LightView
     * @return a string containing the error information
     */
    default InternalMessage errorGenerator(ERRORTYPES error, LightView view)
    {
        switch(error)
        {
            case WRONG_INPUT:
                return new InternalMessage("Wrong Input or already taken");
            case WRONG_PHASE:
                return new InternalMessage("Wrong Phase! You are in " + view.getCurrentTurnState().getGamePhase());
            case MOTHER_ERROR:
                return new InternalMessage("You moved Mother Nature too much!");
            case CARD_ERROR:
                return new InternalMessage("The Card you chose couldn't be played");
            case CLOUD_ERROR:
                if(view.getCurrentTurnState().getGamePhase().equals(GamePhase.PLANNING))
                {
                    return new InternalMessage("Wrong input or you chose a non empty cloud!");
                }
                else if(view.getCurrentTurnState().getGamePhase().equals(GamePhase.ACTION))
                {
                    return new InternalMessage("Wrong input or you chose an empty cloud!");
                }
            case WRONG_TURN:
                return new InternalMessage("Not your Turn!");
            case STUD_MOVE_ERROR:
                return new InternalMessage("You student move was invalid");
            case EMPTY_POUCH:
                return new InternalMessage("Pouch unavailable, skipping phase");
            default:
                return new InternalMessage("Generic Error");
        }
    }


    /** A factory method that uses the client's own information on the game to dynamically generate hints to display to the player
     * @param state the actual state of the game
     * @param teams the list of teams
     * @return the dynamically generated hint
     */
    default InternalMessage informationCreator(LightTurnState state, ArrayList<LightTeam> teams) {
        int players = 0;
        for (LightTeam team : teams) {
            players += team.getPlayers().size();
        }
        if (state.getGamePhase().equals(GamePhase.PLANNING)) {
            switch (state.getPlanningMoves()) {
                case 0:
                    return new InternalMessage("Choose which cloud to refill!");
                case 1:
                    return new InternalMessage("Choose an assistant card to play!");
                default:
                    throw new IllegalStateException("Unexpected value: " + state.getPlanningMoves());
            }
        } else if (state.getGamePhase().equals(GamePhase.ACTION)) {
            boolean threePlayerGame = players == 3;
            if (state.getActionMoves() == 0) {
                if (threePlayerGame)
                    return new InternalMessage("Move four students from your entrance");
                else
                    return new InternalMessage("Move three students from your entrance");
            } else if (state.getActionMoves() == 3) {
                if (!threePlayerGame)
                    return new InternalMessage("Move Mother Nature");
            } else if (state.getActionMoves() == 4) {
                if (threePlayerGame)
                    return new InternalMessage("Move Mother Nature");
                else
                    return new InternalMessage("Choose a cloud to refill your entrance");
            } else if (state.getActionMoves() == 5) {
                if (threePlayerGame)
                    return new InternalMessage("Choose a cloud to refill your entrance");
                else
                    return new InternalMessage("End your turn");
            }
        }
        return new InternalMessage("");
    }



}
