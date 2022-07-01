package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * Message used to tell the client whichever information is in the input string
 */
public class InfoMessage extends StandardSetupAnswer {
    private String info;

    /**
     * Class Constructor.
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
