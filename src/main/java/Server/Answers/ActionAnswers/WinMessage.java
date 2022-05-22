package Server.Answers.ActionAnswers;

public class WinMessage extends StandardActionAnswer
{

    private String winningTeam;

    public WinMessage()
    {
        super.type = ACTIONANSWERTYPE.WIN;
    }

    public String getWinningTeam(){
        return winningTeam;
    }
}
