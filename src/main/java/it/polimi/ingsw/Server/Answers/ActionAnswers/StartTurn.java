package it.polimi.ingsw.Server.Answers.ActionAnswers;

public class StartTurn extends StandardActionAnswer
{
    private String announcement;

    public StartTurn() {
        super.type = ACTIONANSWERTYPE.START_NFO;
    }

    public String getAnnouncement(){
        return announcement;
    }
}
