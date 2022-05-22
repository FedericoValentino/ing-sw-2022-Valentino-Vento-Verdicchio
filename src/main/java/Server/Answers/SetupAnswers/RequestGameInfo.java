package Server.Answers.SetupAnswers;

public class RequestGameInfo extends StandardSetupAnswer {
    private String info;

    public RequestGameInfo()
    {
        super.type = SETUPANSWERTYPE.GAME_NFO_REQ;
    }

    public String getInfo() {
        return info;
    }
}
