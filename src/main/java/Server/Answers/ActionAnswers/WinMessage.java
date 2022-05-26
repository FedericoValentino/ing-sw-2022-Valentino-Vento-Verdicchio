package Server.Answers.ActionAnswers;

public class WinMessage extends StandardActionAnswer
{

    private final String winningTeam;

    public WinMessage(String winning)
    {
        super.type = ACTIONANSWERTYPE.WIN;
        this.winningTeam = winning;
    }



    public String getWinningTeam(){
        return winningTeam;
    }
}
