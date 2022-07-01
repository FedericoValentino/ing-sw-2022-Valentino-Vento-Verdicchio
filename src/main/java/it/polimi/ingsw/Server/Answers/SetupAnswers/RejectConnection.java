package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * This Message is used to tell the client the connection has been rejected due to a duplicate name
 */
public class RejectConnection extends StandardSetupAnswer
{

    private final String rejectionInfo = "Connection rejected. Nickname already present. Please, change your nickname";

    /**
     * Class constructor.
     */
    public RejectConnection()
    {
        type = SETUPANSWERTYPE.REJECT;
    }

    public String getRejectionInfo() {
        return rejectionInfo;
    }
}
