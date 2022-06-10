package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class InfoMessage extends StandardSetupAnswer {
    private String info;

    public InfoMessage(String input)
    {
        this.info = input;
        super.type = SETUPANSWERTYPE.GAME_NFO;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
