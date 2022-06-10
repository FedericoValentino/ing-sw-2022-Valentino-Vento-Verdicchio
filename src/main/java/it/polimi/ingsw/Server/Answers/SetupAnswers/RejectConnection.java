package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class RejectConnection extends StandardSetupAnswer
{

    private final String rejectionInfo = "Connection rejected. Nickname already present. Please, change your nickname";

    public RejectConnection()
    {
        type = SETUPANSWERTYPE.REJECT;
    }

    public String getRejectionInfo() {
        return rejectionInfo;
    }
}
