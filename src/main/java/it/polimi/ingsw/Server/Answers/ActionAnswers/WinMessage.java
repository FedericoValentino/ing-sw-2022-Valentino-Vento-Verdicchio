package it.polimi.ingsw.Server.Answers.ActionAnswers;

public class WinMessage extends StandardActionAnswer
{

    private final String winningTeam;

    /**
     * Class constructor this message is used to tell the client what team has won the match
     * @param winning
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
