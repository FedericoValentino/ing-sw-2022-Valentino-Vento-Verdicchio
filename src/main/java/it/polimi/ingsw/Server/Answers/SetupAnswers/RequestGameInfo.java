package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * This Message is used to ask the client for the gameMode information
 */
public class RequestGameInfo extends StandardSetupAnswer {

    /**
     * Class Constructor
     */
    public RequestGameInfo()
    {
        super.type = SETUPANSWERTYPE.GAME_NFO_REQ;
    }
}
