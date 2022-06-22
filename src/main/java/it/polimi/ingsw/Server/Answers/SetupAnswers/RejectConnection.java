package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class RejectConnection extends StandardSetupAnswer
{

    private final String rejectionInfo = "Connection rejected. Nickname already present. Please, change your nickname";

    /**
     * Class constructor, this message is used to tell the client the connection has been rejected due to a duplicate name
     */
    public RejectConnection()
    {
        type = SETUPANSWERTYPE.REJECT;
    }

    public String getRejectionInfo() {
        return rejectionInfo;
    }
}
