package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class RequestGameInfo extends StandardSetupAnswer {
    private String info;

    /**
     * Class Constructor, this message is used to ask the client for the gamemode information
     */
    public RequestGameInfo()
    {
        super.type = SETUPANSWERTYPE.GAME_NFO_REQ;
    }

    public String getInfo() {
        return info;
    }
}
