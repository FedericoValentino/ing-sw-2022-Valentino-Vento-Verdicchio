package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class InfoMessage extends StandardSetupAnswer {
    private String info;

    /**
     * Class Constructor, used to tell the client whichever information is in the input string
     */
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
