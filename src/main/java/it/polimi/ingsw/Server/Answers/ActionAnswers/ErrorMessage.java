package it.polimi.ingsw.Server.Answers.ActionAnswers;


/**
 * This message is used to tell the client about his errors
 */
public class ErrorMessage extends StandardActionAnswer
{


    private ERRORTYPES error;

    /**
     * Class Constructor.
     */
    public ErrorMessage(ERRORTYPES error)
    {
        this.error = error;
        super.type = ACTIONANSWERTYPE.ERROR;
    }

    public ERRORTYPES getError(){
        return error;
    }
}
