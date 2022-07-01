package it.polimi.ingsw.Server.Answers.ActionAnswers;

/**
 * This Message is used to tell the client which team has won the match
 */
public class WinMessage extends StandardActionAnswer
{

    private final String winningTeam;

    /**
     * Class constructor.
     */
    public WinMessage(String winning)
    {
        super.type = ACTIONANSWERTYPE.WIN;
        this.winningTeam = winning;
    }



    public String getWinningTeam(){
        return winningTeam;
    }
}
