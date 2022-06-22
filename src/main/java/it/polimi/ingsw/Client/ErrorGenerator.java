package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ERRORTYPES;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

public interface ErrorGenerator
{
    /**
     * Factory Method to produce the correct error message string
     * @param error the type of error
     * @param view our current LightView
     * @return a string containing the error information
     */
    default String errorGenerator(ERRORTYPES error, LightView view)
    {
        switch(error)
        {
            case WRONG_INPUT:
                return "Wrong Input or already taken";
            case WRONG_PHASE:
                return "Wrong Phase you are in " + view.getCurrentTurnState().getGamePhase();
            case MOTHER_ERROR:
                return "You moved Mother Nature too much!";
            case CARD_ERROR:
                return "The Card you choose couldn't be played";
            case CLOUD_ERROR:
                if(view.getCurrentTurnState().getGamePhase().equals(GamePhase.PLANNING))
                {
                    return "Wrong input or you choose a non empty cloud!";
                }
                else if(view.getCurrentTurnState().getGamePhase().equals(GamePhase.ACTION))
                {
                    return "Wrong input or you choose an empty cloud!";
                }
            case WRONG_TURN:
                return "Not your Turn!";
            default:
                return "Generic Error";
        }
    }
}
